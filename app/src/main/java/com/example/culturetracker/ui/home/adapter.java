package com.example.culturetracker.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.culturetracker.R;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.MyView>
{
    private final List<Book> bookList;

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate item.xml using LayoutInflator
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);

        // return itemView
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        holder.bookName.setText(bookList.get(position).getName());
        holder.bookAuthor.setText(bookList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        TextView bookName, bookAuthor;
        public MyView(@NonNull View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.book_name);
            bookAuthor= itemView.findViewById(R.id.book_author);
        }
    }
    public adapter(List<Book>bookList){
        this.bookList = bookList;


    }
}
