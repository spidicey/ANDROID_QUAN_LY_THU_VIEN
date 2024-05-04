package com.library.service.api.interceptor;

import com.auth0.android.jwt.JWT;

public class TokenStorage {
    private static String ACCESS_TOKEN, REFRESH_TOKEN;

    public static String getAccessToken() {
        return ACCESS_TOKEN;
    }

    public static void setAccessToken(String accessToken) {
        ACCESS_TOKEN = accessToken;
    }

    public static String getRefreshToken() {
        return REFRESH_TOKEN;
    }

    public static void setRefreshToken(String refreshToken) {
        REFRESH_TOKEN = refreshToken;
    }

    public static JWT getTokenData() {
        try {
            return new JWT(ACCESS_TOKEN);
        } catch (Exception e) {
            return null;
        }
    }
}
