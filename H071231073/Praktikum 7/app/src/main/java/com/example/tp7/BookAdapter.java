package com.example.tp7;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    private final ArrayList<Book> books = new ArrayList<>();
    private final Activity activity;

    public BookAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books.clear();
        if (books.size() > 0) {
            this.books.addAll(books);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.book_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ViewHolder holder, int position) {
        holder.bind(books.get(position));

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvJudul, tvDeskripsi, tvInfo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.judul);
            tvDeskripsi = itemView.findViewById(R.id.deskripsi);
            tvInfo = itemView.findViewById(R.id.info);
        }

        public void bind(Book book) {
            tvJudul.setText(book.getJudul());
            tvDeskripsi.setText(book.getDeskripsi());
            tvInfo.setText(book.getInfo());
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(activity, FormActivity.class);
                intent.putExtra(FormActivity.EXTRA_BOOK, book);
                activity.startActivityForResult(intent, FormActivity.REQUEST_UPDATE);
            });
        }
    }
}
