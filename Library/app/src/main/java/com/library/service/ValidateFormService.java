package com.library.service;

import androidx.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.library.intent.adapter.validate_form.ValidateFormAdapter;
import com.library.model.ImportForm;
import com.library.model.SellForm;
import com.library.service.api.ValidateFormAPIService;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidateFormService {
    public static void listValidateForm(List<SellForm> listSellForm, List<ImportForm> listImportForm,
                                        ValidateFormAdapter adapter, boolean formType) {
        ValidateFormAPIService.service.listValidateForm().enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(@NonNull Call<JsonElement> call, @NonNull Response<JsonElement> response) {
                if (response.body() != null) {
                    JsonObject responseData = response.body().getAsJsonObject();
                    listSellForm.clear();
                    listImportForm.clear();

                    responseData.getAsJsonArray("sell_forms").forEach(e -> {
                        JsonObject formData = e.getAsJsonObject();
                        SellForm form = new SellForm();
                        form.setId(formData.get("id").getAsInt());
                        form.setCreatedDate(formData.get("created_date").getAsString());
                        form.setReason(formData.get("reason").getAsString());
                        form.setTotal(formData.get("total").getAsFloat());
                        form.setState(formData.get("state").getAsInt());
                        form.setLibrarianId(formData.get("librarian").getAsInt());
                        listSellForm.add(form);
                    });

                    responseData.getAsJsonArray("import_forms").forEach(e -> {
                        JsonObject formData = e.getAsJsonObject();
                        ImportForm form = new ImportForm();
                        form.setId(formData.get("id").getAsInt());
                        form.setCreatedDate(formData.get("created_date").getAsString());
                        form.setSupplier(formData.get("supplier").getAsString());
                        form.setTotal(formData.get("total").getAsFloat());
                        form.setState(formData.get("state").getAsInt());
                        form.setLibrarianId(formData.get("librarian").getAsInt());
                        listImportForm.add(form);
                    });

                    if (formType) {
                        adapter.setData(listSellForm.stream().map(form -> (Object) form).collect(Collectors.toList()));
                    } else {
                        adapter.setData(listImportForm.stream().map(form -> (Object) form).collect(Collectors.toList()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonElement> call, @NonNull Throwable t) {

            }
        });
    }
}
