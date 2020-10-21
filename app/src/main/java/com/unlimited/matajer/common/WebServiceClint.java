package com.unlimited.matajer.common;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServiceClint {
    private static Retrofit retrofit =null;

    public static Retrofit getRetrofit(){
if (retrofit==null){
    retrofit=new Retrofit.Builder()
            .baseUrl(AppConstans.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

}

        return retrofit;

    }
}
