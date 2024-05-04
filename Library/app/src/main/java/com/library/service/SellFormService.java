package com.library.service;

import androidx.annotation.NonNull;

import com.library.intent.adapter.sell_form.SellFormAdapter;
import com.library.model.SellForm;
import com.library.model.SellFormDetail;
import com.library.service.api.SellFormAPIService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellFormService {
    public static void listSellForm(SellFormAdapter adapter, List<SellForm> dataStorage) {
        SellFormAPIService.service.listSellForm().enqueue(new Callback<List<SellForm>>() {
            @Override
            public void onResponse(@NonNull Call<List<SellForm>> call, @NonNull Response<List<SellForm>> response) {
                if (response.body() != null) {
                    adapter.setData(response.body());
                    dataStorage.clear();
                    dataStorage.addAll(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SellForm>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void createSellForm(SellForm form, List<SellFormDetail> details) {
        Map<String, Object> data = new HashMap<>();
        data.put("form", form);
        data.put("details", details);

        SellFormAPIService.service.createSellForm(data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public static void updateSellForm(SellForm form, List<SellFormDetail> details) {
        Map<String, Object> data = new HashMap<>();
        data.put("form", form);
        data.put("details", details);

        SellFormAPIService.service.updateSellForm(form.getId(), data).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public static void deleteSellForm(int formId) {
        SellFormAPIService.service.deleteSellForm(formId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public static void completeSellForm(int formId) {
        SellFormAPIService.service.completeSellForm(formId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public static void validateForm(int formId) {
        SellFormAPIService.service.validateForm(formId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }
}
