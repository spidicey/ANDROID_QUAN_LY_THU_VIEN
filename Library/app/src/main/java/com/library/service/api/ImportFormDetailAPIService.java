package com.library.service.api;

import com.library.model.Book;
import com.library.model.ImportFormDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ImportFormDetailAPIService extends APIService {
    ImportFormDetailAPIService service = BUILDER.create(ImportFormDetailAPIService.class);

    @GET("management/import-form-details/filter_by_import_form_id/")
    Call<List<ImportFormDetail>> filterByImportFormId(@Query("id") int formId);

    @GET("management/import-form-details/list_book_of_import_form/")
    Call<List<Book>> listBookOfImportForm(@Query("id") int formId, @Query("is_validate") boolean is_validate);
}
