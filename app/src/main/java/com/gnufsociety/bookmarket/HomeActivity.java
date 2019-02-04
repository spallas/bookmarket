package com.gnufsociety.bookmarket;

import  android.net.Uri;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.gnufsociety.bookmarket.api.Api;
import com.gnufsociety.bookmarket.api.BookmarketEndpoints;
import com.gnufsociety.bookmarket.models.BMUser;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.List;

import retrofit2.Call;

public class HomeActivity extends FragmentActivity
        implements HomeFragment.OnFragmentInteractionListener,
                   ProfileFragment.OnFragmentInteractionListener,
                   ChatFragment.OnFragmentInteractionListener
{

    private ViewPager viewPager;
    private MyPagerAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        myadapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myadapter);

        // Start with home screen
        viewPager.setCurrentItem(1);

        InkPageIndicator inkPageIndicator = (InkPageIndicator) findViewById(R.id.indicator);
        inkPageIndicator.setViewPager(viewPager);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return ProfileFragment.newInstance("Profile", "Page # 1");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return HomeFragment.newInstance();
                case 2: // Fragment # 1 - This will show SecondFragment
                    return ChatFragment.newInstance("Chat", "Page # 3");
                default:
                    return null;
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }

}
