package guru.qa.niffler.api;

import guru.qa.niffler.model.CategoryJson;
import retrofit2.Call;
import retrofit2.http.*;

public interface CategoryApi {

    @POST("/api/categories/add")
    Call<CategoryJson> addCategories(@Body CategoryJson categories);

    @PATCH("/api/categories/update")
    Call<CategoryJson> updateCategories(@Body CategoryJson categories);

    @GET("/api/categories/all")
    Call<CategoryJson> getAllCategories(@Query("excludeArchived") boolean value);



}

