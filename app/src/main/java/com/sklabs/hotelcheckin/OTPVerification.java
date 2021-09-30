package com.sklabs.hotelcheckin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OTPVerification extends AppCompatActivity {
    Button validateOTPButton;
    String codeBySystem;
    private FirebaseDatabase fbase;
    private FirebaseAuth fAuth;
    EditText inputCode1, inputCode2, inputCode3, inputCode4, inputCode5, inputCode6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p_verification);
        fAuth = FirebaseAuth.getInstance();

        validateOTPButton = (Button) findViewById(R.id.validateOTPButton);
        inputCode1 = (EditText) findViewById(R.id.inputCode1);
        inputCode2 = (EditText) findViewById(R.id.inputCode2);
        inputCode3 = (EditText) findViewById(R.id.inputCode3);
        inputCode4 = (EditText) findViewById(R.id.inputCode4);
        inputCode5 = (EditText) findViewById(R.id.inputCode5);
        inputCode6 = (EditText) findViewById(R.id.inputCode6);
        setupOTPInputs();

        String phoneNo = getIntent().getStringExtra("Phonenumber");
        sendVerificationCode(phoneNo);

        validateOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = inputCode1.getText().toString() +
                        inputCode2.getText().toString() +
                        inputCode3.getText().toString() +
                        inputCode4.getText().toString() +
                        inputCode5.getText().toString() +
                        inputCode6.getText().toString();

                if(!code.isEmpty()){
                    //add animation here
                    verifyCode(code);
                }
            }
        });

    }
    private void sendVerificationCode(String phoneNo){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(fAuth)
                        .setPhoneNumber("+91"+phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeBySystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                inputCode1.setText(code.charAt(0));
                inputCode2.setText(code.charAt(1));
                inputCode3.setText(code.charAt(2));
                inputCode4.setText(code.charAt(3));
                inputCode5.setText(code.charAt(4));
                inputCode6.setText(code.charAt(5));
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OTPVerification.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(getApplicationContext(), SelectRoom.class));
                            finish();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            }
                        }
                    }
                });
    }

    private void setupOTPInputs(){
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}