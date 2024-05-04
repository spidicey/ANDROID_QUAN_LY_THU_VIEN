package com.library.service.api.interceptor;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CustomInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        // GET ORIGINAL REQUEST.
        Request originalRequest = chain.request();
        Request.Builder modifiedRequest = originalRequest.newBuilder()
                .method(originalRequest.method(), originalRequest.body());
        // ADD HEADER TO REQUEST.
        String accessToken = TokenStorage.getAccessToken();
        if (accessToken != null) {
            modifiedRequest = modifiedRequest.header("Authorization", String.format("Bearer %s", accessToken));
        }
        // CONTINUE TO EXECUTE REQUEST.
        return chain.proceed(modifiedRequest.build());
    }
}
