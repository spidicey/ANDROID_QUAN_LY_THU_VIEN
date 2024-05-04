package com.library.service;

import android.widget.TextView;

import androidx.annotation.NonNull;

import com.library.intent.adapter.librarian.LibrarianAdapter;
import com.library.model.Librarian;
import com.library.service.api.LibrarianAPIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibrarianService {
    public static void listLibrarian(LibrarianAdapter adapter, List<Librarian> data, List<Librarian> dataStorage) {
        LibrarianAPIService.service.listLibrarian().enqueue(new Callback<List<Librarian>>() {
            @Override
            public void onResponse(@NonNull Call<List<Librarian>> call, @NonNull Response<List<Librarian>> response) {
                if (response.body() != null) {
                    data.clear();
                    dataStorage.clear();

                    data.addAll(response.body());
                    dataStorage.addAll(response.body());

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Librarian>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void setLibrarianTextView(TextView v, int librarianId) {
        LibrarianAPIService.service.getLibrarianById(librarianId).enqueue(new Callback<Librarian>() {
            @Override
            public void onResponse(@NonNull Call<Librarian> call, @NonNull Response<Librarian> response) {
                if (response.body() != null) {
                    v.setText(response.body().getFullName());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Librarian> call, @NonNull Throwable t) {

            }
        });
    }

    public static void createLibrarian(Librarian librarian) {
        LibrarianAPIService.service.createLibrarian(librarian).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public static void updateLibrarian(Librarian librarian) {
        LibrarianAPIService.service.updateLibrarian(librarian.getId(), librarian).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }
}
