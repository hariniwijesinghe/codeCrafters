package com.example.clothing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchResultsFragment extends Fragment {

    private final List<Store> storeList = new ArrayList<>();
    private StoreAdapter storeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        storeAdapter = new StoreAdapter(storeList);
        recyclerView.setAdapter(storeAdapter);

        Button btnSortByRating = view.findViewById(R.id.btnSortByRating);
        Button btnSortByWorkingHours = view.findViewById(R.id.btnSortByWorkingHours);

        btnSortByRating.setOnClickListener(v -> sortAndDisplayStores("rating"));
        btnSortByWorkingHours.setOnClickListener(v -> sortAndDisplayStores("workingHours"));

        initFirebaseDb();

        return view;
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
                new Store("Store One", 4.5, "1234567890", "123 Example St", "9 AM to 9 PM", "http://www.example.com"),
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