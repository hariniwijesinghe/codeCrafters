package com.example.storedbtest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
public class MainActivity extends AppCompatActivity {

    private List<Store> storeList = new ArrayList<>();
    private StoreAdapter storeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        storeAdapter = new StoreAdapter(storeList);
        recyclerView.setAdapter(storeAdapter);

        Button btnSortByRating = findViewById(R.id.btnSortByRating);
        Button btnSortByWorkingHours = findViewById(R.id.btnSortByWorkingHours);

        btnSortByRating.setOnClickListener(v -> sortAndDisplayStores("rating"));
        btnSortByWorkingHours.setOnClickListener(v -> sortAndDisplayStores("workingHours"));

        // Initialize the database and UI once
        initFirebaseDb();
    }

    private void initFirebaseDb() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("stores");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // If no data exists, add the stores
                    addMultipleStoresToDatabase();
                }
                // Always read data
                readDataFromDatabase();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Firebase", "Failed to read value.", databaseError.toException());
            }
        });
    }

    private void addMultipleStoresToDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("stores");

        List<Store> storeList = Arrays.asList(
                new Store("Example Store", 4.5, "1234567890", "123 Example St", "9 AM to 9 PM", "http://www.example.com"),
                new Store("Store Two", 4.3, "1234567891", "124 Example St", "10 AM to 8 PM", "http://www.storetwo.com"),
                new Store("Store Three", 4.7, "1234567892", "125 Example St", "8 AM to 10 PM", "http://www.storethree.com"),
                new Store("Store Four", 4.5, "1234567890", "123 Example St", "9 AM to 9 PM", "http://www.example.com"),
                new Store("Store Five", 4.3, "1234567891", "124 Example St", "10 AM to 8 PM", "http://www.storetwo.com"),
                new Store("Store Six", 4.7, "1234567892", "125 Example St", "8 AM to 10 PM", "http://www.storethree.com"),
                new Store("Store Seven", 4.5, "1234567890", "123 Example St", "9 AM to 9 PM", "http://www.example.com"),
                new Store("Store Eight", 4.3, "1234567891", "124 Example St", "10 AM to 8 PM", "http://www.storetwo.com"),
                new Store("Store Nine", 4.7, "1234567892", "125 Example St", "8 AM to 10 PM", "http://www.storethree.com")
                // Additional stores can be added here
        );

        for (Store store : storeList) {
            String storeId = myRef.push().getKey(); // Generate a new unique key for each store
            if (storeId != null) {
                myRef.child(storeId).setValue(store)
                        .addOnSuccessListener(aVoid -> Log.d("Firebase", "Store added successfully: " + store.getName()))
                        .addOnFailureListener(e -> Log.e("Firebase", "Failed to add store: " + store.getName(), e));
            }
        }
    }

    private void readDataFromDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("stores");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                storeList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Store store = snapshot.getValue(Store.class);
                    if (store != null) {
                        storeList.add(store);
                    }
                }
                storeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("Firebase", "Failed to read value.", databaseError.toException());
            }
        });
    }

    private void sortAndDisplayStores(String criteria) {
        if (criteria.equals("rating")) {
            // Sort by rating in descending order
            Collections.sort(storeList, (store1, store2) -> Double.compare(store2.getRating(), store1.getRating()));
        } else if (criteria.equals("workingHours")) {
            // Sort by working hours in alphabetical order (not really meaningful, but kept as an example)
            Collections.sort(storeList, Comparator.comparing(Store::getWorkingHours));
        }
        storeAdapter.notifyDataSetChanged();
    }
}
