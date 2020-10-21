package com.unlimited.matajer.Home.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unlimited.matajer.MyProfile.SharedManeger;
import com.unlimited.matajer.R;
import com.unlimited.matajer.common.APIService;
import com.unlimited.matajer.common.Adapters.FavAdapter;
import com.unlimited.matajer.common.Moudules.Favorites_by_user.Fav_Result;
import com.unlimited.matajer.common.Moudules.Favorites_by_user.FavoritesByUser;
import com.unlimited.matajer.common.WebServiceClint;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoretFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoretFragment extends Fragment {
    String loading="ds";
    SwipeRefreshLayout swipeRefreshLayout;
    SharedManeger sharedManeger;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    FavAdapter favAdapter;
    List<Fav_Result> clist;
    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoretFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoretFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoretFragment newInstance(String param1, String param2) {
        FavoretFragment fragment = new FavoretFragment();
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
        view= inflater.inflate(R.layout.fragment_favoret, container, false);
        initiatsviews();
        return view;
    }
    private void initiatsviews() {
        swipeRefreshLayout=view.findViewById(R.id.swip);
        sharedManeger=new SharedManeger(getActivity());
        progressDialog=new ProgressDialog(getActivity());
        loading=getResources().getString(R.string.Loading___);
        progressDialog.setMessage(loading);
        progressDialog.show();
        recyclerView=view.findViewById(R.id.recyclerfavoret);
        clist=new ArrayList<>();
        favAdapter=new FavAdapter(getActivity(),clist);
        recyclerView.setAdapter(favAdapter);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        petsstore();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        petsstore();
swipeRefreshLayout.setRefreshing(false);
                    }
                },4000);
            }
        });
    }
    private void petsstore() {

        APIService apiService= WebServiceClint.getRetrofit().create(APIService.class);
        Call<FavoritesByUser> call=apiService.getfave(sharedManeger.getuserdatailes().get(SharedManeger.KEY_ID));
        call.enqueue(new Callback<FavoritesByUser>() {
            @Override
            public void onResponse(Call<FavoritesByUser> call, Response<FavoritesByUser> response) {
                progressDialog.dismiss();
                clist=response.body().getResult();
                favAdapter=new FavAdapter(getActivity(),clist);
                recyclerView.setAdapter(favAdapter);
                Log.e("sucsess",response.body().getMsg());
            }

            @Override
            public void onFailure(Call<FavoritesByUser> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("fail",t.getMessage());
            }

        });
    }
}