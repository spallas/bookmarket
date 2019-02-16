package com.gnufsociety.bookmarket;

import android.content.Intent;
import  android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.gnufsociety.bookmarket.api.Api;
import com.gnufsociety.bookmarket.api.BookmarketEndpoints;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pixelcan.inkpageindicator.InkPageIndicator;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends FragmentActivity
        implements HomeFragment.OnFragmentInteractionListener,
                   ChatFragment.OnFragmentInteractionListener
{

    private static final int RC_SIGN_IN = 123;

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    InkPageIndicator inkPageIndicator;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private MyPagerAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            createSignInIntent();
        }
        else {
            initializeUI();
        }

    }


    private void initializeUI(){

        progressBar.setVisibility(View.GONE);
        myadapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myadapter);

        // Start with home screen
        viewPager.setCurrentItem(1);
        inkPageIndicator.setViewPager(viewPager);

        setupFCM(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }


    public void createSignInIntent() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ic_launcher_foreground)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressBar.setVisibility(View.VISIBLE);

        if (requestCode == RC_SIGN_IN){
            if (resultCode == RESULT_OK){
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                setupFCM(user.getUid());
                BookmarketEndpoints apiEndpoint = Api.getInstance().getApiEndpoint();
                apiEndpoint.existUser().enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200)
                            initializeUI();
                        else if (response.code() == 401)
                            startActivity(new Intent(progressBar.getContext(), CompleteActivity.class));
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("FireBaseLogin",t.getMessage());
                    }
                });
            }






            // TODO check if the user exists on rails backend, if yes jump to home activity, otherwise remain on complete profile

        } else if (resultCode == RESULT_CANCELED) {
            createSignInIntent();
        } else {
            // HANDLE ERRORS HERE???? che ce frega a noi!
        }

    }


    private void setupFCM(String uid){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {

                        String token = task.getResult().getToken();

                        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("fcm_token").setValue(token);


                        Log.d("TAGG", "token fcm: "+token);
                    }
                });
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
                case 0:
                    return ProfileFragment.newInstance();
                case 1:
                    return HomeFragment.newInstance();
                case 2:
                    return ChatFragment.newInstance();
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

    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        Toast.makeText(HomeActivity.this, " ON SEARCH requested! ", Toast.LENGTH_SHORT).show();
        //startSearch(null, false, appData, false);
        return true;
    }


}
