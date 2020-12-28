package com.xenecompany.xene;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends Activity {

    private androidx.appcompat.widget.Toolbar toolbar;

    /////banner view
        private ViewPager bannerSliderViewPager;
        private List<home_banner_modelClass> modelClassList;
        private int currentPage=2;
        Timer timer;
        private  final  long DELAY_TIME=3000;
        private  final  long PERIOD_TIME=3000;
    /////banner view


    /////hostel recycler view
        private RecyclerView hostel_recyclerview;
        hostel_view_adapter hostelViewAdapter;
    /////hostel recycler view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        SessionManager sessionManager = new SessionManager(HomePage.this);
        HashMap<String , String> userdata=sessionManager.getUserDetailFromSession();
        toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setTitle("XENE");


        /////banner View
        bannerSliderViewPager = findViewById(R.id.home_banner_slider);
        modelClassList = new ArrayList<home_banner_modelClass>();


        modelClassList.add(new home_banner_modelClass(R.drawable.toolbar_message));
        modelClassList.add(new home_banner_modelClass(R.drawable.ic_icons8_sms));

        modelClassList.add(new home_banner_modelClass(R.drawable.demo_home_banner_image));
        modelClassList.add(new home_banner_modelClass(R.drawable.ic_menu_camera));
        modelClassList.add(new home_banner_modelClass(R.drawable.toolbar_message));
        modelClassList.add(new home_banner_modelClass(R.drawable.ic_icons8_sms));

        modelClassList.add(new home_banner_modelClass(R.drawable.demo_home_banner_image));
        modelClassList.add(new home_banner_modelClass(R.drawable.ic_menu_camera));


        home_banner_adapter homeBannerAdapter=new home_banner_adapter(modelClassList);
        bannerSliderViewPager.setAdapter(homeBannerAdapter);
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
                        pageLooper();
                    }
            }
        };
        bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
        startBannerAnimation();
        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pageLooper();
                stopBannerAnimation();
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    startBannerAnimation();
                }
                return false;
            }
        });
        ////banner View

        /////hostel recycler view
            ArrayList<hostel_cardview_model> items= new ArrayList<>();
            int image=R.drawable.demo_hostel;
            String name="XYZ";
            String add="ABC";
            float rating= (float) 2.5;
            hostel_cardview_model hostelCardviewModel=new hostel_cardview_model(image , name , add , rating);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);

            hostel_recyclerview=(RecyclerView)findViewById(R.id.home_hostel_view);
            GridLayoutManager gridLayoutManager=new GridLayoutManager(this , 2);
            hostel_recyclerview.setLayoutManager(new GridLayoutManager(this , 2));
            hostel_recyclerview.setHasFixedSize(false);
            hostelViewAdapter = new hostel_view_adapter(items);
            hostelViewAdapter.notifyDataSetChanged();
            hostel_recyclerview.setAdapter(hostelViewAdapter);
        /////hostel recycler view

    }

    /////banner View
    private void startBannerAnimation(){
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
    private void pageLooper(){
                if(currentPage==modelClassList.size()-2){
                    currentPage=2;
                    bannerSliderViewPager.setCurrentItem(currentPage , false);
                }
                if(currentPage==1){
                    currentPage=modelClassList.size()-3;
                    bannerSliderViewPager.setCurrentItem(currentPage , false);
                }
        }
    /////banner View

}