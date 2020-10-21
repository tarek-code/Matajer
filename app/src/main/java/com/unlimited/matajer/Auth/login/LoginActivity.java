package com.unlimited.matajer.Auth.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.unlimited.matajer.common.Moudules.Authresponse.Authresponse;
import com.unlimited.matajer.Auth.signup.SignUpActivity;
import com.unlimited.matajer.Home.HomeActivity;
import com.unlimited.matajer.MyProfile.SharedManeger;
import com.unlimited.matajer.R;
import com.unlimited.matajer.common.APIService;
import com.unlimited.matajer.common.WebServiceClint;
import com.unlimited.matajer.forgetpassword.ForgetPasswordActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
Button mButtonSignup,mButtonLogin;
SharedManeger sharedManeger;
EditText lEditTextEmail,lEditTextPassword;
TextView mTextViewForget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inititatviews();
sharedManeger=new SharedManeger(LoginActivity.this);
        mButtonSignup.setOnClickListener(this);
mButtonLogin.setOnClickListener(this);
mTextViewForget.setOnClickListener(this);
    }

    private void inititatviews() {
        mTextViewForget=findViewById(R.id.forgetlogin);
        mButtonSignup=findViewById(R.id.signUpLogin);
        lEditTextEmail=findViewById(R.id.emaillogin);
        lEditTextPassword=findViewById(R.id.passwordlogin);
        mButtonLogin=findViewById(R.id.loginLogin);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signUpLogin:
                Intent intent=new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.loginLogin:
                if (validateData()){
                    callsignapi();

                }
                break;
            case R.id.forgetlogin:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;
        }
    }

    boolean validateData(){
        if (!Patterns.EMAIL_ADDRESS.matcher(lEditTextEmail.getText().toString().trim()).matches()){
            lEditTextEmail.setError("Invalid Email");
            lEditTextEmail.setFocusable(true);
            return false;
        }
        else if (lEditTextPassword.getText().toString().trim().length()<6){
            lEditTextPassword.setError("Password at least 6 charactarists");
            lEditTextPassword.setFocusable(true);
            return false;
        }
        else {
            return true;
        }
    }
    private void callsignapi() {
        APIService apiService= WebServiceClint.getRetrofit().create(APIService.class);
        Call<Authresponse> call=apiService.login(lEditTextEmail.getText().toString(),
                lEditTextPassword.getText().toString()
              );
        call.enqueue(new Callback<Authresponse>() {
            @Override
            public void onResponse(Call<Authresponse> call, Response<Authresponse> response) {


                    Toast.makeText(LoginActivity.this, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    sharedManeger.creatloginsession(response.body().getStatus(),response.body().getResult().get(0).getId()
                            ,response.body().getResult().get(0).getFullName(),response.body().getResult().get(0).getFirstName(),
                            response.body().getResult().get(0).getLastName(),response.body().getResult().get(0).getPhone()
                            ,response.body().getResult().get(0).getImage(),response.body().getResult().get(0).getEmail());
                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));



            }

            @Override
            public void onFailure(Call<Authresponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}