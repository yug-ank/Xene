package com.xenecompany.xene;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class my_deals extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_deals);

        ////////toolbar
        toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.dealtoolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        FrameLayout wishlistLayout=(FrameLayout)toolbar.getMenu().findItem(R.id.toolbar_wishlist).getActionView();
        wishlistLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(my_deals.this , wishlist.class);
                startActivity(intent);
            }
        });
        FrameLayout messageLayout=(FrameLayout)toolbar.getMenu().findItem(R.id.toolbar_message).getActionView();
        messageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView=(TextView)view.findViewById(R.id.messageCount);
                textView.setText("0");
                startActivity(new Intent(view.getContext(), chatAll.class));
            }
        });
        ////////toolbar



        DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int width=displayMetrics.widthPixels;
        SessionManager sessionManager=new SessionManager(this);
        HashMap<String , String> sessionData = sessionManager.getUserDetailFromSession();
        final FirebaseFirestore db=FirebaseFirestore.getInstance();
        final RecyclerView wishlistRecyclerView=(RecyclerView)findViewById(R.id.dealRecyclerView);
        final ProgressBar wishlistProgressBar=(ProgressBar)findViewById(R.id.dealProgressBar);
        wishlistProgressBar.setVisibility(View.GONE);
        wishlistRecyclerView.setLayoutManager(new GridLayoutManager(this , 2));
        wishlistRecyclerView.setHasFixedSize(false);
        final PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .build();
        db.collection("Student").document("+91"+sessionData.get(SessionManager.Key_Phone_no)).addSnapshotListener(
                new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value.exists()){
                            List<String> list= (List<String>) value.get("requested");
                            list.addAll((List<String>) value.get("accepted"));
                            if(list.size()!=0) {
                                Query query = db.collection("Hostels").whereIn(FieldPath.documentId(), list);
                                FirestorePagingOptions<hostel_cardview_model> options = new FirestorePagingOptions.Builder<hostel_cardview_model>()
                                        .setQuery(query, config, new SnapshotParser<hostel_cardview_model>() {
                                            @NonNull
                                            @Override
                                            public hostel_cardview_model parseSnapshot(@NonNull DocumentSnapshot snapshot){
                                                hostel_cardview_model hostelCardviewModel = snapshot.toObject(hostel_cardview_model.class);
                                                hostelCardviewModel.setItemID(snapshot.getId());
                                                return hostelCardviewModel;
                                            }
                                        })
                                        .build();
                                hostel_view_adapter hostelViewAdapter = new hostel_view_adapter(options);
                                hostelViewAdapter.startListening();
                                hostelViewAdapter.setScreenwidth(width);
                                hostelViewAdapter.setContext(my_deals.this);
                                hostelViewAdapter.setProgressBar(wishlistProgressBar);
                                wishlistRecyclerView.setAdapter(hostelViewAdapter);
                            }
                        }
                    }
                }
        );
    }
}