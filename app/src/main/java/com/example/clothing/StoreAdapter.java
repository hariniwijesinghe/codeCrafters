package com.example.clothing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private final List<Store> storeList;
    private final OnStoreClickListener listener;

    public interface OnStoreClickListener {
        void onStoreClick(int position);
    }

    public StoreAdapter(List<Store> storeList, OnStoreClickListener listener) {
        this.storeList = storeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_item, parent, false);
        return new StoreViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Store store = storeList.get(position);
        holder.tvStoreName.setText(store.getName());
        holder.ratingBar.setRating((float) store.getRating());
        holder.tvStoreHours.setText("Hours: " + store.getWorkingHours());
    }

    @Override
    public int getItemCount() {
        return storeList.size();
    }

    public static class StoreViewHolder extends RecyclerView.ViewHolder {

        TextView tvStoreName, tvStoreHours;
        RatingBar ratingBar;

        public StoreViewHolder(@NonNull View itemView, OnStoreClickListener listener) {
            super(itemView);
            tvStoreName = itemView.findViewById(R.id.tvStoreName);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tvStoreHours = itemView.findViewById(R.id.tvStoreHours);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onStoreClick(position);
                    }
                }
            });
        }
    }
}
