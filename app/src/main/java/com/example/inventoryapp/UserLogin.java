package com.example.inventoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserLogin extends AppCompatActivity {
    EditText phoneNumber,password;
    Button loginBtn;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        db = new DataBaseHelper(getApplicationContext());

        SharedPreferences sh = getSharedPreferences("userId",MODE_PRIVATE);
        int userId = sh.getInt("userId",-1);
        if(userId > 0) {
            ModelUser user = db.getUser(userId);
            if(user.getUserType() != null && user.getUserType().equals("USER")) {
                redirectUserLanding();
            }
        }

        phoneNumber = (EditText) findViewById(R.id.phoneNumber);
        password = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.btnLogin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value1 = phoneNumber.getText().toString();
                String value2 = password.getText().toString();
                boolean validated = true;

                if(value1.length() != 10) {
                    phoneNumber.setError("Enter valid Phone Number");
                    validated = false;
                } else {
                    phoneNumber.setError("",null);
                }
                if(value2.length() == 0) {
                    password.setError("Enter valid Password");
                    validated = false;
                } else {
                    password.setError("",null);
                }
                if(validated) {
                    int loginValue = db.userLogin(value1, value2);
                    if(loginValue < 0) {
                        Toast.makeText(UserLogin.this, "Invalid PhoneNumber Or Password", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sh = getSharedPreferences("userId",MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sh.edit();

                        myEdit.putInt("userId", loginValue);
                        myEdit.apply();
                        redirectUserLanding();
                    }
                }
            }
        });
    }
    public void redirectUserLanding() {
        Intent intent = new Intent(getApplicationContext(), UserMain.class);
        startActivity(intent);
    }
}