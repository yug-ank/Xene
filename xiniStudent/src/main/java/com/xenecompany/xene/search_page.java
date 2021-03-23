package com.xenecompany.xene;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class search_page extends AppCompatActivity {
    private SearchView searchView;
    private RecyclerView recyclerView;
    private DatabaseReference db;
    private LinearLayout recentSearches;
    private boolean recentSearchesFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        recentSearchesFlag=true;
        db = FirebaseDatabase.getInstance().getReference("Hostels");
        recentSearches = findViewById(R.id.activitySearchRecentSearch);
        searchView = (SearchView) findViewById(R.id.activitySearchSearchInput);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(recentSearchesFlag) {
                    recentSearchesFlag=false;
                    recentSearches.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
        }
}