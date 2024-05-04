package com.library.service;

import android.annotation.SuppressLint;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.library.intent.adapter.reader.ReaderAdapter;
import com.library.model.Reader;
import com.library.service.api.ReaderAPIService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReaderService {
    public static void listReader(ReaderAdapter adapter, List<Reader> data, List<Reader> dataStorage) {
        ReaderAPIService.service.listReader().enqueue(new Callback<List<Reader>>() {
            @Override
            public void onResponse(@NonNull Call<List<Reader>> call, @NonNull Response<List<Reader>> response) {
                if (response.body() != null) {
                    data.clear();
                    dataStorage.clear();

                    data.addAll(response.body());
                    dataStorage.addAll(response.body());

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Reader>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void listReader(List<Reader> list, Map<String, Integer> lastNameToIdMap, List<String> lastNameList) {
        ReaderAPIService.service.listReader().enqueue(new Callback<List<Reader>>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onResponse(@NonNull Call<List<Reader>> call, @NonNull Response<List<Reader>> response) {
                if (response.body() != null) {
                    list.addAll(response.body());
                    for (Reader reader : list) {
                        String displayMember = String.format("%s ~ %d", reader.getFullName(), reader.getId());

                        lastNameToIdMap.put(displayMember, reader.getId());
                        lastNameList.add(displayMember);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Reader>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void listReader(ArrayAdapter<String> adapter) {
        ReaderAPIService.service.listReader().enqueue(new Callback<List<Reader>>() {
            @Override
            public void onResponse(@NonNull Call<List<Reader>> call, @NonNull Response<List<Reader>> response) {
                if (response.body() != null) {
                    adapter.addAll(response.body().stream()
                            .map(reader -> String.format("%s #%s", reader.getFullName(), reader.getId()))
                            .collect(Collectors.toList()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Reader>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void listReader(ArrayAdapter<String> adapter, AutoCompleteTextView actReader, int readerId) {
        ReaderAPIService.service.listReader().enqueue(new Callback<List<Reader>>() {
            @Override
            public void onResponse(@NonNull Call<List<Reader>> call, @NonNull Response<List<Reader>> response) {
                if (response.body() != null) {
                    adapter.addAll(response.body().stream()
                            .map(reader -> String.format("%s #%s", reader.getFullName(), reader.getId()))
                            .collect(Collectors.toList()));
                    adapter.notifyDataSetChanged();

                    response.body().stream().filter(e -> e.getId() == readerId).findFirst()
                            .ifPresent(reader -> actReader.setText(String.format("%s #%s", reader.getFullName(), reader.getId())));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Reader>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void setReaderEditText(EditText v, int readerId) {
        ReaderAPIService.service.getReaderById(readerId).enqueue(new Callback<Reader>() {
            @Override
            public void onResponse(@NonNull Call<Reader> call, @NonNull Response<Reader> response) {
                if (response.body() != null) {
                    v.setText(response.body().getFullName());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Reader> call, @NonNull Throwable t) {

            }
        });
    }

    public static void setReaderTextView(TextView v, int readerId) {
        ReaderAPIService.service.getReaderById(readerId).enqueue(new Callback<Reader>() {
            @Override
            public void onResponse(@NonNull Call<Reader> call, @NonNull Response<Reader> response) {
                if (response.body() != null) {
                    v.setText(response.body().getFullName());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Reader> call, @NonNull Throwable t) {

            }
        });
    }

    public static void updateReader(Reader reader) {
        ReaderAPIService.service.updateReader(reader.getId(), reader).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }
}
