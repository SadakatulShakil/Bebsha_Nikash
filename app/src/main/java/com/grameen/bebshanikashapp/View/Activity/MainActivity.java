package com.grameen.bebshanikashapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.grameen.bebshanikashapp.R;

public class MainActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton nextStepBtn;
    private TextView registerLayBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inItView();

        nextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FragmentContainerActivity.class);
                startActivity(intent);
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

    private void inItView() {
        nextStepBtn = findViewById(R.id.nextStepBtn);
        registerLayBtn = findViewById(R.id.registerLayBtn);
    }
}