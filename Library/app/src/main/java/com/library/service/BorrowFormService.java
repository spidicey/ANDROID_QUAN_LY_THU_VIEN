package com.library.service;

import androidx.annotation.NonNull;

import com.library.intent.adapter.borrow_form.BorrowFormAdapter;
import com.library.model.BorrowForm;
import com.library.model.BorrowFormDetail;
import com.library.service.api.BorrowFormAPIService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowFormService {
    public static void listBorrowForm(BorrowFormAdapter adapter, List<BorrowForm> dataStorage) {
        BorrowFormAPIService.service.listBorrowForm().enqueue(new Callback<List<BorrowForm>>() {
            @Override
            public void onResponse(@NonNull Call<List<BorrowForm>> call, @NonNull Response<List<BorrowForm>> response) {
                if (response.body() != null) {
                    adapter.setData(response.body());
                    dataStorage.clear();
                    dataStorage.addAll(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BorrowForm>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void createBorrowForm(BorrowForm form, List<BorrowFormDetail> details) {
        Map<String, Object> data = new HashMap<>();
        data.put("form", form);
        data.put("details", details);
        BorrowFormAPIService.service.createBorrowForm(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public static void updateBorrowForm(BorrowForm form, List<BorrowFormDetail> details) {
        BorrowFormAPIService.service.updateBorrowForm(form.getId(), details).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }
}
