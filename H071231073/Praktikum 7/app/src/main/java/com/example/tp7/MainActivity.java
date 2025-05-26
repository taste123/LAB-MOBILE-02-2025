package com.example.tp7;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tp7.database.BookHelper;
import com.example.tp7.databinding.ActivityMainBinding;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BookAdapter adapter;
    private BookHelper bookHelper;
    private final int REQUEST_ADD = 100;
    private final int REQUEST_UPDATE = 200;
    private ArrayList<Book> allBooks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Book List");
        }

        binding.searchBar.clearFocus();
        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchResult(newText);
                return true;
            }
        });
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BookAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        bookHelper = BookHelper.getInstance(getApplicationContext());

        binding.fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FormActivity.class);
            startActivityForResult(intent, REQUEST_ADD);
        });
        loadBooks();
    }

    private void searchResult(String newText) {
        ArrayList<Book> filteredBooks = new ArrayList<>();
        for (Book book : allBooks) {
            if (book.getJudul().toLowerCase().contains(newText.toLowerCase()) || book.getDeskripsi().toLowerCase().contains(newText.toLowerCase())) {
                filteredBooks.add(book);
            }
        }
        if (filteredBooks.isEmpty()) {
            adapter.setBooks(new ArrayList<>());
            binding.noInternetCard.setVisibility(View.VISIBLE);
        } else {
            adapter.setBooks(filteredBooks);
            binding.noInternetCard.setVisibility(View.GONE);
        }
    }

    private void loadBooks() {
        new LoadBooksAsync(this, new LoadBooksCallback() {
            @Override
            public void postExecute(ArrayList<Book> books) {
                allBooks = books;
                if (books.size() > 0) {
                    adapter.setBooks(books);
                    binding.noInternetCard.setVisibility(View.GONE);
                } else {
                    adapter.setBooks(new ArrayList<>());
                    binding.noInternetCard.setVisibility(View.VISIBLE);
                }
            }
        }).execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD) {
            if (resultCode == FormActivity.RESULT_ADD) {
                Toast.makeText(this, "book added sucessfully", Toast.LENGTH_SHORT).show();
                loadBooks();
            }
        } else if (requestCode == REQUEST_UPDATE) {
            if (resultCode == FormActivity.RESULT_UPDATE) {
                Toast.makeText(this, "book updated sucessfully", Toast.LENGTH_SHORT).show();
                loadBooks();
            } else if (resultCode == FormActivity.RESULT_DELETE) {
                Toast.makeText(this, "book deleted sucessfully", Toast.LENGTH_SHORT).show();
                loadBooks();
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (bookHelper != null) {
            bookHelper.close();
        }
    }

    interface LoadBooksCallback {
        void postExecute(ArrayList<Book> books);
    }

    private static class LoadBooksAsync {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadBooksCallback> weakCallback;

        private LoadBooksAsync(Context context, LoadBooksCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                Context context = weakContext.get();
                if (context != null) {
                    BookHelper bookHelper = BookHelper.getInstance(context);
                    bookHelper.open();
                    Cursor bookscursor = bookHelper.queryAll();
                    ArrayList<Book> books = MappingHelper.mapCursorToArrayList(bookscursor);
                    bookscursor.close();

                    handler.post(() -> {
                        LoadBooksCallback callback = weakCallback.get();
                        if (callback != null) {
                            callback.postExecute(books);
                        }
                    });
                }
            });
        }
    }
}