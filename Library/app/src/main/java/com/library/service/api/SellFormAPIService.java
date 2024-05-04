package com.library.service.api;

import com.library.model.SellForm;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SellFormAPIService extends APIService {
    SellFormAPIService service = BUILDER.create(SellFormAPIService.class);

    @GET("management/sell-forms/")
    Call<List<SellForm>> listSellForm();

    @POST("management/sell-forms/")
    Call<Void> createSellForm(@Body Map<String, Object> data);

    @PUT("management/sell-forms/{id}/")
    Call<Void> updateSellForm(@Path("id") int formId, @Body Map<String, Object> data);

    @DELETE("management/sell-forms/{id}/")
    Call<Void> deleteSellForm(@Path("id") int formId);

    @PATCH("management/sell-forms/{id}/complete_form/")
    Call<Void> completeSellForm(@Path("id") int formId);

    @PATCH("management/sell-forms/{id}/validate_form/")
    Call<Void> validateForm(@Path("id") int formId);
}
