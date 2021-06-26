package com.xenecompany.xene.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.xenecompany.xene.R;
import com.xenecompany.xene.model.pgDetailsModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class pgDetailsAdapter extends RecyclerView.Adapter<pgDetailsAdapter.viewHolder> {
    public ArrayList<pgDetailsModel> data;
    Context c;

    public  pgDetailsAdapter(ArrayList<pgDetailsModel> data ,Context c){
        this.c=c;
        this.data=data;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.layout_pg_pictures_data,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if(data.get(position).img.length()>0)
             Picasso.get().load(data.get(position).img).noFade().into(holder.img);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.pgPicturesDataImg);
        }
    }
}
