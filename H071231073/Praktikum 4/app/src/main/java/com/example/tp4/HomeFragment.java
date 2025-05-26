package com.example.tp4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView rv_books;
    private BooksAdapter bookAdapter;
    private SearchView searchBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchBar = view.findViewById(R.id.search_bar);
        searchBar.clearFocus();
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        rv_books = view.findViewById(R.id.rvHomeBook);
        rv_books.setHasFixedSize(true);
        rv_books.setLayoutManager(new GridLayoutManager(getContext(), 2));

        bookAdapter = new BooksAdapter(DataDummy.books);
        rv_books.setAdapter(bookAdapter);

        return view;
    }

    private void filterList(String text) {
        ArrayList<Books> filteredList = new ArrayList<>();
        for (Books book : DataDummy.books) {
            if (book.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(book);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            bookAdapter.setSearchBook(filteredList);
        }
    }

    public void refreshBooks() {
        if (bookAdapter != null) {
            bookAdapter.notifyDataSetChanged();
        }
    }
}