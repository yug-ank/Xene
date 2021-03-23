package com.xenecompany.xene;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.like.LikeButton;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

public class hostel_view_adapter extends FirestorePagingAdapter<hostel_cardview_model , hostel_view_adapter.hostel_view_holder> {

    int screenwidth;
    Context context;
    ProgressBar progressBar;
    View view;
    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
    public hostel_view_adapter(@NonNull FirestorePagingOptions<hostel_cardview_model> options) {
        super(options);
    }

    public void setContext(Context context) {
        this.context = context;
    }
    public void setScreenwidth(int screenwidth) {
        this.screenwidth = screenwidth;
    }
    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
    @Override
    protected void onBindViewHolder(@NonNull hostel_view_holder holder, int position, @NonNull final hostel_cardview_model model) {
        holder.hostelName.setText(model.getName());
        holder.hostelAddress.setText(model.getAddress());
        holder.hostelRating.setRating(model.getRating());
        Picasso.get().load(model.getBanner_image()).fit().into(holder.hostelImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                    Log.i("rectify" , ""+e);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , pgDetails.class);
                intent.putExtra("ItemId" , model.getItemID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        switch (state){
            case LOADING_INITIAL:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
            case LOADED:
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
            case LOADING_MORE:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
            case FINISHED:
                    Toast.makeText(context , "end of list" , Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
            case ERROR:
                    Toast.makeText(context , "network error" , Toast.LENGTH_SHORT).show();
                    break;
        }
    }

    @NonNull
    @Override
    public hostel_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(screenwidth/2 , screenwidth/2);
        view.setLayoutParams(layoutParams);
        return new hostel_view_holder(view);
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
