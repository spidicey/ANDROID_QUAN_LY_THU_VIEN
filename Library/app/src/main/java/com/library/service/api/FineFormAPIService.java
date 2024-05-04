package com.library.service.api;

import com.library.model.FineForm;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FineFormAPIService extends APIService {
    FineFormAPIService service = BUILDER.create(FineFormAPIService.class);

    @GET("management/fine-forms/")
    Call<List<FineForm>> listFineForm();

    @POST("management/fine-forms/")
    Call<Void> createFineForm(@Body FineForm form);

    @PUT("management/fine-forms/{id}/")
    Call<Void> updateFineForm(@Path("id") int formId, @Body FineForm form);

    @DELETE("management/fine-forms/{id}/")
    Call<Void> deleteFineForm(@Path("id") int formId);
}
