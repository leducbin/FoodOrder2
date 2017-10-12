package com.ldb.bin.foodorder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {
    MaterialEditText edtPhone,edtPassword;
    Button btnSignIn;
    ProgressDialog pDialog;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        AnhXa();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtPhone.getText().toString().trim() != null && edtPassword.getText().toString().trim() != null)
                {
                    pDialog = new ProgressDialog(SignIn.this);
                    pDialog.setMessage("Please wait...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    String response = "http://172.16.1.107/api/login";
                    RequestQueue requestQueue = Volley.newRequestQueue(SignIn.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, response, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response != null) {
                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONObject data =jsonObject.getJSONObject("data");
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("phone",data.getString("phone"));
                                    editor.putString("api_token",data.getString("api_token"));
                                    editor.commit();
                                    Intent intent = new Intent(SignIn.this,Home.class);
                                    SignIn.this.startActivity(intent);
                                } catch (JSONException e) {

                                }
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
                                        builder = new AlertDialog.Builder(SignIn.this, android.R.style.Theme_Material_Dialog_Alert);
                                    } else {
                                        builder = new AlertDialog.Builder(SignIn.this);
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
                            params.put("password", edtPassword.getText().toString().trim());
                            return params;
                        }
                    };

                    requestQueue.add(stringRequest);
                }
            }

        });


    }

    private void AnhXa() {
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone);
        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword);
        btnSignIn =(Button) findViewById(R.id.btnSignIn);
        sharedPreferences = getSharedPreferences("dataLogin_food",MODE_PRIVATE);
    }
}
