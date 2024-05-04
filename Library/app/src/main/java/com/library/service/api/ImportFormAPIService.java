package com.library.service.api;

import com.library.model.ImportForm;

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

public interface ImportFormAPIService extends APIService {
    ImportFormAPIService service = BUILDER.create(ImportFormAPIService.class);

    @GET("management/import-forms/")
    Call<List<ImportForm>> listImportForm();

    @POST("management/import-forms/")
    Call<Void> createImportForm(@Body Map<String, Object> data);

    @PUT("management/import-forms/{id}/")
    Call<Void> updateImportForm(@Path("id") int formId, @Body Map<String, Object> data);

    @DELETE("management/import-forms/{id}/")
    Call<Void> deleteImportForm(@Path("id") int formId);

    @PATCH("management/import-forms/{id}/complete_form/")
    Call<Void> completeImportForm(@Path("id") int formId);

    @PATCH("management/import-forms/{id}/validate_form/")
    Call<Void> validateForm(@Path("id") int formId);
}
