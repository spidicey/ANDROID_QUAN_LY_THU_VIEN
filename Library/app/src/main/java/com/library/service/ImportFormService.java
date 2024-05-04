package com.library.service;

import androidx.annotation.NonNull;

import com.library.intent.adapter.import_form.ImportFormAdapter;
import com.library.model.ImportForm;
import com.library.model.ImportFormDetail;
import com.library.service.api.ImportFormAPIService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImportFormService {
    public static void listImportForm(ImportFormAdapter adapter, List<ImportForm> dataStorage) {
        ImportFormAPIService.service.listImportForm().enqueue(new Callback<List<ImportForm>>() {
            @Override
            public void onResponse(@NonNull Call<List<ImportForm>> call, @NonNull Response<List<ImportForm>> response) {
                if (response.body() != null) {
                    adapter.setData(response.body());
                    dataStorage.clear();
                    dataStorage.addAll(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ImportForm>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void createImportForm(ImportForm form, List<ImportFormDetail> details) {
        Map<String, Object> data = new HashMap<>();
        data.put("form", form);
        data.put("details", details);

        ImportFormAPIService.service.createImportForm(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public static void updateImportForm(ImportForm form, List<ImportFormDetail> details) {
        Map<String, Object> data = new HashMap<>();
        data.put("form", form);
        data.put("details", details);

        ImportFormAPIService.service.updateImportForm(form.getId(), data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public static void deleteImportForm(int formId) {
        ImportFormAPIService.service.deleteImportForm(formId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public static void completeImportForm(int formId) {
        ImportFormAPIService.service.completeImportForm(formId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public static void validateForm(int formId) {
        ImportFormAPIService.service.validateForm(formId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }
}
