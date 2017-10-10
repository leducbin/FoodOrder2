package com.ldb.bin.foodorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.rengwuxian.materialedittext.MaterialEditText;

public class SignIn extends AppCompatActivity {
    MaterialEditText edtPhone,edtPassword;
    Button btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        AnhXa();
    }

    private void AnhXa() {
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        btnSignIn =(Button) findViewById(R.id.btnSignIn);
    }
}
