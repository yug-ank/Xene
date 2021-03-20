package com.xenecompany.xinihostel;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class homePageParentRecyclerView extends RecyclerView.Adapter {

    private static Context context;
    private static int width;
    private static ProgressBar progressBar;
    public homePageParentRecyclerView(@NonNull Context context, int width , ProgressBar progressBar) {
        this.context = context;
        this.width = width;
        this.progressBar=progressBar;
        progressBar.setVisibility(View.GONE
        );

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0){
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.homepage_adbanner , parent , false);
                return new adBanner(view);
            }
            else if(viewType==1){
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.homepagehostelrecycleview , parent , false);
                return new hostelView(view);
            }
            return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //////////////ad banner
        if(position==0){
            final ArrayList<home_banner_modelClass> modelClassList = new ArrayList<>();
            FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
            CollectionReference collectionReference= firebaseFirestore.collection("Promotion");
            collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(!queryDocumentSnapshots.isEmpty()){
                        List<DocumentSnapshot> list= queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d: list){
                            home_banner_modelClass home=d.toObject(home_banner_modelClass.class);
                            modelClassList.add(home);
                        }
                    }
                }
            });
         //   ((adBanner)holder).setBannerSliderViewPager(modelClassList);
        }
        //////////////ad banner

        /////////////hostelview
        else if(position==1){
            ((hostelView)holder).setRecyclerView(width , context , progressBar);
        }
        /////////////hostelview
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private static class adBanner extends RecyclerView.ViewHolder{
        private ViewPager bannerSliderViewPager;
        private int currentPage=2;
        Timer timer;
        private  final  long DELAY_TIME=3000;
        private  final  long PERIOD_TIME=3000;
        public adBanner(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.homePageAdBanner);
        }
        private void setBannerSliderViewPager(final List<home_banner_modelClass> modelClassList){
            home_banner_adapter homeBannerAdapter=new home_banner_adapter(modelClassList);
            bannerSliderViewPager.setAdapter(homeBannerAdapter);
            homeBannerAdapter.notifyDataSetChanged();
            bannerSliderViewPager.setCurrentItem(currentPage);
            bannerSliderViewPager.setClipToPadding(false);
            bannerSliderViewPager.setPageMargin(20);
            ViewPager.OnPageChangeListener onPageChangeListener=new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage=position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if(state==ViewPager.SCROLL_STATE_IDLE){
                        pageLooper(modelClassList);
                    }
                }
            };
            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
            startBannerAnimation(modelClassList);
            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    pageLooper(modelClassList);
                    stopBannerAnimation();
                    if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                        startBannerAnimation(modelClassList);
                    }
                    return false;
                }
            });
        }
        private void startBannerAnimation(final List<home_banner_modelClass> modelClassList){
            final Handler handler= new Handler();
            final Runnable update=new Runnable() {
                @Override
                public void run() {
                    if(currentPage>=modelClassList.size()){
                        currentPage=1;
                    }
                    bannerSliderViewPager.setCurrentItem(currentPage++ , true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            } , DELAY_TIME , PERIOD_TIME);
        }
        private void stopBannerAnimation(){
            timer.cancel();
        }
        private void pageLooper(List<home_banner_modelClass> modelClassList){
            if(currentPage==modelClassList.size()-2){
                currentPage=2;
                bannerSliderViewPager.setCurrentItem(currentPage , false);
            }
            if(currentPage==1){
                currentPage=modelClassList.size()-3;
                bannerSliderViewPager.setCurrentItem(currentPage , false);
            }
        }
    }

    private static class hostelView extends RecyclerView.ViewHolder{
        private RecyclerView recyclerView;
        public hostelView(@NonNull View itemView) {
            super(itemView);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.hostelRecyclerView);
        }
        private void setRecyclerView(final int width , final Context context , final ProgressBar progressBar){
            SessionManager sessionManager;
            HashMap<String , String> sessionData;
            sessionManager= new SessionManager(context);
            sessionData=sessionManager.getUserDetailFromSession();
            recyclerView.setLayoutManager(new GridLayoutManager(context , 2));
            recyclerView.setHasFixedSize(false);
            final PagedList.Config config = new PagedList.Config.Builder()
                    .setInitialLoadSizeHint(10)
                    .setPageSize(10)
                    .build();
            final FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
            firebaseFirestore.collection("Hostels").document("+91" + sessionData.get(SessionManager.Key_Phone_no))
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                             if(value.exists()) {
                                 List<String> total = (List<String>) value.get("requested");
                                 total.addAll((Collection<? extends String>) value.get("accepted"));
                                 if (total.size() != 0) {
                                     Query query = firebaseFirestore.collection("Student")
                                             .whereIn(FieldPath.documentId(), total);
                                     FirestorePagingOptions<StudentCardViewModel> options = new FirestorePagingOptions.Builder<StudentCardViewModel>()
                                             .setQuery(query, config, new SnapshotParser<StudentCardViewModel>() {
                                                 @NonNull
                                                 @Override
                                                 public StudentCardViewModel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                                                     StudentCardViewModel hostelCardviewModel = snapshot.toObject(StudentCardViewModel.class);
                                                     hostelCardviewModel.setItemID(snapshot.getId());
                                                     return hostelCardviewModel;
                                                 }
                                             })
                                             .build();
                                     student_view_adapter hostelViewAdapter = new student_view_adapter(options);
                                     hostelViewAdapter.startListening();
                                     hostelViewAdapter.setScreenwidth(width);
                                     hostelViewAdapter.setContext(context);
                                     hostelViewAdapter.setProgressBar(progressBar);
                                     recyclerView.setAdapter(hostelViewAdapter);
                                 }
                             }
                        }
                    });
        }
    }
}
