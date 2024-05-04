package com.library.service.api;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ValidateFormAPIService extends APIService {
    ValidateFormAPIService service = BUILDER.create(ValidateFormAPIService.class);

    @GET("/management/validate-forms/")
    Call<JsonElement> listValidateForm();
}
