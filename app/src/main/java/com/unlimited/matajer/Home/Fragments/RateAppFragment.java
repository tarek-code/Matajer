package com.unlimited.matajer.Home.Fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.unlimited.matajer.MyProfile.RatManger;
import com.unlimited.matajer.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RateAppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RateAppFragment extends AppCompatDialogFragment {
    RatManger ratManger;
    RatingBar ratingBar;
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RateAppFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RateAppFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RateAppFragment newInstance(String param1, String param2) {
        RateAppFragment fragment = new RateAppFragment();
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
        view= inflater.inflate(R.layout.fragment_rate_app, container, false);

        ratManger=new RatManger(getActivity());



ratingBar=view.findViewById(R.id.rateBare);
ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        int ratingb=(int) rating;
String message=null;
switch (ratingb){
    case 1:
        message="Sorry to here that";
        break;
    case 2:
        message="You Always accept suggestions";
        break;
    case 3:
        message="Good enough";
        break;
    case 4:
        message="Grate thank you";
        break;
    case 5:
        message="Awesome! Yor are the best";
        break;
}
ratManger.creatloginsession(String.valueOf(ratingb));
        Toast.makeText(getActivity(), ""+message, Toast.LENGTH_SHORT).show();
    }
});
        ratingBar.setRating(Integer.parseInt(ratManger.getuserdatailes().get(RatManger.RATING)));
    return view;
    }
}