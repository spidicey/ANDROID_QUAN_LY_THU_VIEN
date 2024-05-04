package com.library.service.api;

import com.library.model.Book;
import com.library.model.BorrowFormDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BorrowFormDetailAPIService extends APIService {
    BorrowFormDetailAPIService service = BUILDER.create(BorrowFormDetailAPIService.class);

    @GET("management/borrow-form-details/filter_by_borrow_form_id/")
    Call<List<BorrowFormDetail>> filterByBorrowFormId(@Query("id") int formId);

    @GET("management/borrow-form-details/list_book_of_borrow_form/")
    Call<List<Book>> listBookOfBorrowForm(@Query("id") int formId);
}
