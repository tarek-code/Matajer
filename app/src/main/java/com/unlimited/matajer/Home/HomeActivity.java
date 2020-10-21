package com.unlimited.matajer.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.unlimited.matajer.Home.Fragments.AboutUsFragment;
import com.unlimited.matajer.Home.Fragments.FavoretFragment;
import com.unlimited.matajer.Home.Fragments.PetsFragment;
import com.unlimited.matajer.Home.Fragments.SettingsFragment;
import com.unlimited.matajer.MyProfile.SharedManeger;
import com.unlimited.matajer.R;
import com.unlimited.matajer.Trying.RecActivity;
import com.unlimited.matajer.common.APIService;
import com.unlimited.matajer.common.Moudules.Authresponse.Authresponse;
import com.unlimited.matajer.common.Moudules.Authresponse.Result;
import com.unlimited.matajer.common.WebServiceClint;

import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

NavigationView mNavigationView;
    SharedManeger sharedManeger;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    DrawerLayout mdrawer;
    ImageView mOpendrawr;
    CircularImageView mImageViewUserImage;
    TextView mTextViewHome, mTextViewFav, mTextViewHistory, mTextViewSittings, mTextViewAboutUs, mTextViewHomeAsly, mTextViewname;
    String language;
    String fav="dsa";
    String set="scd";
    String about="df";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences pref = getSharedPreferences("langsettings", Activity.MODE_PRIVATE);
        language = pref.getString("My_Lang", "");
        setLocal(language);

        initiatviews();



    }

    private void initiatviews() {
        mNavigationView=findViewById(R.id.nav);
        mImageViewUserImage = findViewById(R.id.pic_person);
        sharedManeger = new SharedManeger(HomeActivity.this);
        mTextViewname = findViewById(R.id.name);
        mTextViewHomeAsly = findViewById(R.id.home);
        mTextViewHome = findViewById(R.id.home2);
        mTextViewFav = findViewById(R.id.fav);
        mTextViewHistory = findViewById(R.id.history);
        mTextViewSittings = findViewById(R.id.sittingshome);
        mTextViewAboutUs = findViewById(R.id.aboutus);
        mdrawer = findViewById(R.id.drawer);
        mOpendrawr = findViewById(R.id.opendrawer);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.changer, new PetsFragment()).commit();
        mTextViewname.setText(sharedManeger.getuserdatailes().get(SharedManeger.KEY_NAME));
// open drawer //
        mOpendrawr.setOnClickListener(this);
        mTextViewHome.setOnClickListener(this);
        mTextViewFav.setOnClickListener(this);
        mTextViewHistory.setOnClickListener(this);
        mTextViewSittings.setOnClickListener(this);
        mTextViewAboutUs.setOnClickListener(this);

      // get user photo from SharedPreferences//
        preferences = getSharedPreferences("myprefs", MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();
        preferences = getSharedPreferences("myprefs", MODE_PRIVATE);
        // If value for key not exist then return second param value - In this case "..."
        String img_str = preferences.getString("userphoto", "");
        if (!img_str.equals("")) {
            //decode string to image
            String base = img_str;
            byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
            mImageViewUserImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            mImageViewUserImage.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));

        }
else {
            Glide.with(this).load(sharedManeger.getuserdatailes().get(SharedManeger.KEY_IMAGE)).into(mImageViewUserImage);
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.opendrawer) {

                mdrawer.openDrawer(Gravity.START);

        } else if (v.getId() == R.id.home2) {
            mdrawer.closeDrawers();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.changer, new PetsFragment()).commit();
        } else if (v.getId() == R.id.fav || v.getId() == R.id.history) {
            fav=getResources().getString(R.string.fav);
            mTextViewHomeAsly.setText(fav);
            mdrawer.closeDrawers();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.changer, new FavoretFragment()).commit();
        } else if (v.getId() == R.id.sittingshome) {
            set=getResources().getString(R.string.sittimgs);
            mTextViewHomeAsly.setText(set);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.changer, new SettingsFragment()).commit();
            mdrawer.closeDrawers();
        } else if (v.getId() == R.id.aboutus) {
            about=getResources().getString(R.string.abbout_us);
            mTextViewHomeAsly.setText(about);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.changer, new AboutUsFragment()).commit();
            mdrawer.closeDrawers();
        }

    }

    private void setLocal(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration cofig = new Configuration();
        cofig.locale = locale;
        getBaseContext().getResources().updateConfiguration(cofig, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("langsettings", Context.MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }
}