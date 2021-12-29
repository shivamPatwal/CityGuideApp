package com.example.cityguide.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.bumptech.glide.Glide;
import com.example.cityguide.R;
import com.example.cityguide.adapters.categoryAdapter;
import com.example.cityguide.databinding.ActivityDetailBinding;
import com.example.cityguide.fragments.fragmentInfo;
import com.example.cityguide.fragments.fragmentPhoto;
import com.example.cityguide.fragments.fragmentReview;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class detailActivity extends AppCompatActivity {
ImageView image;
    private ActivityDetailBinding binding;
    private detailActivity activity;
    private viewPagerAdapter adapter;
    CollapsingToolbarLayout text;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    binding=DataBindingUtil.setContentView(this,R.layout.activity_detail);
    activity=this;
image=findViewById(R.id.image);
text=findViewById(R.id.toolbar);

categoryAdapter a=new categoryAdapter();
        Glide.with(getApplicationContext()).load(a.getphotoiurl()).into(image);

        text.setTitle(a.getpplacename());


    initView();
    }

    private void initView() {

    adapter=new viewPagerAdapter(activity.getSupportFragmentManager(),activity.getLifecycle());
    adapter.addFragement(new fragmentInfo(),"INFO" );
        adapter.addFragement(new fragmentPhoto(),"PHOTO");
        adapter.addFragement(new fragmentReview(),"REVIEWS" );
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(1);
        new TabLayoutMediator(binding.tabLayout,binding.viewPager,
                (tab, position) -> {
tab.setText(adapter.fragmentTitleList.get(position));
                }).attach();

       for(int i=0;i<binding.tabLayout.getTabCount();i++){

           TextView tv = (TextView) LayoutInflater.from(activity)
                   .inflate(R.layout.custom_tab, null);

           binding.tabLayout.getTabAt(i).setCustomView(tv);
       }
    }


    class viewPagerAdapter extends FragmentStateAdapter {
        private  final  List<Fragment> fragmentList=new ArrayList<>();
        private  final  List<String> fragmentTitleList=new ArrayList<>();


        public viewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        public void addFragement(Fragment fragment,String title){
            fragmentList.add(fragment);
            fragmentTitleList.add(title);

        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentList.size();
        }
    }



}
