package com.grameen.bebshanikashapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.grameen.bebshanikashapp.Api.ApiInterface;
import com.grameen.bebshanikashapp.Api.RetrofitClient;
import com.grameen.bebshanikashapp.Model.AsignRole.AsignRole;
import com.grameen.bebshanikashapp.Model.SignUp.SignUp;
import com.grameen.bebshanikashapp.R;

public class RegistrationActivity extends AppCompatActivity {

    private TextView logInLayBtn, phoneNumberTxt;
    private ExtendedFloatingActionButton nextStepBtn, nextStepVerifyBtn;
    private RelativeLayout registrationLay, verifyLayOut;
    private EditText shopName, phoneNumber, password, confirmPassword, verifyCode;
    private ProgressBar progressBar;
    private String resVerifyCode, role = "seller";
    public static final String TAG = "resgister";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        inItView();

        logInLayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(i);
            }
        });


        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpRegistration();
            }
        });

        nextStepVerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                setUpVerification();
            }
        });
    }

    private void setUpVerification() {

        String verifyText = verifyCode.getText().toString().trim();
        if(verifyText.equals(resVerifyCode)){
            progressBar.setVisibility(View.GONE);
            Retrofit retrofit = RetrofitClient.getRetrofitClient();
            ApiInterface api = retrofit.create(ApiInterface.class);

            Call<AsignRole> asignCall = api.postByAsignRole(phoneNumberTxt.getText().toString().trim(), role);
            asignCall.enqueue(new Callback<AsignRole>() {
                @Override
                public void onResponse(Call<AsignRole> call, Response<AsignRole> response) {
                    if(response.code() == 200){
                        AsignRole asignRole = response.body();
                        Toast.makeText(RegistrationActivity.this, asignRole.getMessage(), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(i);
                    }
                }

                @Override
                public void onFailure(Call<AsignRole> call, Throwable t) {

                }
            });
        }else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "ভেরিফিকেশন কোড মিল নাই !", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUpRegistration() {
        String setUpShopName = shopName.getText().toString().trim();
        String setUpPhoneNumber = phoneNumber.getText().toString().trim();
        String setUpPassword = password.getText().toString().trim();
        String setUpConfirmPass = confirmPassword.getText().toString().trim();

        if (setUpShopName.isEmpty()) {
            shopName.setError("দোকানের নাম দিতে হবে");
            shopName.requestFocus();
            return;
        }

        if (setUpPhoneNumber.isEmpty()) {
            phoneNumber.setError("ফোন নম্বর দিতে হবে");
            phoneNumber.requestFocus();
            return;
        }

        if (setUpPassword.isEmpty()) {
            password.setError("পাসওয়ার্ড দিতে হবে");
            password.requestFocus();
            return;
        }

        if (setUpConfirmPass.isEmpty()) {
            confirmPassword.setError("পাসওয়ার্ড কনফার্ম করতে হবে");
            confirmPassword.requestFocus();
            return;
        }

        if(setUpPassword.equals(setUpConfirmPass)){

            callSignUpUser(setUpShopName, setUpPhoneNumber, setUpPassword, setUpConfirmPass);
        }else {
            Toast.makeText(this, "পাসওয়ার্ড মিল হয় নি !", Toast.LENGTH_SHORT).show();
        }

    }

    private void callSignUpUser(String setUpShopName, String setUpPhoneNumber, String setUpPassword, String setUpConfirmPass) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<SignUp> signUpCall = api.postBySignUp(setUpShopName, setUpPhoneNumber, setUpPassword, setUpConfirmPass);

        signUpCall.enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                if(response.code() == 200){
                    SignUp signUpRes = response.body();
                    progressBar.setVisibility(View.GONE);
                    registrationLay.setVisibility(View.GONE);
                    verifyLayOut.setVisibility(View.VISIBLE);
                    resVerifyCode = String.valueOf(signUpRes.getSmsCode());
                    phoneNumberTxt.setText(signUpRes.getPhone());
                    verifyCode.setText(resVerifyCode);

                    Log.d(TAG, "onResponse: "+ response.body());
                }
            }

            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                Toast.makeText(RegistrationActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void inItView() {

        logInLayBtn = findViewById(R.id.logInLayBtn);
        nextStepBtn = findViewById(R.id.nextStepBtn);
        registrationLay = findViewById(R.id.registrationLay);
        verifyLayOut = findViewById(R.id.verifyLayOut);
        nextStepVerifyBtn = findViewById(R.id.nextStepVerifyBtn);
        shopName = findViewById(R.id.shopName);
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        progressBar = findViewById(R.id.progressBar);
        verifyCode = findViewById(R.id.verifyCode);
        phoneNumberTxt = findViewById(R.id.phoneNumberTxt);
    }
}