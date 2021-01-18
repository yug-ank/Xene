package com.xenecompany.xene;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class hostel_view_adapter extends RecyclerView.Adapter<hostel_view_adapter.hostel_view_holder> {

    private List<hostel_cardview_model> items;
    int screenwidth;
    public hostel_view_adapter(List<hostel_cardview_model> items , int screenwidth) {
        this.items = items;
        this.screenwidth=screenwidth;
    }

    @NonNull
    @Override
    public hostel_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view;
            view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view , parent , false);
            ConstraintLayout.LayoutParams layoutParams=new ConstraintLayout.LayoutParams(screenwidth/2 , screenwidth/2);
            view.setLayoutParams(layoutParams);
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
