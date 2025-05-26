package com.example.tp5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    private RecyclerView rv_books;
    private BooksAdapter bookAdapter;
    private SearchView searchBar;
    private ProgressBar progressBar;
    private ExecutorService executorService;
    private Handler searchHandler;
    private final Runnable searchRunnable = new Runnable() {
        @Override
        public void run() {
            performSearch();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchHandler = new Handler(Looper.getMainLooper());

        progressBar = view.findViewById(R.id.progressBar);
        searchBar = view.findViewById(R.id.search_bar);
        rv_books = view.findViewById(R.id.rvHomeBook);

        rv_books.setHasFixedSize(true);
        rv_books.setLayoutManager(new GridLayoutManager(getContext(), 2));

        bookAdapter = new BooksAdapter(DataDummy.books);
        rv_books.setAdapter(bookAdapter);

        searchBar.clearFocus();
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handleSearchInput();
                return true;
            }
        });


        return view;
    }

    private void handleSearchInput() {
        if (searchHandler != null){
            searchHandler.removeCallbacks(searchRunnable);
            progressBar.setVisibility(View.VISIBLE);
            searchHandler.postDelayed(searchRunnable, 800);
        }
    }

    private void performSearch() {
        String query = searchBar.getQuery().toString().trim().toLowerCase();
        if (query.isEmpty()) {
            bookAdapter.setSearchBook(DataDummy.books);
            progressBar.setVisibility(View.GONE);
            return;
        }

        if (executorService == null) {
            executorService = Executors.newSingleThreadExecutor();
        }
        executorService.execute(() -> {
            ArrayList<Books> filteredList = new ArrayList<>();
            for (Books book : DataDummy.books) {
                if (book.getTitle().toLowerCase().contains(query)) {
                    filteredList.add(book);
                }
            }

            requireActivity().runOnUiThread(() -> {
                progressBar.setVisibility(View.GONE);
                if (isAdded()) {
                    bookAdapter.setSearchBook(filteredList);
                    if (filteredList.isEmpty()) {
                        Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (executorService != null) {
            executorService.shutdownNow();
            executorService = null;
        }
        searchHandler.removeCallbacks(searchRunnable);
    }

    public void refreshBooks() {
        if (bookAdapter != null) {
            bookAdapter.setSearchBook(DataDummy.books);
            bookAdapter.notifyDataSetChanged();
        }
    }
}