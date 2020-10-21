package com.unlimited.matajer.Home.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unlimited.matajer.common.Adapters.HomeAdapter;
import com.unlimited.matajer.common.Moudules.CategoriesRespons.CategoriesRespons;
import com.unlimited.matajer.common.Moudules.CategoriesRespons.responce;
import com.unlimited.matajer.R;
import com.unlimited.matajer.common.APIService;
import com.unlimited.matajer.common.WebServiceClint;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PetsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PetsFragment extends Fragment implements View.OnClickListener {
    String loading="dfc";
ProgressDialog progressDialog;
    RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    List<responce> clist;
    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PetsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PetsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PetsFragment newInstance(String param1, String param2) {
        PetsFragment fragment = new PetsFragment();
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
        view= inflater.inflate(R.layout.fragment_pets, container, false);

        initiatsviews();
    return  view;
    }

    private void initiatsviews() {
        progressDialog=new ProgressDialog(getActivity());
        loading=getResources().getString(R.string.Loading___);
        progressDialog.setMessage(loading);
        progressDialog.show();
        recyclerView=view.findViewById(R.id.recycle);
        clist=new ArrayList<>();
        homeAdapter=new HomeAdapter(getActivity(),clist);
        recyclerView.setAdapter(homeAdapter);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        petsstore();
    }

    private void petsstore() {

        APIService apiService= WebServiceClint.getRetrofit().create(APIService.class);
        Call<CategoriesRespons> call=apiService.getCatrgotis("en");
        call.enqueue(new Callback<CategoriesRespons>() {
            @Override
            public void onResponse(Call<CategoriesRespons> call, Response<CategoriesRespons> response) {
                progressDialog.dismiss();
                clist=response.body().getResult();
                homeAdapter=new HomeAdapter(getActivity(),clist);
                recyclerView.setAdapter(homeAdapter);
            }

            @Override
            public void onFailure(Call<CategoriesRespons> call, Throwable t) {
progressDialog.dismiss();
            }

    });
    }

    @Override
    public void onClick(View v) {

    }
}