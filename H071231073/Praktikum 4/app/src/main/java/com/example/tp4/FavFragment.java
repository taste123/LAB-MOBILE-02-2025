package com.example.tp4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.core.content.ContextCompat;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FavFragment extends Fragment {

    private RecyclerView rv_books;
    private BooksAdapter bookAdapter;
    private ArrayList<Books> likedBooks = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        rv_books = view.findViewById(R.id.rvFavBook);
        rv_books.setHasFixedSize(true);
        rv_books.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        filterLikedBooks();

        bookAdapter = new BooksAdapter(likedBooks);
        rv_books.setAdapter(bookAdapter);

        return view;
    }

    private void filterLikedBooks() {
        likedBooks.clear();
        for (Books book : DataDummy.books) {
            if (book.getLike() == 1) {
                likedBooks.add(book);
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        filterLikedBooks();
        if (bookAdapter != null) {
            bookAdapter.notifyDataSetChanged();
        }
    }
    private BroadcastReceiver favUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshData();
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("BOOK_FAVORITE_CHANGED");
        ContextCompat.registerReceiver(requireContext(), favUpdateReceiver, filter, ContextCompat.RECEIVER_EXPORTED);
    }

    @Override
    public void onStop() {
        super.onStop();
        requireActivity().unregisterReceiver(favUpdateReceiver);
    }
}