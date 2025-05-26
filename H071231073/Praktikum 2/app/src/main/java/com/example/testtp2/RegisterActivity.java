package com.example.testtp2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    private ImageView imgProfile;
    private EditText etNama, etUsername, etPassword, etEmail;
    private Button button;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        imgProfile = findViewById(R.id.imgprofile);
        etNama = findViewById(R.id.etNama);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        button = findViewById(R.id.submit);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nama = etNama.getText().toString().trim();
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String email = etEmail.getText().toString().trim();

                if (nama.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String imageUriString = "";
                if (imageUri == null) {
                    imageUriString = null;
                } else {
                    imageUriString = imageUri.toString();
                }

                    User user = new User(nama, username, password, email, imageUriString);
                    Intent intent = new Intent(RegisterActivity.this, StartActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
//                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imgProfile.setImageURI(imageUri);
        }
    }
}