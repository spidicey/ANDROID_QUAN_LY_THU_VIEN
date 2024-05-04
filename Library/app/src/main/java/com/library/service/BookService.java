package com.library.service;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.library.intent.adapter.book.BookAdapter;
import com.library.main.fragment.reader.BookSearchAdapter;
import com.library.model.Book;
import com.library.service.api.BookAPIService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookService {
    public static void listBookInformation(ArrayAdapter<String> authorAdapter, ArrayAdapter<String> categoryAdapter) {
        BookAPIService.service.listBook().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Book>> call, @NonNull Response<List<Book>> response) {
                if (response.body() != null) {
                    Set<String> setAuthor = new HashSet<>();
                    Set<String> setCategory = new HashSet<>();

                    response.body().forEach(book -> {
                        setAuthor.add(book.getAuthor());
                        setCategory.add(book.getCategory());
                    });

                    authorAdapter.addAll(setAuthor);
                    categoryAdapter.addAll(setCategory);

                    authorAdapter.notifyDataSetChanged();
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void listBook(BookAdapter adapter, List<Book> dataStorage) {
        BookAPIService.service.listBook().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Book>> call, @NonNull Response<List<Book>> response) {
                if (response.body() != null) {
                    adapter.setData(response.body());
                    dataStorage.clear();
                    dataStorage.addAll(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void listBook(com.library.intent.adapter.borrow_form.BookListCreateAdapter adapter) {
        BookAPIService.service.listBook().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Book>> call, @NonNull Response<List<Book>> response) {
                if (response.body() != null) {
                    adapter.setData(response.body().stream()
                            .filter(book -> book.getQuantity() > 0).collect(Collectors.toList()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void listBook(com.library.intent.adapter.sell_form.BookListCreateAdapter adapter) {
        BookAPIService.service.listBook().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Book>> call, @NonNull Response<List<Book>> response) {
                if (response.body() != null) {
                    adapter.setData(response.body().stream()
                            .filter(book -> book.getQuantity() > 0).collect(Collectors.toList()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void listBook(com.library.intent.adapter.import_form.BookListCreateAdapter adapter) {
        BookAPIService.service.listBook().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Book>> call, @NonNull Response<List<Book>> response) {
                adapter.setData(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void listBook(BookSearchAdapter adapter, List<Book> dataStorage) {
        BookAPIService.service.listBook().enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Book>> call, @NonNull Response<List<Book>> response) {
                if (response.body() != null) {
                    adapter.setData(response.body());
                    dataStorage.clear();
                    dataStorage.addAll(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {

            }
        });
    }

    public static void createBook(Book book) {
        BookAPIService.service.createBook(book).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public static void updateBook(Book book) {
        BookAPIService.service.updateBook(book.getId(), book).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }

    public static void deleteBook(int bookId) {
        BookAPIService.service.deleteBook(bookId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {

            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        });
    }
}
