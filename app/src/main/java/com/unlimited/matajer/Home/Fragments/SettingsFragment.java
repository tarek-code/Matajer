package com.unlimited.matajer.Home.Fragments;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unlimited.matajer.MyProfile.ProfileActivity;
import com.unlimited.matajer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {
    View view;
    CardView mCardView1,mCardView2,mCardView3,mCardView4,mCardView5,mCardView6;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_settings, container, false);
        mCardView1=view.findViewById(R.id.card1);
        mCardView2=view.findViewById(R.id.card2);
        mCardView3=view.findViewById(R.id.card3);
        mCardView4=view.findViewById(R.id.card4);
        mCardView5=view.findViewById(R.id.card5);
        mCardView6=view.findViewById(R.id.card6);
        mCardView1.setOnClickListener(this);
        mCardView2.setOnClickListener(this);
        mCardView3.setOnClickListener(this);
        mCardView4.setOnClickListener(this);
        mCardView5.setOnClickListener(this);
        mCardView6.setOnClickListener(this);
    return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.card1:
                startActivity(new Intent(getActivity(), ProfileActivity.class));
                break;
            case R.id.card2:
                LanguchFragment languchFragment=new LanguchFragment();
                languchFragment.show(getActivity().getSupportFragmentManager(),"");
                break;
            case R.id.card3:
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.changer,new ContactUsFragment()).commit();
                break;
            case R.id.card4:
                ApplicationInfo api=getActivity().getApplicationContext().getApplicationInfo();
                String apkPath=api.sourceDir;
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareBody="Your Body Here";
                String shareSub="Your Subject Here";
                intent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                intent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(intent,"Share APP"));
                break;
            case R.id.card5:
                RateAppFragment rateAppFragment=new RateAppFragment();
                rateAppFragment.show(getActivity().getSupportFragmentManager(),"");
                break;
            case R.id.card6:
                LogoutFragment logoutFragment=new LogoutFragment();
                logoutFragment.show(getActivity().getSupportFragmentManager(),"");
                break;
        }
    }
}