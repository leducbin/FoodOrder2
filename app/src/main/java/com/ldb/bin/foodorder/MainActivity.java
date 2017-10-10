package com.ldb.bin.foodorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import info.hoang8f.widget.FButton;

public class MainActivity extends AppCompatActivity {

    TextView txtSlogan;
    FButton btnSignIn,btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"hello sign up",Toast.LENGTH_SHORT).show();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"hello sign in",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AnhXa() {
        txtSlogan = (TextView) findViewById(R.id.txtSlogan);
        btnSignIn = (FButton) findViewById(R.id.btnSignIn);
        btnSignUp = (FButton) findViewById(R.id.btnSignUp);

    }
}
