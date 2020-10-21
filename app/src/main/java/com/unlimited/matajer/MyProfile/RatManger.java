package com.unlimited.matajer.MyProfile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class RatManger {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor meditor;
    private Context mContext;
    private int PRIVET_MODE=0;
    private Intent mIntent;
    private static final String PRE_NAME="my_rate";
    public static final String RATING="rating";

    public RatManger(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences=mContext.getSharedPreferences(PRE_NAME,PRIVET_MODE);
        meditor=mSharedPreferences.edit();
    }

    public void creatloginsession (String rate){
        meditor.putString(RATING,rate);
        meditor.commit();
    }
    public HashMap<String,String> getuserdatailes (){
        HashMap<String ,String > rate_user =new HashMap<String ,String >();
        rate_user.put(RATING,mSharedPreferences.getString(RATING,"0"));
        return rate_user;
        }
}
