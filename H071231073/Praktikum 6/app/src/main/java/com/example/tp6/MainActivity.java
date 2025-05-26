package com.example.tp6;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tp6.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private UserAdapter adapter;
    private ApiService apiService;
    AtomicInteger i = new AtomicInteger(1);

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

        apiService = RetrofitClient.getClient().create(ApiService.class);
        binding.rvUser.setHasFixedSize(true);
        binding.rvUser.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(new ArrayList<>());
        binding.rvUser.setAdapter(adapter);

        loadUsers(i.get());

        binding.loadBtn.setOnClickListener(v -> {
            binding.noInternetCard.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.VISIBLE);
            i.getAndIncrement();
            loadUsers(i.get());
        });
    }

    private void loadUsers(int i) {
        Call<UserResponse> call = apiService.getUsers(i);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    List<User> userList = response.body().getResults();
                    binding.progressBar.setVisibility(View.GONE);
                    adapter.addUsers(userList);
                    binding.rvUser.setVisibility(View.VISIBLE);
                    binding.loadBtn.setVisibility(View.VISIBLE);
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.noInternetCard.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                binding.loadBtn.setVisibility(View.GONE);
                new Handler().postDelayed(() -> {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.noInternetCard.setVisibility(View.VISIBLE);
                    binding.loadBtn.setVisibility(View.VISIBLE);
                }, 2000);
            }
        });
    }
}