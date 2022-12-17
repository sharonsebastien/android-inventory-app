package com.example.inventoryapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;

public class UserMain extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    DataBaseHelper db;
    int activeUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        db = new DataBaseHelper(getApplicationContext());

        setLayoutTitle("User Dashboard");

        SharedPreferences sh = getSharedPreferences("userId",MODE_PRIVATE);
        int userId = sh.getInt("userId",-1);
        if(userId < 0) {
            redirectToLogin();
        } else {
            activeUserId = userId;
        }

        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navView = findViewById(R.id.navigation_view);
        navView.bringToFront();
        navView.setNavigationItemSelectedListener(this);

        loadFragment(new UserLandingFragment());
    }

    public void setLayoutTitle(String layoutTitle) {
        this.setTitle(layoutTitle);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("TAG", "onOptionsItemSelected: " + item.getItemId());
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d("TAG", "onNavigationItemSelected: " + item.getItemId());
        switch (item.getItemId()) {
            case R.id.nav_user_profile:
                loadFragment(new UserProfileFragment());
                setLayoutTitle("User Profile");
                break;
            case R.id.nav_home:
                loadFragment(new UserLandingFragment());
                setLayoutTitle("User Dashboard");
                break;
            case R.id.item_list:
                loadFragment(new UserItemList());
                setLayoutTitle("Item List");
                break;
            case R.id.nav_order_history:
                loadFragment(new UserOrderListFragment());
                setLayoutTitle("Order History");
                break;
            case R.id.nav_cart:
                loadFragment(new UserCartFragment());
                setLayoutTitle("Cart Page");
                break;
            case R.id.nav_logout:
                SharedPreferences sn = getSharedPreferences("userId", MODE_PRIVATE);
                sn.edit().remove("userId").commit();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            default:
        }
        drawerLayout.close();
        return true;
    }

    public void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }

    public void redirectToLogin() {
        Intent intent = new Intent(getApplicationContext(), AdminLogin.class);
        startActivity(intent);
    }
    public int getActiveUserId() {
        return activeUserId;
    }
}