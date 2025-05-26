package com.example.tp6;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tp6.databinding.ActivityDetailBinding;
import com.example.tp6.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding binding;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        int id = getIntent().getIntExtra("id", 0);

        apiService = RetrofitClient.getClient().create(ApiService.class);
        refresh(id);

        binding.refreshBtn.setOnClickListener(V -> {
            refresh(id);
        });
    }

    public void refresh(int id) {
        Call<User> call = apiService.getCharacter(id);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                binding.progressBar.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    User user = response.body();
                    binding.name.setText(user.getName());
                    binding.status.setText(user.getStatus());
                    binding.species.setText(user.getSpecies());
                    binding.gender.setText(user.getGender());
                    Picasso.get().load(user.getImage()).into(binding.image);
                    binding.progressBar.setVisibility(View.GONE);
                    binding.cardDetail.setVisibility(View.VISIBLE);
                    binding.refreshBtn.setVisibility(View.GONE);
                    binding.noInternetCard.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                new Handler().postDelayed(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.noInternetCard.setVisibility(View.VISIBLE);
                    binding.refreshBtn.setVisibility(View.VISIBLE);
                }, 2000);
            }
        });
    }
}