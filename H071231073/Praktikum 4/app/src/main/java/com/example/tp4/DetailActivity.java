package com.example.tp4;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    private ImageView cover;
    private TextView title, writer, year, genre, sinopsis;
    private Button backBtn, favBtn;
    private Books book;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        cover = findViewById(R.id.cover);
        title = findViewById(R.id.title);
        writer = findViewById(R.id.writer);
        year = findViewById(R.id.year);
        genre = findViewById(R.id.genre);
        sinopsis = findViewById(R.id.sinopsis);
        backBtn = findViewById(R.id.backBtn);
        favBtn = findViewById(R.id.favBtn);

        book = getIntent().getParcelableExtra("book");
        position = getIntent().getIntExtra("position", -1);

        if (book != null && position != -1) {
            title.setText(book.getTitle());
            writer.setText(book.getWriter());
            year.setText(String.valueOf(book.getYear()));
            genre.setText(book.getGenre());
            sinopsis.setText(book.getBlurb());

            Uri uriCover = Uri.parse(book.getCover());
            Glide.with(this).load(uriCover).into(cover);

            setupFavoriteButton();
        }

        backBtn.setOnClickListener(v -> finish());
    }

    private void setupFavoriteButton() {
        updateFavButtonText(book.getLike());

        favBtn.setOnClickListener(v -> {
            int newLikeStatus = book.getLike() == 0 ? 1 : 0;

            book.setLike(newLikeStatus);
            DataDummy.books.get(position).setLike(newLikeStatus);

            updateFavButtonText(newLikeStatus);
            notifyFavFragment();
        });
    }

    private void updateFavButtonText(int likeStatus) {
        favBtn.setText(likeStatus == 0 ? "Add to Favorite" : "Remove Favorite");
    }

    private void notifyFavFragment() {
        Intent intent = new Intent("BOOK_FAVORITE_CHANGED");
        sendBroadcast(intent);
    }
}