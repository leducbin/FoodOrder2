package com.ldb.bin.foodorder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

import info.hoang8f.widget.FButton;

public class SignUp extends AppCompatActivity {
    MaterialEditText edtPhone,edtName,edtPassword,edtPasswordConfirm;
    FButton btnSignUp;
    ProgressDialog pDialog;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AnhXa();

        edtPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!edtPassword.getText().equals(edtPasswordConfirm.getText()))
                {
                    Toast.makeText(SignUp.this,"Mật khẩu xác nhận chưa giống!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pDialog = new ProgressDialog(SignUp.this);
                pDialog.setMessage("Please wait...");
                pDialog.setCancelable(false);
                pDialog.show();
                if(edtPhone.getText().toString().trim() != null && edtName.getText().toString().trim() != null
                        && edtPasswordConfirm.getText().toString().trim() != null && edtPassword.getText().toString().trim() != null
                        && edtPassword.getText().equals(edtPasswordConfirm.getText())
                        )
                {
                    String response = "http://172.16.1.107/api/register";
                    RequestQueue requestQueue = Volley.newRequestQueue(SignUp.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, response, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response != null) {
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                Toast.makeText(SignUp.this, "Bạn đã đăng ký thành công!", Toast.LENGTH_LONG).show();

                            }
                        }
                    },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    if (pDialog.isShowing())
                                        pDialog.dismiss();
                                    AlertDialog.Builder builder;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        builder = new AlertDialog.Builder(SignUp.this, android.R.style.Theme_Material_Dialog_Alert);
                                    } else {
                                        builder = new AlertDialog.Builder(SignUp.this);
                                    }
                                    builder.setTitle("Dữ liệu serve bị lỗi, hãy kiểm tra kết nối!")
                                            .setNegativeButton("Oke", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("phone", edtPhone.getText().toString().trim());
                            params.put("name", edtName.getText().toString().trim());
                            params.put("password", edtPassword.getText().toString().trim());
                            params.put("password_confirmation", edtPasswordConfirm.getText().toString().trim());
                            return params;
                        }
                    };

                    requestQueue.add(stringRequest);
                }else
                {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(SignUp.this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(SignUp.this);
                    }
                    builder.setTitle("Hãy kiểm tra lại và điền đầy đủ thông tin.")
                            .setNegativeButton("Oke", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });

    }

    private void AnhXa() {
        edtPhone =(MaterialEditText) findViewById(R.id.edtPhone);
        edtName =(MaterialEditText) findViewById(R.id.edtName);
        edtPassword =(MaterialEditText) findViewById(R.id.edtPassword);
        edtPasswordConfirm =(MaterialEditText) findViewById(R.id.edtPassword_confirm);
        btnSignUp =(FButton) findViewById(R.id.btnSignUp);
        sharedPreferences = getSharedPreferences("dataLogin_food",MODE_PRIVATE);
    }
}
