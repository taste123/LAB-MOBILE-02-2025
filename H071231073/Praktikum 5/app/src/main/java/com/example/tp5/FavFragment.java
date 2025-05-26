package com.example.tp5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.core.content.ContextCompat;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavFragment extends Fragment {

    private RecyclerView rv_books;
    private BooksAdapter bookAdapter;
    private ArrayList<Books> likedBooks = new ArrayList<>();
    private ProgressBar progressBar;
    private LinearLayout noBooksCard;
    private ExecutorService executorService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fav, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        noBooksCard = view.findViewById(R.id.noBooksCard);
        rv_books = view.findViewById(R.id.rvFavBook);

        rv_books.setLayoutManager(new LinearLayoutManager(getContext()));
        bookAdapter = new BooksAdapter(likedBooks);
        rv_books.setAdapter(bookAdapter);

        load();
        return view;
    }

    private void load() {
        progressBar.setVisibility(View.VISIBLE);
        executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException ignored) {}

            ArrayList<Books> newLikedBooks = new ArrayList<>();
            for (Books book : DataDummy.books) {
                if (book.getLike() == 1) newLikedBooks.add(book);
            }

            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    likedBooks.clear();
                    likedBooks.addAll(newLikedBooks);
                    bookAdapter.notifyDataSetChanged();
                    updateUI();
                });
            }
        });
    }

    private void updateUI() {
        if (likedBooks.isEmpty()) {
            noBooksCard.setVisibility(View.VISIBLE);
            rv_books.setVisibility(View.GONE);
        } else {
            noBooksCard.setVisibility(View.GONE);
            rv_books.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }


    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver();
    }


    private void unregisterReceiver() {
        try {
            requireContext().unregisterReceiver(favUpdateReceiver);
        } catch (IllegalArgumentException ignored) {}
    }

    private final BroadcastReceiver favUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isAdded()) {
                refreshData();
            }
        }
    };

    private void refreshData() {
        new Handler(Looper.getMainLooper()).post(() -> {
            ArrayList<Books> newLikedBooks = new ArrayList<>();
            for (Books book : DataDummy.books) {
                if (book.getLike() == 1) newLikedBooks.add(book);
            }

            likedBooks.clear();
            likedBooks.addAll(newLikedBooks);
            bookAdapter.notifyDataSetChanged();
            updateUI();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (executorService != null) {
            executorService.shutdownNow();
        }
    }
}