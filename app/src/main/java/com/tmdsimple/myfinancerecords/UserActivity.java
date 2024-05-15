package com.tmdsimple.myfinancerecords;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.util.Log;


public class UserActivity extends AppCompatActivity {

    Button btnLogOut;
    TextView txtUser;
    TextView txtusername;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    private FirebaseAuth.AuthStateListener authStateListener;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        firebaseAuth = FirebaseAuth.getInstance();
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        txtUser = (TextView) findViewById(R.id.txtUser);
        txtusername = (TextView) findViewById(R.id.txtusername);
        user = firebaseAuth.getCurrentUser();


        if (user == null ) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            txtUser.setText(user.getEmail());
        }

        if (user == null ) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            txtUser.setText(user.getEmail());
        }

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                //Intent I = new Intent(UserActivity.this, LoginActivity.class);
                //startActivity(I);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}

