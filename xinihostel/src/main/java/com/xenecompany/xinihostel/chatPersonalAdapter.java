package com.xenecompany.xinihostel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class chatPersonalAdapter extends RecyclerView.Adapter<chatPersonalAdapter.viewHolder> {
    ArrayList<chat_object> chat;
    Context context;
    int width;

    public chatPersonalAdapter(ArrayList<chat_object> chat, Context context, int width) {
        this.chat = chat;
        this.context = context;
        this.width = width;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view, null);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(width , ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0 , 10 , 0  , 0);
        view.setLayoutParams(layoutParams);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if(chat.get(position).sender.equals("H")){
            Log.i("rectify" , "sent");
            holder.recievedMessageTextView.setVisibility(View.VISIBLE);
            holder.recievedMessageTextView.setText(chat.get(position).getText());
        }
        if(chat.get(position).sender.equals("U")) {
            Log.i("rectify" , "received");
            holder.sentMessageTextView.setVisibility(View.VISIBLE);
            holder.sentMessageTextView.setText(chat.get(position).getText());
        }
    }

    @Override
    public int getItemCount() {
        return chat.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView sentMessageTextView;
        private TextView recievedMessageTextView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            sentMessageTextView=itemView.findViewById(R.id.chatSentMessageTextView);
            recievedMessageTextView=itemView.findViewById(R.id.chatRecievedMessageTextView);
        }
    }
}
