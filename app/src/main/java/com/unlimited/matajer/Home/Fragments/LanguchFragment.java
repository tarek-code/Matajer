package com.unlimited.matajer.Home.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.VolumeShaper;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.unlimited.matajer.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LanguchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LanguchFragment extends AppCompatDialogFragment  {

    RadioButton mRadioButtonEnglish,mRadioButtonArabic;
    View view;
    TextView mTextViewMainTV,mTextViewArabic,mTextViewEnglish;
    Button mButtonSave;
    ImageView mImageViewArabic,mImageViewEnglish;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LanguchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LanguchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LanguchFragment newInstance(String param1, String param2) {
        LanguchFragment fragment = new LanguchFragment();
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
        view= inflater.inflate(R.layout.fragment_languch, container, false);

        mRadioButtonEnglish=view.findViewById(R.id.english);
        mRadioButtonArabic=view.findViewById(R.id.arabic);

        mRadioButtonEnglish.setTextColor(Color.GRAY);

mRadioButtonArabic.setTextColor(Color.GRAY);

        mButtonSave=view.findViewById(R.id.savefragment);

mRadioButtonEnglish.setChecked(false);
mRadioButtonArabic.setChecked(false);



        mRadioButtonArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "ar", Toast.LENGTH_SHORT).show();
mRadioButtonArabic.setChecked(true);
mRadioButtonEnglish.setChecked(false);

                mRadioButtonEnglish.setTextColor(Color.GRAY);

                mRadioButtonArabic.setTextColor(Color.CYAN);
            }
        });
        mRadioButtonEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "en", Toast.LENGTH_SHORT).show();
                mRadioButtonEnglish.setChecked(true);
                mRadioButtonArabic.setChecked(false);
                mRadioButtonEnglish.setTextColor(Color.CYAN);

                mRadioButtonArabic.setTextColor(Color.GRAY);


            }
        });
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
if(mRadioButtonEnglish.isChecked()){
    setLocal("en");
    getActivity().recreate();
    dismiss();
}
else {
    setLocal("ar");
    getActivity().recreate();
    dismiss();
}
            }
        });
        loadlocal();

    return  view;
    }

    private void setLocal(String lang) {
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration cofig=new Configuration();
        cofig.locale=locale;
        getActivity().getBaseContext().getResources().updateConfiguration(cofig,getActivity().getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor=getActivity().getSharedPreferences("langsettings", Context.MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }
    public void loadlocal(){
        SharedPreferences pref=getActivity().getSharedPreferences("langsettings", Activity.MODE_PRIVATE);
        String language=pref.getString("My_Lang","");
        setLocal(language);
    }
}