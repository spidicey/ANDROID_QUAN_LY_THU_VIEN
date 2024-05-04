package com.library.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.library.intent.auth.IntentLogin;
import com.library.intent.auth.IntentVerifyOTP;
import com.library.main.MainActivity;
import com.library.service.api.AuthenticationAPIService;
import com.library.service.api.interceptor.TokenStorage;

import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationService {
    public static void login(Map<String, String> data, Context context) {
        AuthenticationAPIService.service.login(data).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                try {
                    // GET JSON RESPONSE.
                    JsonObject responseData = Objects.requireNonNull(response.body()).getAsJsonObject();
                    String accessToken = responseData.get("access").getAsString();
                    String refreshToken = responseData.get("refresh").getAsString();
                    // STORE ACCESS & REFRESH TOKEN.
                    TokenStorage.setAccessToken(accessToken);
                    TokenStorage.setRefreshToken(refreshToken);
                    // REDIRECT TO MAIN PAGE.
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "Thông tin đăng nhập không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {
                Toast.makeText(context, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void register(Map<String, Object> data, Context context) {
        AuthenticationAPIService.service.register(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("SaveTK", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", (String) data.get("username"));
                    editor.putString("password", (String) data.get("password"));
                    editor.apply();

                    context.startActivity(new Intent(context, IntentLogin.class));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public static void checkAccount(Map<String, String> data, Context context, Map<String, Object> registerData) {
        AuthenticationAPIService.service.checkAccount(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent verify_layout = new Intent(context, IntentVerifyOTP.class);
                    verify_layout.putExtra("phone", (String) registerData.get("phone"));
                    verify_layout.putExtra("ho", (String) registerData.get("ho"));
                    verify_layout.putExtra("ten", (String) registerData.get("ten"));
                    verify_layout.putExtra("gender", (String) registerData.get("gender"));
                    verify_layout.putExtra("username", (String) registerData.get("username"));
                    verify_layout.putExtra("password", (String) registerData.get("password"));
                    verify_layout.putExtra("pathImage", (String) registerData.get("pathImage"));
                    context.startActivity(verify_layout);
                } else {
                    Toast.makeText(context, "Tài Khoản đã tồn tài!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }
}
