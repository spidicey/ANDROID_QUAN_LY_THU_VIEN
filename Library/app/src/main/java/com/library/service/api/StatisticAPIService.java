package com.library.service.api;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StatisticAPIService extends APIService {
    StatisticAPIService service = BUILDER.create(StatisticAPIService.class);

    @GET("information/graph-book-type/")
    Call<JsonArray> graphBookType();

    @GET("management/graph-book-sell/")
    Call<JsonArray> graphBookSell();

    @GET("management/graph-book-borrow/")
    Call<JsonArray> graphBookBorrow();
}
