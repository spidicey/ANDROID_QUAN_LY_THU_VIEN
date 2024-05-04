package com.library.intent.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.library.R;
import com.library.service.AuthenticationService;

import java.util.HashMap;
import java.util.Map;

public class IntentLogin extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin, btnSignup;
    CheckBox cbLuuTaiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_login);
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setControl();
        setEvent();
    }

    private void setControl() {
        edtUsername = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btn_sign_up);
        cbLuuTaiKhoan = findViewById(R.id.cbLuuTK);
    }

    private void setEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (checkInput(username, password)) {
                    login(username, password);
                    // REMEMBER ACCOUNT.
                    if (cbLuuTaiKhoan.isChecked()) writePassword(username, password);
                    else notWritePassword();
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_layout = new Intent(IntentLogin.this, IntentRegister.class);
                startActivity(register_layout);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("SaveTK", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");

        edtUsername.setText(username);
        edtPassword.setText(password);
    }

    private void writePassword(String username, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences("SaveTK", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

    private void notWritePassword() {
        SharedPreferences sharedPreferences = getSharedPreferences("SaveTK", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    private void showError(EditText edtText) {
        edtText.setError("Vui lòng nhập trường này");
        edtText.requestFocus();
    }

    private boolean checkInput(String username, String password) {
        if (username.isEmpty()) {
            showError(edtUsername);
            return false;
        } else if (password.isEmpty()) {
            showError(edtPassword);
            return false;
        }
        return true;
    }

    private void login(String username, String password) {
        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);
        AuthenticationService.login(data, this);
    }
}