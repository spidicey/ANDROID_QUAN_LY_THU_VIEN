package com.library.service.api;

import com.library.model.Librarian;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LibrarianAPIService extends APIService {
    LibrarianAPIService service = BUILDER.create(LibrarianAPIService.class);

    @GET("information/librarians/")
    Call<List<Librarian>> listLibrarian();

    @GET("information/librarians/{id}/")
    Call<Librarian> getLibrarianById(@Path("id") int librarianId);

    @POST("information/librarians/")
    Call<Void> createLibrarian(@Body Librarian librarian);

    @PUT("information/librarians/{id}/")
    Call<Void> updateLibrarian(@Path("id") int librarianId, @Body Librarian librarian);
}
