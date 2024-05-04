package com.library.service.api;

import com.google.gson.JsonElement;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationAPIService extends APIService {
    AuthenticationAPIService service = BUILDER.create(AuthenticationAPIService.class);

    @POST("api/token/")
    Call<JsonElement> login(@Body Map<String, String> account);

    @POST("api/register/")
    Call<Void> register(@Body Map<String, Object> data);

    @POST("api/check-account/")
    Call<Void> checkAccount(@Body Map<String, String> data);
}
