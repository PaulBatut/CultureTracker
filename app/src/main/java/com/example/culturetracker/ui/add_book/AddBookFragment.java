package com.example.culturetracker.ui.add_book;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.culturetracker.databinding.FragmentAddBookBinding;
import com.example.culturetracker.ui.SaveList;
import com.example.culturetracker.ui.home.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddBookFragment extends Fragment {
    private List<Book> bookList ;
    private FragmentAddBookBinding binding;

    public void getHistory(){
        SaveList saveList = new SaveList();
        JSONObject book = null;
        try {
            book = saveList.getJson(getActivity(),"booklist.json");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(book!=null){
            JSONArray bookarray = null;
            try {
                bookarray = book.getJSONArray("Book");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(int i=0;i<bookarray.length(); i++ ){
                JSONObject bookDescription = new JSONObject();
                Book newBook = null;
                try {
                    bookDescription = (JSONObject) bookarray.get(i);
                    newBook = new Book(bookDescription.getString("name"), bookDescription.getString("author"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                bookList.add(newBook);
            }
        }
    }
    public void saveHistory() throws JSONException, IOException {
        System.out.println(bookList);
        JSONObject saveBook = new JSONObject();
        JSONArray saveBookArray = new JSONArray();
        for(Book book:bookList){
            JSONObject tempBook = new JSONObject();
            tempBook.put("name", book.getName());
            tempBook.put("author", book.getAuthor());
            saveBookArray.put(tempBook);
        }
        saveBook.put("Book", saveBookArray);
        SaveList saveList = new SaveList();
        saveList.saveBookList(getActivity(), "booklist.json", saveBook);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddBookViewModel AddBookViewModel =
                new ViewModelProvider(this).get(AddBookViewModel.class);

        binding = FragmentAddBookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        bookList = new ArrayList<>();
        getHistory();
        binding.addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isbn = binding.editTextNumber.getText().toString();
                if(isbn!= null || isbn!=""){
                    Thread thread = new Thread(new Runnable() {

                        @Override
                        public void run() {

                            try  {
                                retrieveBookInfo(isbn);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();
                }
                else{
                    Toast.makeText(getContext(), "The ISBN is null", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final TextView textView = binding.textAddBook;
        AddBookViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }

    public void retrieveBookInfo(String isbn){
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String params = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbn;
        try {
            URL url = new URL(params);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                //Log.d("Response: ", "> " + line);   //here u ll get whole response......

            }
            JSONObject bookISBNgeneral = new JSONObject(buffer.toString());

            JSONArray bookInfoArray = bookISBNgeneral.getJSONArray("items");
            if(bookInfoArray != null ){
                JSONObject bookISBNinfo = (JSONObject) bookInfoArray.get(0);
                JSONObject volumeInfo = (JSONObject) bookISBNinfo.get("volumeInfo");
                System.out.println( bookISBNinfo.get("kind"));
                Book newbook = new Book(volumeInfo.getString("title"), volumeInfo.getString("authors"));
                bookList.add(newbook);
                saveHistory();
            }
            else{
                Toast.makeText(getContext(), "Invalid ISBN", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}