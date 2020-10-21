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

import com.unlimited.matajer.common.Adapters.RecAdapter;
import com.unlimited.matajer.common.Moudules.items.Items;
import com.unlimited.matajer.common.Moudules.items.iremesresult;
import com.unlimited.matajer.Home.HomeActivity;
import com.unlimited.matajer.MyProfile.SharedManeger;
import com.unlimited.matajer.R;
import com.unlimited.matajer.common.APIService;
import com.unlimited.matajer.common.WebServiceClint;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecActivity extends AppCompatActivity {
RecyclerView recyclerView;
RecAdapter recAdapter;
List<iremesresult> clist;
ImageView mImageViewBack;
TextView mTextViewchanger;
    Intent intent;
    SharedManeger sharedManeger;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);
        sharedManeger=new SharedManeger(RecActivity.this);
        progressDialog=new ProgressDialog(RecActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
         intent=getIntent();
        mTextViewchanger=findViewById(R.id.home);
        mTextViewchanger.setText(intent.getStringExtra("name"));
        recyclerView=findViewById(R.id.recyclerec);
        mImageViewBack=findViewById(R.id.bavktolastactivity);
        clist=new ArrayList<>();
        recAdapter=new RecAdapter(RecActivity.this,clist);
        recyclerView.setAdapter(recAdapter);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(RecActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
mImageViewBack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(RecActivity.this, HomeActivity.class);
        startActivity(intent);
    }
});
callitemes();
    }

    private void callitemes() {
        APIService apiService= WebServiceClint.getRetrofit().create(APIService.class);
        Call<Items> call=apiService.getitemes("en",sharedManeger.getuserdatailes().get(SharedManeger.KEY_ID));
        call.enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                clist=response.body().getResult();
                recAdapter=new RecAdapter(RecActivity.this,clist);
                recyclerView.setAdapter(recAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {
progressDialog.dismiss();
            }
        });
    }

}