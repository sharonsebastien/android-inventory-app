package com.example.inventoryapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBaseHelper(getApplicationContext());

        SharedPreferences sh = getSharedPreferences("userId",MODE_PRIVATE);
        int userId = sh.getInt("userId",-1);

        if(userId > 0) {
            ModelUser user = db.getUser(userId);

            if(user.getUserType() != null && user.getUserType().equals("ADMIN")) {
                redirectAdminLanding();
            } else if(user.getUserType() != null && user.getUserType().equals("USER")) {
                redirectUserLanding();
            }
        }
    }
    public void redirectUserLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), UserLogin.class);
        startActivity(intent);
    }
    public void redirectAdminLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminLogin.class);
        startActivity(intent);
    }
    public void redirectAdminLanding() {
        Intent intent = new Intent(getApplicationContext(), AdminMain.class);
        startActivity(intent);
    }
    public void redirectUserLanding() {
        Intent intent = new Intent(getApplicationContext(), UserMain.class);
        startActivity(intent);
    }


}