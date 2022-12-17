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

public class AdminMain extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        db = new DataBaseHelper(getApplicationContext());
        setLayoutTitle("Admin Dashboard");

        //LOGIN AUTHENTICATE CODE
        SharedPreferences sh = getSharedPreferences("userId",MODE_PRIVATE);
        int userId = sh.getInt("userId",-1);
        if(userId < 0) {
            redirectToLogin();
        }

        drawerLayout = findViewById(R.id.drawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navView = findViewById(R.id.navigation_view);
        navView.bringToFront();
        navView.setNavigationItemSelectedListener(this);

        loadFragment(new AdminLandingFragment());
    }

    public void setLayoutTitle(String layoutTitle) {
        this.setTitle(layoutTitle);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_admin_profile:
                loadFragment(new AdminProfileFragment());
                setLayoutTitle("Admin Profile");
                break;
            case R.id.nav_home:
                loadFragment(new AdminLandingFragment());
                setLayoutTitle("Admin Dashboard");
                break;
            case R.id.item_list:
                loadFragment(new AdminItemListFragment());
                setLayoutTitle("Item List");
                break;
            case R.id.nav_order_history:
                loadFragment(new AdminOrderListFragment());
                setLayoutTitle("Order History");
                break;
            case R.id.user_list:
                loadFragment(new AdminUserListFragment());
                setLayoutTitle("User List");
                break;
            case R.id.category_list:
                loadFragment(new AdminCategoryList());
                setLayoutTitle("Category List");
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

    public void loadFragment(Fragment fragment,String ...names) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(names.length > 1) {
            Bundle bundle = new Bundle();
            bundle.putString("type", names[0]);
            bundle.putString("id", names[1]);
            fragment.setArguments(bundle);
        }

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
}