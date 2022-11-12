package com.example.culturetracker.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.culturetracker.databinding.FragmentHomeBinding;
import com.example.culturetracker.ui.SaveList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    // Layout Manager
    RecyclerView.LayoutManager RecyclerViewLayoutManager;
    // Linear Layout Manager
    LinearLayoutManager VerticalLayout;
    private List<Book> bookList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = binding.recyclerViewId;
        RecyclerViewLayoutManager = new LinearLayoutManager(getActivity());

        // Set LayoutManager on Recycler View
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // calling constructor of adapter
        // with source list as a parameter
        adapter adapter = new adapter(bookList);

        // Set Horizontal Layout Manager
        // for Recycler view
        VerticalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(VerticalLayout);

        // Set adapter on recycler view
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}