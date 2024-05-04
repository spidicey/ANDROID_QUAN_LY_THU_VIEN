package com.library.service.api;

import com.library.model.BorrowForm;
import com.library.model.BorrowFormDetail;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BorrowFormAPIService extends APIService {
    BorrowFormAPIService service = BUILDER.create(BorrowFormAPIService.class);

    @GET("management/borrow-forms/")
    Call<List<BorrowForm>> listBorrowForm();

    @POST("management/borrow-forms/")
    Call<Void> createBorrowForm(@Body Map<String, Object> data);

    @PUT("management/borrow-forms/{id}/")
    Call<Void> updateBorrowForm(@Path("id") int formId, @Body List<BorrowFormDetail> details);
}
