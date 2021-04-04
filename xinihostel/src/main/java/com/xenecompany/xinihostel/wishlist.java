package com.xenecompany.xinihostel;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class wishlist extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    DrawerLayout drawer;
    int width;
    RecyclerView wishlistRecyclerView;
    ProgressBar wishlistProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.wishlistRefreshLayout);
        ////////toolbar
        toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.wishlistToolbar);
//        drawer = (DrawerLayout) findViewById(R.id.wishlist_with_navigation_drawer);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
        toolbar.inflateMenu(R.menu.menu_main);
        FrameLayout wishlistLayout=(FrameLayout)toolbar.getMenu().findItem(R.id.toolbar_wishlist).getActionView();
        ImageView imageView=(ImageView)wishlistLayout.findViewById(R.id.toolbarHeartImageView);
        imageView.setImageResource(R.drawable.toolbar_heart_selected);


        FrameLayout messageLayout=(FrameLayout)toolbar.getMenu().findItem(R.id.toolbar_message).getActionView();
        messageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView=(TextView)view.findViewById(R.id.messageCount);
                textView.setText("0");
                ImageView imageView=(ImageView)findViewById(R.id.toolbarMessageImageView);
                imageView.setImageResource(R.drawable.toolbar_message_selected);
            }
        });
        ////////toolbar
        DisplayMetrics displayMetrics= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width=displayMetrics.widthPixels;
        wishlistRecyclerView=(RecyclerView)findViewById(R.id.wishlistRecyclerView);
        wishlistProgressBar=(ProgressBar)findViewById(R.id.wishlistProgressBar);
        wishlistProgressBar.setVisibility(View.GONE);
        wishlistRecyclerView.setLayoutManager(new GridLayoutManager(this , 2));
        wishlistRecyclerView.setHasFixedSize(false);
        loadData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void loadData(){
        SessionManager sessionManager=new SessionManager(this);
        HashMap<String , String> sessionData = sessionManager.getUserDetailFromSession();
        final FirebaseFirestore db=FirebaseFirestore.getInstance();
        final PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(10)
                .build();
        db.collection("Hostels").document("+91"+sessionData.get(SessionManager.Key_Phone_no)).addSnapshotListener(
                new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value.exists()){
                            List<String> wishlist= (List<String>) value.get("wishlist");
                            if(wishlist.size()!=0) {
                                Query query = db.collection("Student").whereIn(FieldPath.documentId(), wishlist);
                                FirestorePagingOptions<StudentCardViewModel> options = new FirestorePagingOptions.Builder<StudentCardViewModel>()
                                        .setQuery(query, config, new SnapshotParser<StudentCardViewModel>() {
                                            @NonNull
                                            @Override
                                            public StudentCardViewModel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                                                StudentCardViewModel studentCardViewModel = snapshot.toObject(StudentCardViewModel.class);
                                                studentCardViewModel.setItemID(snapshot.getId());
                                                return studentCardViewModel;
                                            }
                                        })
                                        .build();
                                student_view_adapter student_view_adapter= new student_view_adapter(options);
                                student_view_adapter.startListening();
                                student_view_adapter.setScreenwidth(width);
                                student_view_adapter.setContext(wishlist.this);
                                student_view_adapter.setProgressBar(wishlistProgressBar);
                                wishlistRecyclerView.setAdapter(student_view_adapter);
                            }
                        }
                    }
                }
        );
    }
}