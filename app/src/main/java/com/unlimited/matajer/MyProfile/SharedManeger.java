package com.unlimited.matajer.MyProfile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.unlimited.matajer.Auth.login.LoginActivity;

import java.util.HashMap;

public class SharedManeger {
    private SharedPreferences mSharedPreferences;
private SharedPreferences.Editor meditor;
private Context mContext;
private int PRIVET_MODE=0;
private Intent mIntent;
private static final String PRE_NAME="my_app";
    public static final String IS_LOGIN="is_login";
    public static final String KEY_ID="key_id";
    public static final String KEY_NAME="key_name";
    public static final String KEY_FIRIST_NAME="firistname";
    public static final String KEY_LAST_NAME="lastname";
    public static final String KEY_EMAIL="email";
    public static final String KEY_PHONE="key_phone";
    public static final String KEY_IMAGE="key+image";
    public static final String KEY_PATH_IMAGE="key_path_image";

    public SharedManeger(Context mContext) {
        this.mContext = mContext;
    mSharedPreferences=mContext.getSharedPreferences(PRE_NAME,PRIVET_MODE);
    meditor=mSharedPreferences.edit();
    }
    public void creatloginsession (Boolean status,String id,String name ,String firistname,String lastname,String phone,String image,String email){
        meditor.putBoolean(IS_LOGIN,status);
        meditor.putString(KEY_ID,id);
        meditor.putString(KEY_NAME,name);
        meditor.putString(KEY_FIRIST_NAME,firistname);
        meditor.putString(KEY_LAST_NAME,lastname);
        meditor.putString(KEY_PHONE,phone);
        meditor.putString(KEY_IMAGE,image);
        meditor.putString(KEY_EMAIL,email);
        meditor.commit();
    }
    public HashMap<String,String> getuserdatailes (){
        HashMap<String ,String > user =new HashMap<String ,String >();
        user.put(KEY_ID,mSharedPreferences.getString(KEY_ID,null));
        user.put(KEY_NAME,mSharedPreferences.getString(KEY_NAME,null));
        user.put(KEY_FIRIST_NAME,mSharedPreferences.getString(KEY_FIRIST_NAME,null));
        user.put(KEY_LAST_NAME,mSharedPreferences.getString(KEY_LAST_NAME,null));
        user.put(KEY_PHONE,mSharedPreferences.getString(KEY_PHONE,null));
        user.put(KEY_IMAGE,mSharedPreferences.getString(KEY_IMAGE,null));
        user.put(KEY_PATH_IMAGE,mSharedPreferences.getString(KEY_PATH_IMAGE,null));
        user.put(KEY_EMAIL,mSharedPreferences.getString(KEY_EMAIL,null));
        return user;
    }

public void logoutuser (){
        meditor.clear();
        meditor.commit();
        mIntent=new Intent(mContext, LoginActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(mIntent);
}
public Boolean islogin(){
        return mSharedPreferences.getBoolean(IS_LOGIN,false);
}
}
