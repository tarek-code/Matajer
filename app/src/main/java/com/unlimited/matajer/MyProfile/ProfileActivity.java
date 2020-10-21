package com.unlimited.matajer.MyProfile;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.unlimited.matajer.Home.HomeActivity;
import com.unlimited.matajer.R;
import com.unlimited.matajer.common.APIService;
import com.unlimited.matajer.common.Moudules.EditProfile.EditProfile;
import com.unlimited.matajer.common.Moudules.UploadImage.UploadImage;
import com.unlimited.matajer.common.WebServiceClint;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    Bitmap thumbnail;
File file;
    private static final int GALIRY =1;
    private static final int CAMERA=2;
     SharedPreferences preferences;
     SharedPreferences.Editor editor;
    Bitmap bitmap;
    TextView mTextViewuploadphoto;
    SharedManeger sharedManeger;
ImageView mImageViewBack;
    CircularImageView mImageViewprofileuser;
EditText mEditTextfirstname,mEditTextlastename,mEditTextemile,mEditTextphonr;
Button mButtonsave,mButtonSaveImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        inittatesviews();
    }

    private void inittatesviews() {
        sharedManeger=new SharedManeger(ProfileActivity.this);
        mTextViewuploadphoto=findViewById(R.id.uploadprofile);
        mImageViewprofileuser=findViewById(R.id.userimage);
        mEditTextfirstname=findViewById(R.id.firistname);
        mEditTextlastename=findViewById(R.id.lastname);
        mEditTextemile=findViewById(R.id.emailsignup);
        mEditTextphonr=findViewById(R.id.phone_sign_Up);
        mButtonsave=findViewById(R.id.loginsignup);
        mButtonSaveImage=findViewById(R.id.saveimage);
        mImageViewBack=findViewById(R.id.bavktolastactivity);

        // get photo //
        preferences = getSharedPreferences("myprefs",MODE_PRIVATE);
        editor = preferences.edit();
        editor.commit();
        preferences = getSharedPreferences("myprefs",MODE_PRIVATE);
        // If value for key not exist then return second param value - In this case "..."
        String img_str=preferences.getString("userphoto", "");
        if (!img_str.equals("")){
            //decode string to image
            String base=img_str;
            byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
            mImageViewprofileuser.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );
            mImageViewprofileuser.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );
        }
        else {
            Glide.with(this).load(sharedManeger.getuserdatailes().get(SharedManeger.KEY_IMAGE)).into(mImageViewprofileuser);
        }

        mEditTextfirstname.setHint(sharedManeger.getuserdatailes().get(SharedManeger.KEY_FIRIST_NAME));
        mEditTextlastename.setHint(sharedManeger.getuserdatailes().get(SharedManeger.KEY_LAST_NAME));

        mEditTextemile.setHint(sharedManeger.getuserdatailes().get(SharedManeger.KEY_EMAIL));
        mEditTextphonr.setHint(sharedManeger.getuserdatailes().get(SharedManeger.KEY_PHONE));
mImageViewBack.setOnClickListener(this);
        mButtonsave.setOnClickListener(this);
        mButtonSaveImage.setOnClickListener(this);
        mTextViewuploadphoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
