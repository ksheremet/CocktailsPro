package ch.sheremet.katarina.cocktailspro.utils;

import ch.sheremet.katarina.cocktailspro.model.BeveragesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IBeveragesApi {
    @GET("filter.php?")
    Call<BeveragesResponse> getBeverages(@Query("a") String searchAttribute);

    @GET("filter.php?")
    Call<BeveragesResponse> getByCategory(@Query("c") String searchAttribute);
}
