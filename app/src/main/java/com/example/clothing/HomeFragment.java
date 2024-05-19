package com.example.clothing;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.w3c.dom.Text;


public class HomeFragment extends Fragment {

    private EditText searchEditText;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize views
        searchEditText = rootView.findViewById(R.id.search_edittext);

        // Set OnClickListener to search EditText
        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SearchResultsFragment when search EditText is clicked
                navigateToSearchResults();
            }
        });

        return rootView;
    }

    // Method to navigate to SearchResultsFragment
    private void navigateToSearchResults() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout, new SearchResultsFragment())
                    .commit();
        }
    }
}