package com.example.clothing;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.clothing.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.profile) {
                replaceFragment(new MyProfileFragment());
            } else if (item.getItemId() == R.id.review) {
                replaceFragment(new ReviewFragment());
            } else if (item.getItemId() == R.id.fav) {
                replaceFragment(new Collection());
            } else if (item.getItemId() == R.id.menu) {
                replaceFragment(new HomeFragment());
            }

            return true;
        });

        // Add OnClickListener to the search EditText
        binding.frameLayout.setOnClickListener(view -> {
            // Replace current fragment with SearchResultsFragment when search EditText is clicked
            replaceFragment(new SearchResultsFragment());
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}