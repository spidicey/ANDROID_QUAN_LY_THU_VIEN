package com.library.service;

import androidx.annotation.NonNull;

import com.library.intent.adapter.import_form.BookListModifyAdapter;
import com.library.model.Book;
import com.library.model.ImportFormDetail;
import com.library.service.api.ImportFormDetailAPIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImportFormDetailService {
    public static void loadDataAdapter(int formId, BookListModifyAdapter adapter, boolean isValidate) {
        ImportFormDetailAPIService.service.filterByImportFormId(formId).enqueue(new Callback<List<ImportFormDetail>>() {
            @Override
            public void onResponse(@NonNull Call<List<ImportFormDetail>> call, @NonNull Response<List<ImportFormDetail>> response) {
                adapter.setDetails(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<ImportFormDetail>> call, @NonNull Throwable t) {

            }
        });

        ImportFormDetailAPIService.service.listBookOfImportForm(formId, isValidate).enqueue(new Callback<List<Book>>() {
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
