package com.ldb.bin.foodorder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ldb.bin.foodorder.Adapter.RecyclerViewAdapter;
import com.ldb.bin.foodorder.Model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView txtFullName;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog pDialog;
    ArrayList<Category> array_category = new ArrayList<Category>();
    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.e("TAG","THử đây");
        toolbar = (Toolbar) findViewById(R.id.toolbar_menu);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView) headerView.findViewById(R.id.txtFullName);
        sharedPreferences = getSharedPreferences("dataLogin_food",MODE_PRIVATE);
        txtFullName.setText(sharedPreferences.getString("phone",""));
        GetArrayList();
    }

    private void GetArrayList() {
        pDialog = new ProgressDialog(Home.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        pDialog.show();

        final String token_user = sharedPreferences.getString("api_token","");
        String response = "http://172.16.1.107/api/category";
        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, response, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {

                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    try {
                        JSONArray json_ar = new JSONArray(response);
                        for(int i=0; i<json_ar.length();i++)
                        {
                            JSONObject json_ob = json_ar.getJSONObject(i);
                            Log.e("TAG","data " + json_ar.getJSONObject(i));
                            Category tmp = new Category();
                            tmp.setName(json_ob.getString("name"));
                            tmp.setImage(json_ob.getString("image"));
                            array_category.add(tmp);
                        }

                        recyclerView = findViewById(R.id.recycler_manu);
                        recyclerView.setHasFixedSize(true);
                        layoutManager = new LinearLayoutManager(Home.this);
                        recyclerView.setLayoutManager(layoutManager);

                        Log.e("TAG","data " + array_category);
                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(Home.this,array_category);
                        recyclerView.setAdapter(adapter);

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
                            builder = new AlertDialog.Builder(Home.this, android.R.style.Theme_Material_Dialog_Alert);
                        } else {
                            builder = new AlertDialog.Builder(Home.this);
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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Api-Token", token_user);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_cart) {
        } else if (id == R.id.nav_orders) {
        } else if (id == R.id.nav_log_out) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
