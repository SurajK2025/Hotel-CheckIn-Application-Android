package com.sklabs.hotelcheckin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText passwordEditText, userNameEditText;
    TextView signinPageIntentTextView;
    Button loginButton;
    ProgressBar progressBar;
    DatabaseReference reff;
    private FirebaseDatabase fbase;
    private FirebaseAuth fAuth;
    public static String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        userNameEditText = (EditText) findViewById(R.id.userNameEditText);
        loginButton = (Button) findViewById(R.id.registerButton);
        signinPageIntentTextView = (TextView) findViewById(R.id.loginPageIntentTextView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        signinPageIntentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateName() | !validatePassword()){
                    return;
                }else{
                    isUser();
                }
            }
        });
    }

    private Boolean validateName() {
        String name = userNameEditText.getText().toString();

        if (name.isEmpty()) {
            userNameEditText.setError("Field cannot be empty");
            return false;
        }
        else {
            userNameEditText.setError(null);
            return true;
        }
    }
    private Boolean validatePassword() {
        String password = passwordEditText.getText().toString();

        if (password.isEmpty()) {
            passwordEditText.setError("Field cannot be empty");
            return false;
        } else {
            passwordEditText.setError(null);
            return true;
        }
    }

    private void isUser(){
        String username = userNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);

        reff = FirebaseDatabase.getInstance().getReference(username);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String databasePassword = snapshot.child("Password").getValue(String.class);
                   if (databasePassword.matches(password)) {
                       currentUsername = username;

                       startActivity(new Intent(getApplicationContext(), SelectRoom.class));
                       progressBar.setVisibility(View.INVISIBLE);
                       finish();
                   }
                   else{
                       Toast.makeText(MainActivity.this, "Wrong Password. Please Try Again!!", Toast.LENGTH_SHORT).show();
                       progressBar.setVisibility(View.INVISIBLE);
                   }

                }
                else{
                    Toast.makeText(MainActivity.this, "User dosen't exist. Please create new account.", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}