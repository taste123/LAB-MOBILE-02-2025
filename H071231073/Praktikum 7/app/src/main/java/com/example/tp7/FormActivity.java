package com.example.tp7;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tp7.database.BookHelper;
import com.example.tp7.database.DatabaseContract;
import com.example.tp7.databinding.ActivityFormBinding;

public class FormActivity extends AppCompatActivity {

    private ActivityFormBinding binding;
    public static final String EXTRA_BOOK = "extra_book";
    public static final int RESULT_ADD = 101;
    public static final int RESULT_UPDATE = 201;
    public static final int RESULT_DELETE = 301;
    public static final int REQUEST_UPDATE = 200;

    private Book book;
    private boolean isEdit = false;
    private BookHelper bookHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bookHelper = BookHelper.getInstance(getApplicationContext());
        bookHelper.open();

        book = getIntent().getParcelableExtra(EXTRA_BOOK);
        if (book != null) {
            isEdit = true;
        } else {
            book = new Book();
        }

        String actionBarTitle;
        String buttonTitle;

        if (isEdit) {
            actionBarTitle = "Edit Book";
            buttonTitle = "Update";
            if (book != null) {
                binding.etJudul.setText(book.getJudul());
                binding.etDeskripsi.setText(book.getDeskripsi());
            }
            binding.btnDelete.setVisibility(View.VISIBLE);
//            binding.btnDelete.setOnClickListener(view -> {
//                bookHelper.deleteById(String.valueOf(book.getId()));
//                setResult(RESULT_DELETE, null);
//                finish();
//            });
        } else {
            actionBarTitle = "Add Book";
            buttonTitle = "Save";
        }

        binding.btnSave.setText(buttonTitle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        binding.btnSave.setOnClickListener(view -> saveBook());
        binding.btnDelete.setOnClickListener(view -> deleteBook());
    }

    private void saveBook() {
        String judul = binding.etJudul.getText().toString().trim();
        String deskripsi = binding.etDeskripsi.getText().toString().trim();

        if (judul.isEmpty()) {
            binding.etJudul.setError("Judul is required");
            return;
        }
        if (deskripsi.isEmpty()) {
            binding.etDeskripsi.setError("Deskripsi is required");
            return;
        }

        String curentTime = getCurrentTime();

        book.setJudul(judul);
        book.setDeskripsi(deskripsi);
        book.setInfo(curentTime);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_BOOK, book);

        ContentValues values = new ContentValues();
        values.put(DatabaseContract.BookColumns.JUDUL, judul);
        values.put(DatabaseContract.BookColumns.DESKRIPSI, deskripsi);
        values.put(DatabaseContract.BookColumns.INFO, curentTime);

        if (isEdit) {
            long result = bookHelper.update(String.valueOf(book.getId()), values);
            if (result > 0) {
                setResult(RESULT_UPDATE, intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to update data", Toast.LENGTH_SHORT).show();
            }
        } else {
            long result = bookHelper.insert(values);
            if (result > 0) {
                book.setId((int)result);
                setResult(RESULT_ADD, intent);
                finish();
            } else {
                Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getCurrentTime() {
        long currentTimeMillis = System.currentTimeMillis();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (isEdit) {
            return "updated at " + sdf.format(new java.util.Date(currentTimeMillis));
        } else {
            return "created at " + sdf.format(new java.util.Date(currentTimeMillis));
        }
    }

    private void deleteBook() {
        if (book != null && book.getId() > 0) {
            long result = bookHelper.deleteById(String.valueOf(book.getId()));
            if (result > 0) {
                setResult(RESULT_DELETE, null);
                finish();
            } else {
                Toast.makeText(this, "Failed to delete data", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No data to delete", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bookHelper != null) {
            bookHelper.close();
        }
    }
}