switch (v.getId()){
    case R.id.saveimage:
        //code image to string
        mImageViewprofileuser.buildDrawingCache();
        bitmap = mImageViewprofileuser.getDrawingCache();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image=stream.toByteArray();
        //System.out.println("byte array:"+image);
        //final String img_str = "data:image/png;base64,"+ Base64.encodeToString(image, 0);
        //System.out.println("string:"+img_str);
        String  img_str = Base64.encodeToString(image, 0);
        //decode string to image
        String base=img_str;
        byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
        mImageViewprofileuser.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes,0, imageAsBytes.length)
        );
        //save in sharedpreferences
        SharedPreferences preferences = getSharedPreferences("myprefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userphoto",img_str);
        editor.commit();

       saveimage();
        break;
    case R.id.uploadprofile:
        showselectiondialog();
        break;
    case R.id.loginsignup:
        calgetprofile();
        break;
    case R.id.bavktolastactivity:
        startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
        break;
}
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
  private void saveimage() {
      Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
MultipartBody.Part requestImage=null;

String path=mImageViewprofileuser.toString();
if (file==null){
file=new File(getRealPathFromURI(tempUri));
}
RequestBody requestFile=RequestBody.create(MediaType.parse("multipart/form-data"),file);
requestImage=MultipartBody.Part.createFormData("image",file.getName(),requestFile);

        APIService apiService= WebServiceClint.getRetrofit().create(APIService.class);
        Call<UploadImage> call=apiService.getuploadimage(sharedManeger.getuserdatailes().get(SharedManeger.KEY_ID
                ), requestImage) ;
        call.enqueue(new Callback<UploadImage>() {
            @Override
            public void onResponse(Call<UploadImage> call, Response<UploadImage> response) {
                Toast.makeText(ProfileActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                Log.e("sucsess",""+response.body().getMsg());
            }

            @Override
            public void onFailure(Call<UploadImage> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("failed",""+t.getMessage());
            }
        });
    }

    private void calgetprofile() {
        APIService apiService= WebServiceClint.getRetrofit().create(APIService.class);
        Call<EditProfile> call=apiService.getProfile(sharedManeger.getuserdatailes().get(SharedManeger.KEY_ID),mEditTextfirstname.getText().toString(),
                mEditTextlastename.getText().toString(),mEditTextphonr.getText().toString(),mEditTextemile.getText().toString());
        call.enqueue(new Callback<EditProfile>() {
            @Override
            public void onResponse(Call<EditProfile> call, Response<EditProfile> response) {
                Toast.makeText(ProfileActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                sharedManeger.creatloginsession(response.body().getStatus(),response.body().getResult().get(0).getId()
                        ,response.body().getResult().get(0).getFullName(),response.body().getResult().get(0).getFirstName(),
                        response.body().getResult().get(0).getLastName(),response.body().getResult().get(0).getPhone()
                        ,response.body().getResult().get(0).getImage(),response.body().getResult().get(0).getEmail());
            }

            @Override
            public void onFailure(Call<EditProfile> call, Throwable t) {

            }
        });
    }

    // to get photo from phone //

    private void showselectiondialog(){
        AlertDialog.Builder selectiondialog=new AlertDialog.Builder(this);
        selectiondialog.setTitle("Selection Action");
        String[] selctiondialogitimes={
                "select photo from galiry","capture photo from camera"
        };

        selectiondialog.setItems(selctiondialogitimes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        chosephotofromgaliry();
                        break;
                    case 1:
                        chosephotofromcanera();
                        break;
                }
            }
        });
        selectiondialog.show();
    }

    @AfterPermissionGranted(101)
    public void chosephotofromgaliry(){
        String[] galiry=new String[0];
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN){
            galiry=new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }
        if (EasyPermissions.hasPermissions(this,galiry)){
            Intent galiryintent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galiryintent,GALIRY);
        }
        else {
            EasyPermissions.requestPermissions(this,"Acsess for storage",101,galiry);
        }
    }

    @AfterPermissionGranted(123)
    private void chosephotofromcanera(){
        String[] photo=new String[1];
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
            photo=new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE};
        }
        if (EasyPermissions.hasPermissions(this,photo)){
            Intent photointent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(photointent,CAMERA);
        }
        else {
            EasyPermissions.requestPermissions(this,"Acsess for storage ",123,photo);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode== RESULT_CANCELED){
            return;
        }
        if (requestCode==GALIRY&&resultCode==RESULT_OK){
            if (data != null){

                Uri  contentUri=data.getData();
                try {
                    thumbnail=MediaStore.Images.Media.getBitmap(this.getContentResolver(),contentUri);
                    mImageViewprofileuser.setImageBitmap(thumbnail);
                }catch (IOException e){
                    e.printStackTrace();
                }

            }

        }
        if (requestCode== CAMERA&&resultCode==RESULT_OK){
             thumbnail=(Bitmap)data.getExtras().get("data");
            mImageViewprofileuser.setImageBitmap(thumbnail);



        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
        if (grantResults.length>0){
            if (grantResults.toString().equals(GALIRY)){
                Intent galiryintent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galiryintent,GALIRY);
            }
            else if (grantResults.toString().equals(CAMERA)){
                Intent photointent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photointent,CAMERA);
            }
        }
    }

}