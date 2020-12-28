package com.xenecompany.xene;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class hostel_view_adapter extends RecyclerView.Adapter<hostel_view_adapter.hostel_view_holder> {

    private ArrayList<hostel_cardview_model> items;

    public hostel_view_adapter(ArrayList<hostel_cardview_model> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public hostel_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view , parent , false);
            hostel_view_holder hostelViewHolder=new hostel_view_holder(view);
            return hostelViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull hostel_view_holder holder, int position) {
            hostel_cardview_model currentItem=items.get(position);
            holder.hostelImage.setImageResource(currentItem.getImage());
            holder.hostelName.setText(currentItem.getName());
            holder.hostelAddress.setText(currentItem.getAddress());
            holder.hostelRating.setRating(currentItem.getRating());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class hostel_view_holder extends RecyclerView.ViewHolder {
            ImageView hostelImage;
            TextView  hostelName;
            TextView hostelAddress;
            RatingBar hostelRating;
            public hostel_view_holder(@NonNull View itemView) {
                super(itemView);
                hostelImage=itemView.findViewById(R.id.hostel_image);
                hostelName=itemView.findViewById(R.id.hostel_name);
                hostelAddress=itemView.findViewById(R.id.hostel_address);
                hostelRating=itemView.findViewById(R.id.hostel_rating);
            }
        }
}
