package com.library.service;

import androidx.annotation.NonNull;

import com.library.intent.adapter.fine_form.FineFormAdapter;
import com.library.model.FineForm;
import com.library.service.api.FineFormAPIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FineFormService {
    public static void listFineForm(FineFormAdapter adapter, List<FineForm> dataStorage) {
        FineFormAPIService.service.listFineForm().enqueue(new Callback<List<FineForm>>() {
            @Override
            public void onResponse(@NonNull Call<List<FineForm>> call, @NonNull Response<List<FineForm>> response) {
                if (response.body() != null) {
                    adapter.setData(response.body());
                    dataStorage.clear();
                    dataStorage.addAll(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<FineForm>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void createFineForm(FineForm form) {
        FineFormAPIService.service.createFineForm(form).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public static void updateFineForm(FineForm form) {
        FineFormAPIService.service.updateFineForm(form.getId(), form).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public static void deleteFineForm(int formId) {
        FineFormAPIService.service.deleteFineForm(formId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }
}
