package guru.qa.niffler.api;

import guru.qa.niffler.api.enums.Currency;
import guru.qa.niffler.api.enums.Period;
import guru.qa.niffler.model.SpendJson;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface SpendApi {

    @POST("internal/spends/add")
    Call<SpendJson> addSpend(@Body SpendJson spend);

    @PATCH("internal/spends/edit")
    Call<SpendJson> editSpend(@Body SpendJson spend);

    @GET("internal/spends/{id}")
    Call<SpendJson> getSpend(@Path("id") String id);

    @GET("internal/spends/all")
    Call<SpendJson> getAllSpend(@Query("filterPeriod") Period period, @Query("filterCurrency") Currency currency);

    @DELETE("internal/spends/remove")
    Call<Void> deleteSpend(@Query("ids") List<String> ids);


}
