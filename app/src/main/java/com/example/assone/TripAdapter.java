package com.example.assone;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    ArrayList<Trip> trips;
    OnTripClick listener;
    Context context;

    public interface OnTripClick {
        void onEdit(Trip trip, int position);
        void onLongClick(int position);
        void onClick(Trip trip);
    }

    public TripAdapter(Context context, ArrayList<Trip> trips, OnTripClick listener) {
        this.context = context;
        this.trips = trips;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = trips.get(position);
        holder.tvLocation.setText(trip.location);
        holder.tvPrice.setText("Total: $" + trip.totalPrice);

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(trip, position));
        holder.btnDelete.setOnClickListener(v -> listener.onLongClick(position));
        holder.itemView.setOnClickListener(v -> listener.onClick(trip));
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView tvLocation, tvPrice;
        ImageButton btnEdit, btnDelete;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLocation = itemView.findViewById(R.id.tvTripLocation);
            tvPrice = itemView.findViewById(R.id.tvTripPrice);
            btnEdit = itemView.findViewById(R.id.btnEditTrip);
            btnDelete = itemView.findViewById(R.id.btnDeleteTrip);
        }
    }
}
