package com.unlimited.matajer.Trying;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.unlimited.matajer.MyProfile.SharedManeger;
import com.unlimited.matajer.R;
import com.unlimited.matajer.common.APIService;
import com.unlimited.matajer.common.Adapters.DetailesApdapter;
import com.unlimited.matajer.common.Moudules.itemDetails.ItemDetails;
import com.unlimited.matajer.common.Moudules.itemDetails.ResultResponse;
import com.unlimited.matajer.common.WebServiceClint;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailesActivity extends AppCompatActivity {
    SharedManeger sharedManeger;
    ProgressDialog progressDialog;
RecyclerView mRecyclerView;
DetailesApdapter detailesApdapter;
List<ResultResponse> clist;
TextView mTextViewnamechanger,mTextViewpricecganger;
ImageView mImageViewback;
Intent intent1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailes);
        sharedManeger=new SharedManeger(DetailesActivity.this);
        progressDialog=new ProgressDialog(DetailesActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        intent1=getIntent();
        final Intent intent=getIntent();
        mTextViewnamechanger=findViewById(R.id.namedetailes);
        mTextViewpricecganger=findViewById(R.id.pricedetalies);
        mTextViewnamechanger.setText(intent.getStringExtra("name"));
        mTextViewpricecganger.setText(intent.getStringExtra("price"));
        mImageViewback=findViewById(R.id.backdetailes);
        mImageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(DetailesActivity.this,RecActivity.class);
                startActivity(intent1);
            }
        });
mRecyclerView=findViewById(R.id.recycle3);
clist=new ArrayList<>();
detailesApdapter=new DetailesApdapter(clist,DetailesActivity.this);
mRecyclerView.setAdapter(detailesApdapter);
RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(DetailesActivity.this,RecyclerView.HORIZONTAL,false);
mRecyclerView.setLayoutManager(layoutManager);
calitemdetails();
    }

    private void calitemdetails() {
        APIService apiService= WebServiceClint.getRetrofit().create(APIService.class);
        Call<ItemDetails> call=apiService.getitemdetails(intent1.getStringExtra("id"),sharedManeger.getuserdatailes().get(SharedManeger.KEY_ID));
        call.enqueue(new Callback<ItemDetails>() {
            @Override
            public void onResponse(Call<ItemDetails> call, Response<ItemDetails> response) {
                clist=response.body().getResult();
                detailesApdapter=new DetailesApdapter(clist,DetailesActivity.this);
                mRecyclerView.setAdapter(detailesApdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ItemDetails> call, Throwable t) {
progressDialog.dismiss();
            }
        });
    }


}