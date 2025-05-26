package com.example.tp5;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tp5.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.homeBtn.setOnClickListener(v -> replaceFragment(new HomeFragment()));
        binding.upBtn.setOnClickListener(v -> replaceFragment(new UploadFragment()));
        binding.favBtn.setOnClickListener(v -> replaceFragment(new FavFragment()));
        replaceFragment(new HomeFragment());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment instanceof HomeFragment) {
            transaction.replace(R.id.fragment_container, fragment, "homeFragment");
        } else {
            transaction.replace(R.id.fragment_container, fragment);
        }
        transaction.commit();
    }

    public void notifyBooksAdapter() {
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
        if (homeFragment != null) {
            homeFragment.refreshBooks();
        }
    }
}