package com.grameen.bebshanikashapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.grameen.bebshanikashapp.Api.ApiInterface;
import com.grameen.bebshanikashapp.Api.RetrofitClient;
import com.grameen.bebshanikashapp.Model.LogIn.LogIn;
import com.grameen.bebshanikashapp.Model.SignUp.SignUp;
import com.grameen.bebshanikashapp.R;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton nextStepBtn;
    private EditText phoneNumber, password;
    private TextView registerLayBtn;
    private ProgressBar progressBar;
    public static final String TAG = "main";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inItView();
        SharedPreferences preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
        String retrievedToken  = preferences.getString("TOKEN",null);

        if(retrievedToken != null){
            Intent i = new Intent(MainActivity.this, FragmentContainerActivity.class);
            startActivity(i);
            finish();
        }
        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpSignIn();
            }
        });

        registerLayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpSignIn() {
        String upEmail = phoneNumber.getText().toString().trim();
        String upPassword = password.getText().toString().trim();

        if (upEmail.isEmpty()) {
            phoneNumber.setError("ফোন নম্বর দিতে হবে");
            phoneNumber.requestFocus();
            return;
        }

        if (upPassword.isEmpty()) {
            password.setError("পাসওয়ার্ড দিতে হবে");
            password.requestFocus();
            return;
        }

        callLoginUser(upEmail, upPassword);
    }

    private void callLoginUser(String upEmail, String upPassword) {

        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitClient.getRetrofitClient();
        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<LogIn> logInCall = api.postByLogIn(upEmail, upPassword);

        logInCall.enqueue(new Callback<LogIn>() {
            @Override
            public void onResponse(Call<LogIn> call, Response<LogIn> response) {
                if(response.code() == 200){

                    LogIn logInInfo = response.body();
                    Log.d(TAG, "onMainRes: "+ response.body());
                    if(logInInfo !=null) {
                        Log.d(TAG, "onStatus: " + logInInfo.getStatus());
                        if(logInInfo.getStatus().equals("1")) {
                            progressBar.setVisibility(View.GONE);
                            finish();
                            Log.d(TAG, "onToken: " + logInInfo.getToken());
                            SharedPreferences preferences = getSharedPreferences("MY_APP", Context.MODE_PRIVATE);
                            preferences.edit().putString("TOKEN", logInInfo.getToken()).apply();
                            Intent intent = new Intent(MainActivity.this, FragmentContainerActivity.class);
                            startActivity(intent);
                        }
                    }
                }else if(response.code() == 401){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "ফোন নম্বর/পাসওয়ার্ড ভুল হয়েছে", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LogIn> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+ t.getLocalizedMessage());
            }
        });
    }

    private void inItView() {
        nextStepBtn = findViewById(R.id.nextStepBtn);
        registerLayBtn = findViewById(R.id.registerLayBtn);
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
    }
}