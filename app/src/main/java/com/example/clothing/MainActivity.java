package com.example.clothing;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

        // Add button to open MainActivity2
        Button btnOpenMainActivity2 = new Button(this);
        btnOpenMainActivity2.setText("Open MainActivity2");
        btnOpenMainActivity2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });
        binding.getRoot().addView(btnOpenMainActivity2);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
