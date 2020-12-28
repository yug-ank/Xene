package com.xenecompany.xene;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class home_banner_adapter extends PagerAdapter {
    private List<home_banner_modelClass> modelClassList;

    public home_banner_adapter(List<home_banner_modelClass> modelClassList) {
        this.modelClassList = modelClassList;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
             View view = LayoutInflater.from(container.getContext()).inflate(R.layout.home_banner_slider_dataview , container , false);
            ImageView banner =view.findViewById(R.id.home_banner_imageview);
            banner.setImageResource(modelClassList.get(position).getBanner());
            container.addView(view  , 0);
            return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
    public int getCount() {
        return modelClassList.size();
    }

}
