package ch.sheremet.katarina.cocktailspro.utils;

import ch.sheremet.katarina.cocktailspro.model.BeveragesResponse;
import retrofit2.Call;
import retrofit2.Callback;

public class ApiManager {
    private final IBeveragesApi service;

    private static final String ALCOHOLIC_BEVERAGES_PATH = "Alcoholic";
    private static final String NON_ALCOHOLIC_BEVERAGES_PATH = "Non_Alcoholic";
    private static final String COCOA_BEVERAGES_PATH = "Cocoa";

    public ApiManager(IBeveragesApi moviesApi) {
        this.service = moviesApi;
    }

    public void getAlcoholicBeverages(final Callback<BeveragesResponse> callback) {
        Call<BeveragesResponse> alcoholicBeveragesCall = service.getBeverages(ALCOHOLIC_BEVERAGES_PATH);
        alcoholicBeveragesCall.enqueue(callback);
    }

    public void getNonAlcoholicBeverages(final Callback<BeveragesResponse> callback) {
        Call<BeveragesResponse> alcoholicBeveragesCall = service.getBeverages(NON_ALCOHOLIC_BEVERAGES_PATH);
        alcoholicBeveragesCall.enqueue(callback);
    }

    public void getCocoaBeverages(final Callback<BeveragesResponse> callback) {
        Call<BeveragesResponse> alcoholicBeveragesCall = service.getByCategory(COCOA_BEVERAGES_PATH);
        alcoholicBeveragesCall.enqueue(callback);
    }
}
