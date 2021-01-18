package com.xenecompany.xene;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class homePageParentRecyclerView extends RecyclerView.Adapter {

    private static Context context;
    private static int width;
    public homePageParentRecyclerView(@NonNull Context context, int width) {
        this.context = context;
        this.width = width;
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
            List<home_banner_modelClass> modelClassList;
            modelClassList = new ArrayList<home_banner_modelClass>();
            modelClassList.add(new home_banner_modelClass(R.drawable.toolbar_message));
            modelClassList.add(new home_banner_modelClass(R.drawable.ic_icons8_sms));
            modelClassList.add(new home_banner_modelClass(R.drawable.demo_home_banner_image));
            modelClassList.add(new home_banner_modelClass(R.drawable.ic_menu_camera));
            modelClassList.add(new home_banner_modelClass(R.drawable.toolbar_message));
            modelClassList.add(new home_banner_modelClass(R.drawable.ic_icons8_sms));
            modelClassList.add(new home_banner_modelClass(R.drawable.demo_home_banner_image));
            modelClassList.add(new home_banner_modelClass(R.drawable.ic_menu_camera));
            ((adBanner)holder).setBannerSliderViewPager(modelClassList);
        }
        //////////////ad banner

        /////////////hostelview
        else if(position==1){
            List<hostel_cardview_model> items= new ArrayList<hostel_cardview_model>();
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
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            items.add(hostelCardviewModel);
            ((hostelView)holder).setRecyclerView(items , width , context);
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
        private void setRecyclerView(List<hostel_cardview_model> modelClassList , int width ,Context context){
            recyclerView.setLayoutManager(new GridLayoutManager(context , 2));
            recyclerView.setHasFixedSize(false);
            hostel_view_adapter hostelViewAdapter=new hostel_view_adapter(modelClassList , width);
            hostelViewAdapter.notifyDataSetChanged();
            recyclerView.setAdapter(hostelViewAdapter);
        }
    }
}
