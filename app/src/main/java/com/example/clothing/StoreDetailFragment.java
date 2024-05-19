package com.example.clothing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StoreDetailFragment extends Fragment {

    private String storeName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_detail, container, false);

        if (getArguments() != null) {
            storeName = getArguments().getString("storeName");
        }

        TextView tvStoreName = view.findViewById(R.id.store_name_1);
        tvStoreName.setText(storeName);

        return view;
    }
}
