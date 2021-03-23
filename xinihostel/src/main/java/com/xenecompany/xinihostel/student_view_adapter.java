package com.xenecompany.xinihostel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class student_view_adapter extends FirestorePagingAdapter<StudentCardViewModel, student_view_adapter.hostel_view_holder> {

    int screenwidth;
    Context context;
    ProgressBar progressBar;
    View view;
    /**
     * Construct a new FirestorePagingAdapter from the given {@link FirestorePagingOptions}.
     *
     * @param options
     */
    public student_view_adapter(@NonNull FirestorePagingOptions<StudentCardViewModel> options) {
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
    protected void onBindViewHolder(@NonNull hostel_view_holder holder, int position, @NonNull final StudentCardViewModel model) {
                        holder.studentName.setText(model.getName());
                        holder.studentInstituteName.setText(model.getInstituteName());
                        if(model.getProfilePicture().length()>0) {
                            Picasso.get().load(model.getProfilePicture()).fit().into(holder.studentImage, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {
                                    Log.i("rectify", "" + e);
                                }
                            });
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(context, studentDetails.class);
                                    intent.putExtra("ItemId", model.getItemID());
                                    intent.putExtra("token" , model.getToken());
                                    context.startActivity(intent);
                                }
                            });
                        }
                        else{
                            Picasso.get().load(R.drawable.ic_male_avatr).fit().into(holder.studentImage);
                        }
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
            ImageView studentImage;
            TextView  studentName;
            TextView studentInstituteName;
            public hostel_view_holder(@NonNull View itemView) {
                super(itemView);
                studentImage=(ImageView)itemView.findViewById(R.id.studentImage);
                studentName=(TextView)itemView.findViewById(R.id.studentName);
                studentInstituteName=(TextView)itemView.findViewById(R.id.studentInstituteName);
            }
        }
}
