package com.library.intent.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.library.R;
import com.library.service.AuthenticationService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class IntentVerifyOTP extends AppCompatActivity {
    PhoneAuthProvider.ForceResendingToken resendingToken;
    FirebaseAuth myAuth;
    EditText codeInput1, codeInput2, codeInput3, codeInput4, codeInput5, codeInput6;
    String verificationID;
    TextView btnBack;
    Button btnVerify, btnResendOPT;
    String ho, ten, gender, userName, phoneInput, password;
    String codeInput;
    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
            signinbyCredentials(credential);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(IntentVerifyOTP.this, "Vertification failed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            verificationID = s;
            Toast.makeText(IntentVerifyOTP.this, "sent OPT success", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_verify_otp_activity);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        myAuth = FirebaseAuth.getInstance();
        if (getIntent().getExtras() != null) {
            ho = getIntent().getExtras().getString("ho");
            ten = getIntent().getExtras().getString("ten");
            gender = getIntent().getExtras().getString("gender");
            userName = getIntent().getExtras().getString("username");
            phoneInput = getIntent().getExtras().getString("phone");
            password = getIntent().getExtras().getString("password");
            sendOtp(phoneInput);
        }

        setControl();
        setEvent();
    }

    private void setControl() {
        codeInput1 = findViewById(R.id.code_input_1);
        codeInput2 = findViewById(R.id.code_input_2);
        codeInput3 = findViewById(R.id.code_input_3);
        codeInput4 = findViewById(R.id.code_input_4);
        codeInput5 = findViewById(R.id.code_input_5);
        codeInput6 = findViewById(R.id.code_input_6);
        btnBack = findViewById(R.id.back);
        btnVerify = findViewById(R.id.btnVerify);
        btnResendOPT = findViewById(R.id.btnResendOPT);
    }

    private void setEvent() {
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (codeInput1.getText().toString().trim().isEmpty() || codeInput2.getText().toString().trim().isEmpty() ||
                        codeInput3.getText().toString().trim().isEmpty() || codeInput4.getText().toString().trim().isEmpty() ||
                        codeInput5.getText().toString().trim().isEmpty() || codeInput6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(IntentVerifyOTP.this, "Vui long nhap day du ma OPT", Toast.LENGTH_SHORT).show();
                    return;
                }

                codeInput = codeInput1.getText().toString().trim() +
                        codeInput2.getText().toString().trim() +
                        codeInput3.getText().toString().trim() +
                        codeInput4.getText().toString().trim() +
                        codeInput5.getText().toString().trim() +
                        codeInput6.getText().toString().trim();

                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, codeInput);

                signinbyCredentials(credential);
            }
        });

        btnResendOPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtp(phoneInput);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dangky_layout = new Intent(IntentVerifyOTP.this, IntentRegister.class);
                startActivity(dangky_layout);
                finish();
            }
        });
    }

    private void sendOtp(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(myAuth)
                        .setPhoneNumber("+84" + phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signinbyCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(IntentVerifyOTP.this, "Verity Succuess", Toast.LENGTH_SHORT).show();

//                    boolean checkExistUser = dbLogin.checkUsername(userName);
                    boolean checkExistUser = true;
                    if (!checkExistUser) {
//                        boolean checkInsert = dbLogin.insertData(ho, ten, gender, userName, phoneInput, password);
                        boolean checkInsert = true;
                        if (checkInsert) {
                            Toast.makeText(IntentVerifyOTP.this, "Register Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(IntentVerifyOTP.this, "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(IntentVerifyOTP.this, "Username da ton tai", Toast.LENGTH_SHORT).show();
                    }

                    Map<String, Object> data = new HashMap<>();
                    data.put("username", userName);
                    data.put("password", password);
                    data.put("last_name", ho);
                    data.put("first_name", ten);
                    data.put("gender", gender.equals("Nam"));
                    data.put("phone", phoneInput);
                    AuthenticationService.register(data, IntentVerifyOTP.this);
                } else {
                    Toast.makeText(IntentVerifyOTP.this, "Verify Failed!" + "\n" + verificationID + "\n" + codeInput, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}