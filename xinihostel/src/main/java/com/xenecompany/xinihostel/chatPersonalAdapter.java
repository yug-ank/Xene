package com.xenecompany.xinihostel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.getColor;

public class chatPersonalAdapter extends RecyclerView.Adapter<chatPersonalAdapter.viewHolder> {
    ArrayList<chat_object> chat;
    Context context;

    public chatPersonalAdapter(ArrayList<chat_object> chat,  Context context){ this.chat = chat; this.context = context; }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view, null);
//        LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_recieve, null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        TextView msg = new TextView(context);
        msg.setLayoutParams( new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT ));
        if(chat.get(position).sender.equals("H")){
            msg.setBackgroundResource(R.drawable.sent_message);
            msg.setTextColor(getColor(context, R.color.black));
            msg.setText(chat.get(position).getText());
        }
        if(chat.get(position).sender.equals("U")) {
            msg.setBackgroundResource(R.drawable.received_message);
            msg.setTextColor(getColor(context, R.color.white));
            msg.setText(chat.get(position).getText());
        }
        holder.linearLayout.addView(msg);
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
        private LinearLayout linearLayout;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.sendMessageLinearLayout);
        }
    }
}
