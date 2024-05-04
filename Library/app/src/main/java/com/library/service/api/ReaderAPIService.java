package com.library.service.api;

import com.library.model.Reader;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ReaderAPIService extends APIService {
    ReaderAPIService service = BUILDER.create(ReaderAPIService.class);

    @GET("information/readers/")
    Call<List<Reader>> listReader();

    @GET("information/readers/{id}/")
    Call<Reader> getReaderById(@Path("id") int readerId);

    @PUT("information/readers/{id}/")
    Call<Void> updateReader(@Path("id") int readerId, @Body Reader reader);
}
