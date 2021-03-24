package com.xenecompany.xinihostel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class chat_all_adapter extends RecyclerView.Adapter<chat_all_adapter.viewHolder> {
    private ArrayList<ChatObject> chatList;
    private Context context;

    public chat_all_adapter(ArrayList<ChatObject> chatList , Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_card_view, null);
        return new viewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        holder.name.setText(chatList.get(position).getUserName());
        Picasso.get().load(chatList.get(position).getProfilePicture()).into(holder.profilePic);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatPersonal.class);
                intent.putExtra("name", chatList.get(position).getUserName());
                intent.putExtra("profilePicture", chatList.get(position).getProfilePicture());
                intent.putExtra("chatroom", chatList.get(position).getChatroomId());
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView profilePic;
        private CardView cardView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.activityChatCard_name);
            profilePic = itemView.findViewById(R.id.activityChatCard_profilePicture);
            cardView = itemView.findViewById(R.id.activityChatCard_cardView);
        }
    }
}
