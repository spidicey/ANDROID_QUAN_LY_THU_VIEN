package com.library.service;

import androidx.annotation.NonNull;

import com.library.intent.adapter.borrow_form.BookListModifyAdapter;
import com.library.model.Book;
import com.library.model.BorrowForm;
import com.library.model.BorrowFormDetail;
import com.library.service.api.BorrowFormDetailAPIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowFormDetailService {
    public static void loadDataAdapter(BorrowForm form, BookListModifyAdapter adapter) {
        BorrowFormDetailAPIService.service.filterByBorrowFormId(form.getId()).enqueue(new Callback<List<BorrowFormDetail>>() {
            @Override
            public void onResponse(@NonNull Call<List<BorrowFormDetail>> call, @NonNull Response<List<BorrowFormDetail>> response) {
                adapter.setDetails(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<BorrowFormDetail>> call, @NonNull Throwable t) {

            }
        });

        BorrowFormDetailAPIService.service.listBookOfBorrowForm(form.getId()).enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Book>> call, @NonNull Response<List<Book>> response) {
                adapter.setData(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {

            }
        });
    }
}
