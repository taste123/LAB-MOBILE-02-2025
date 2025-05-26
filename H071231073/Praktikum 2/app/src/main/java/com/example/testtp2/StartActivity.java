package com.example.testtp2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StartActivity extends AppCompatActivity {

    private TextView tvNama, tvUsername, tvEmail;
    private ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgProfile = findViewById(R.id.imgprofile);
        tvNama = findViewById(R.id.tvNama);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("user")) {
            User user = intent.getParcelableExtra("user");

            if (user != null) {
                tvNama.setText(user.getName());
                tvUsername.setText(user.getUsername());
                tvEmail.setText(user.getEmail());
            }

            assert user != null;
            String imageUrl = user.getImageUri();
            if (imageUrl != null && !imageUrl.isEmpty()) {
               Uri imageUri = Uri.parse(imageUrl);
               imgProfile.setImageURI(imageUri);
               imgProfile.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imgProfile.setImageResource(R.drawable.char1);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
                imgProfile.setLayoutParams(params);
            }
        }
    }
}