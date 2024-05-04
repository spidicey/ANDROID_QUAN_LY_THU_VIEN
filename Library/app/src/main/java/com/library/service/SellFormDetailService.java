package com.library.service;

import androidx.annotation.NonNull;

import com.library.intent.adapter.sell_form.BookListModifyAdapter;
import com.library.model.Book;
import com.library.model.SellFormDetail;
import com.library.service.api.SellFormDetailAPIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellFormDetailService {
    public static void loadDataAdapter(int formId, BookListModifyAdapter adapter, boolean isValidate) {
        SellFormDetailAPIService.service.filterBySellFormId(formId).enqueue(new Callback<List<SellFormDetail>>() {
            @Override
            public void onResponse(@NonNull Call<List<SellFormDetail>> call, @NonNull Response<List<SellFormDetail>> response) {
                adapter.setDetails(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<SellFormDetail>> call, @NonNull Throwable t) {

            }
        });

        SellFormDetailAPIService.service.listBookOfSellForm(formId, isValidate).enqueue(new Callback<List<Book>>() {
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
