package com.example.tp5;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tp5.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {

    private ArrayList<Books> books;

    public BooksAdapter(ArrayList<Books> books) {
        this.books = books;
    }

    public void setSearchBook(ArrayList<Books> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BooksAdapter.BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.books_item, parent, false);
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksAdapter.BooksViewHolder holder, int position) {
        Books book = books.get(position);
        holder.title.setText(book.getTitle());

        Uri coverUri = Uri.parse(book.getCover());
        Glide.with(holder.itemView.getContext()).load(coverUri).into(holder.cover);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("book", book);
            int actualPosition = DataDummy.books.indexOf(book);
            intent.putExtra("position", actualPosition);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BooksViewHolder extends RecyclerView.ViewHolder {
        private ImageView cover;
        private TextView title;
        public BooksViewHolder(@NonNull View itemView) {
            super(itemView);

            cover = itemView.findViewById(R.id.cover);
            title = itemView.findViewById(R.id.title);
        }
    }
}
