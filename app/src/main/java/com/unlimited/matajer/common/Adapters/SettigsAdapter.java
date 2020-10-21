package com.unlimited.matajer.common.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.unlimited.matajer.Home.Fragments.ContactUsFragment;
import com.unlimited.matajer.Home.Fragments.LanguchFragment;
import com.unlimited.matajer.Home.Fragments.LogoutFragment;
import com.unlimited.matajer.Home.Fragments.RateAppFragment;
import com.unlimited.matajer.MyProfile.ProfileActivity;
import com.unlimited.matajer.R;
import com.unlimited.matajer.common.Moudules.SrttingsModule.SrttingsModule;

import java.util.ArrayList;

public class SettigsAdapter extends RecyclerView.Adapter<SettigsAdapter.SettingsViewHolder> {
ArrayList<SrttingsModule> arrayList;
Context context;
View v;
    public SettigsAdapter(ArrayList<SrttingsModule> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_item,parent,false);
        SettingsViewHolder settingsViewHolder=new SettingsViewHolder(view);
        return settingsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, final int position) {
holder.mTextViewSettings.setText(arrayList.get(position).getSettingsname());
holder.photoSettings.setImageResource(arrayList.get(position).getPhotoSettings());
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if (position==0){
           context.startActivity(new Intent(context, ProfileActivity.class));
        }
        else if (position==1){
            AppCompatActivity activity=(AppCompatActivity)v.getContext();
            LanguchFragment languchFragment=new LanguchFragment();
            languchFragment.show(activity.getSupportFragmentManager(),"");
        }
        else if (position==5){
            AppCompatActivity activity=(AppCompatActivity)v.getContext();
            LogoutFragment logoutFragment=new LogoutFragment();
            logoutFragment.show(activity.getSupportFragmentManager(),"");
        }
        else if (position==2){
            AppCompatActivity activity=(AppCompatActivity)v.getContext();
            FragmentManager fragmentManager=activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.changer,new ContactUsFragment()).commit();
        }
        else if (position==3){
            ApplicationInfo api=context.getApplicationContext().getApplicationInfo();
            String apkPath=api.sourceDir;
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String shareBody="Your Body Here";
            String shareSub="Your Subject Here";
            intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            intent.putExtra(Intent.EXTRA_TEXT,shareBody);
            context.startActivity(Intent.createChooser(intent,"Share APP"));
        }
        else if (position==4){
            AppCompatActivity activity=(AppCompatActivity)v.getContext();
            RateAppFragment rateAppFragment=new RateAppFragment();
            rateAppFragment.show(activity.getSupportFragmentManager(),"");
        }

        }
});
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class SettingsViewHolder extends RecyclerView.ViewHolder {
         ImageView photoSettings;
         TextView mTextViewSettings;
        public SettingsViewHolder(@NonNull View itemView) {
            super(itemView);
            photoSettings=itemView.findViewById(R.id.photoSettings);
            mTextViewSettings=itemView.findViewById(R.id.titleSettings);
        }
    }
}
