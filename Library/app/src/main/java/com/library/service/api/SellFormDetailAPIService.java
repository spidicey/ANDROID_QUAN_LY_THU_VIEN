package com.library.service.api;

import com.library.model.Book;
import com.library.model.SellFormDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SellFormDetailAPIService extends APIService {
    SellFormDetailAPIService service = BUILDER.create(SellFormDetailAPIService.class);

    @GET("management/sell-form-details/filter_by_sell_form_id/")
    Call<List<SellFormDetail>> filterBySellFormId(@Query("id") int formId);

    @GET("management/sell-form-details/list_book_of_sell_form/")
    Call<List<Book>> listBookOfSellForm(@Query("id") int formId, @Query("is_validate") boolean isValidate);
}
