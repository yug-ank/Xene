package com.xenecompany.xene;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class chatPersonalAdapter extends RecyclerView.Adapter<chatPersonalAdapter.viewHolder> {
    ArrayList<chat_object> chat;

    public chatPersonalAdapter(ArrayList<chat_object> chat){ this.chat = chat; }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view, null);
//        LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_recieve, null);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if(chat.get(position).sender.equals("U")){
//            holder.msgSend.setEnabled(true);
            holder.msgSend.setVisibility(View.VISIBLE);
            Log.i("yashwant", "done");
            holder.msgSend.setText(chat.get(position).getText());
        }
        if(chat.get(position).sender.equals("H")) {
//            holder.msgRecieve.setEnabled(true);
            holder.msgRecieve.setVisibility(View.VISIBLE);
            holder.msgRecieve.setText(chat.get(position).getText());
        }
    }

    @Override
    public int getItemCount() {
        return chat.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView msgSend , msgRecieve;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            msgSend = itemView.findViewById(R.id.textMessageSend);
            msgRecieve = itemView.findViewById(R.id.textMessageRecieve);
        }
    }
}
