package guru.qa.niffler.api;

import guru.qa.niffler.model.CategoryJson;
import retrofit2.Call;
import retrofit2.http.*;

public interface CategoryApi {

    @POST("internal/categories/add")
    Call<CategoryJson> addCategories(@Body CategoryJson category);

    @PATCH("internal/categories/update")
    Call<CategoryJson> updateCategories(@Body CategoryJson category);

    @GET("internal/categories/all")
    Call<CategoryJson> getAllCategories(@Query("excludeArchived") boolean value);



}

