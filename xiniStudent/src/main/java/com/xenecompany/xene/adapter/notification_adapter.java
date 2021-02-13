package com.xenecompany.xene.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xenecompany.xene.R;
import com.xenecompany.xene.model.notification_model;

import java.util.ArrayList;

public class notification_adapter extends RecyclerView.Adapter<notification_adapter.ViewHolder> {
    ArrayList<notification_model> data;
    public notification_adapter(ArrayList<notification_model> data){
        this.data=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notification_data,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        notification_model obj = data.get(position);
        holder.img.setImageResource(R.drawable.ok);
        holder.heading.setText(obj.heading);
        holder.details.setText(obj.details);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView heading ,details;
        public ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.textView13);
            details = itemView.findViewById(R.id.textView14);
            img = itemView.findViewById(R.id.imageView5);
        }
    }
}
