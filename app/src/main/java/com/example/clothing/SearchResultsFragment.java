package com.example.clothing;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class SearchResultsFragment extends Fragment implements StoreAdapter.OnStoreClickListener {

    private final List<Store> storeList = new ArrayList<>();
    private StoreAdapter storeAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        storeAdapter = new StoreAdapter(storeList, this);
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
                    addMultipleStoresToDatabase();
                }
                readDataFromDatabase();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Log error
            }
        });
    }

    private void addMultipleStoresToDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("stores");

        List<Store> storeList = Arrays.asList(
                new Store("Store One", 4.5, "1234567890", "123 Example St", "9 AM to 9 PM", "http://www.example.com"),
                new Store("Store Two", 4.3, "1234567891", "124 Example St", "10 AM to 8 PM", "http://www.storetwo.com"),
                new Store("Store Three", 4.7, "1234567892", "125 Example St", "8 AM to 10 PM", "http://www.storethree.com")
                // Additional stores can be added here
        );

        for (Store store : storeList) {
            String storeId = myRef.push().getKey();
            if (storeId != null) {
                myRef.child(storeId).setValue(store);
            }
        }
    }

    private void readDataFromDatabase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("stores");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                // Log error
            }
        });
    }

    private void sortAndDisplayStores(String sortBy) {
        if (sortBy.equals("rating")) {
            Collections.sort(storeList, Comparator.comparingDouble(Store::getRating).reversed());
        } else if (sortBy.equals("workingHours")) {
            Collections.sort(storeList, Comparator.comparing(Store::getWorkingHours).reversed());
        }
        storeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStoreClick(int position) {
        Store store = storeList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("storeName", store.getName());

        StoreDetailFragment storeDetailFragment = new StoreDetailFragment();
        storeDetailFragment.setArguments(bundle);

        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, storeDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}
