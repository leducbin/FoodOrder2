package com.ldb.bin.foodorder;

import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SignUp.class);
                MainActivity.this.startActivity(intent);
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this,SignIn.class);
                    MainActivity.this.startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        txtSlogan = (TextView) findViewById(R.id.txtSlogan);
        btnSignIn = (FButton) findViewById(R.id.btnSignIn);
        btnSignUp = (FButton) findViewById(R.id.btnSignUp);
        sharedPreferences = getSharedPreferences("dataLogin_food",MODE_PRIVATE);

    }
}
