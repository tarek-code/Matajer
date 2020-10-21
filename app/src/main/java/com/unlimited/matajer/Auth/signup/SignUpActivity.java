package com.unlimited.matajer.Auth.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unlimited.matajer.common.Moudules.Authresponse.Authresponse;
import com.unlimited.matajer.Auth.login.LoginActivity;
import com.unlimited.matajer.Home.HomeActivity;
import com.unlimited.matajer.MyProfile.SharedManeger;
import com.unlimited.matajer.R;
import com.unlimited.matajer.common.APIService;
import com.unlimited.matajer.common.WebServiceClint;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    SharedManeger sharedManeger;
Button mButtonLogin,mButtonSignup;
EditText mEditTextFiristName,mEditTextLastName,mEditTextEmail,mEditTextPassword,mEditTextPhone;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        sharedManeger=new SharedManeger(SignUpActivity.this);
    initites();
    
}

    private void initites() {
    mButtonLogin=findViewById(R.id.loginsignup);
    mButtonLogin.setOnClickListener(this);
    mButtonSignup=findViewById(R.id.signupsignup);
    mButtonSignup.setOnClickListener(this);
    mEditTextFiristName=findViewById(R.id.firistnameSignup);
    mEditTextLastName=findViewById(R.id.lastnameSignup);
    mEditTextEmail=findViewById(R.id.emailsignupasdasd);
    mEditTextPhone=findViewById(R.id.phone_sign_Updsfsdf);
    mEditTextPassword=findViewById(R.id.passwordsignup);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginsignup:
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                break;
            case R.id.signupsignup:
                if (validateData()){
                    callsignapi();
                }
                break;
        }
    }

    private void callsignapi() {
        APIService apiService= WebServiceClint.getRetrofit().create(APIService.class);
        Call<Authresponse> call=apiService.register(mEditTextFiristName.getText().toString().trim(),
                                                    mEditTextLastName.getText().toString().trim(),
                                                    mEditTextPhone.getText().toString().trim(),
                                                    mEditTextEmail.getText().toString().trim(),
                                                    mEditTextPassword.getText().toString().trim());
call.enqueue(new Callback<Authresponse>() {
    @Override
    public void onResponse(Call<Authresponse> call, Response<Authresponse> response) {

            Toast.makeText(SignUpActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();

            sharedManeger.creatloginsession(response.body().getStatus(),response.body().getResult().get(0).getId()
                    ,response.body().getResult().get(0).getFullName(),response.body().getResult().get(0).getFirstName(),
                    response.body().getResult().get(0).getLastName(),response.body().getResult().get(0).getPhone()
                    ,response.body().getResult().get(0).getImage(),response.body().getResult().get(0).getEmail());
            startActivity(new Intent(SignUpActivity.this,HomeActivity.class));
            SignUpActivity.this.finish();


    }

    @Override
    public void onFailure(Call<Authresponse> call, Throwable t) {
        Log.e("failer",t.getMessage()+"");
        Toast.makeText(SignUpActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
    }
});
}

    boolean validateData(){
    if (mEditTextFiristName.getText().toString().trim().isEmpty()){
        mEditTextFiristName.setError("Wright your name");
        mEditTextFiristName.setFocusable(true);
        return false;
    }
      else   if (mEditTextLastName.getText().toString().trim().isEmpty()){
            mEditTextLastName.setError("Wright your name");
            mEditTextLastName.setFocusable(true);
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(mEditTextEmail.getText().toString().trim()).matches()){
            mEditTextEmail.setError("Invalid Email");
            mEditTextEmail.setFocusable(true);
            return false;
        }
    else   if (mEditTextPhone.getText().toString().trim().isEmpty()){
        mEditTextPhone.setError("Wright your phone");
        mEditTextPhone.setFocusable(true);
        return false;
    }
        else if (mEditTextPassword.getText().toString().trim().length()<6){
            mEditTextPassword.setError("Password at least 6 charactarists");
            mEditTextPassword.setFocusable(true);
            return false;
        }
        else {
            return true;
        }
    }
}