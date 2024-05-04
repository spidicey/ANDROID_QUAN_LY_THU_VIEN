package com.library.service.api;

import com.library.model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BookAPIService extends APIService {
    BookAPIService service = BUILDER.create(BookAPIService.class);

    @GET("information/books/")
    Call<List<Book>> listBook();

    @POST("information/books/")
    Call<Void> createBook(@Body Book book);

    @PUT("information/books/{id}/")
    Call<Void> updateBook(@Path("id") int bookId, @Body Book book);

    @DELETE("information/books/{id}/")
    Call<Void> deleteBook(@Path("id") int bookId);
}