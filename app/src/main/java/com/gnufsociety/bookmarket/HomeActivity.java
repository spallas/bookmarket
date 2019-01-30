package com.gnufsociety.bookmarket;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import  android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.gnufsociety.bookmarket.HomeFragment.OnFragmentInteractionListener;
import com.gnufsociety.bookmarket.api.Api;
import com.gnufsociety.bookmarket.api.BookmarketEndpoints;
import com.gnufsociety.bookmarket.models.BMUser;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        BookmarketEndpoints apiEndpoint = Api.getInstance().getApiEndpoint();

        Call<List<BMUser>> call = apiEndpoint.allUsers(/*give user id to api path*/ );

/*        call.enqueue(new Callback<List<BMUser>>() {
            @Override
            public void onResponse(Call<List<BMUser>> call, Response<List<BMUser>> response) {
                Toast.makeText(HomeActivity.this,
                        response.body().get(this_user_id).user_id,
                        Toast.LENGTH_SHORT);

            }

            @Override
            public void onFailure(Call<List<BMUser>> call, Throwable t) {
                Toast.makeText(HomeActivity.this,
                        "Something went wrong...Error message: " + t.getMessage(),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });*/
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
                    return HomeFragment.newInstance("Home", "Page # 2");
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
