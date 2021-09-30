package com.sklabs.hotelcheckin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText, mobilenumberEditText, emailEditText;
    TextView loginPageIntentTextView;
    Button registerButton;
    boolean check;
    ProgressBar progressBar;
    DatabaseReference reff;
    private FirebaseDatabase fbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Log.i("Message", "Back Again");

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        mobilenumberEditText = (EditText) findViewById(R.id.mobilenumberEditText);
        emailEditText = (EditText) findViewById(R.id.userNameEditText);
        registerButton = (Button) findViewById(R.id.registerButton);
        loginPageIntentTextView = (TextView) findViewById(R.id.loginPageIntentTextView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        check = false;
        MainActivity.currentUsername = null;

        loginPageIntentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fbase = FirebaseDatabase.getInstance();
                reff = fbase.getReference();

                String name = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String number = mobilenumberEditText.getText().toString();

                if(!check) {
                    new AlertDialog.Builder(SignInActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Are you shure?")
                            .setMessage("An OTP will be sent to " + number + ". Do you want to continue.")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("Name", usernameEditText.getText().toString());
                                    map.put("Email", emailEditText.getText().toString());
                                    map.put("MobileNumber", mobilenumberEditText.getText().toString());
                                    map.put("Password", passwordEditText.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child(name).updateChildren(map);

                                    MainActivity.currentUsername = name;

                                    Log.i("Check", "Successfull");

                                    Intent intent = new Intent(getApplicationContext(), OTPVerification.class);
                                    intent.putExtra("Phonenumber", number);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Change Number", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mobilenumberEditText.setText("");
                                    mobilenumberEditText.requestFocus();
                                    check = true;
                                }
                            })
                            .show();
                }
                else{
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("Name", usernameEditText.getText().toString());
                    map.put("Email", emailEditText.getText().toString());
                    map.put("MobileNumber", mobilenumberEditText.getText().toString());
                    map.put("Password", passwordEditText.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child(name).updateChildren(map);

                    MainActivity.currentUsername = name;

                    Intent intent = new Intent(getApplicationContext(), OTPVerification.class);
                    intent.putExtra("Phonenumber", number);
                    startActivity(intent);
                }
            }
        });


    }


}