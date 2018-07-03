package ch.sheremet.katarina.cocktailspro.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeveragesResponse {
    @SerializedName("drinks")
    private List<Beverage> mBeverages;

    public List<Beverage> getBeverages() {
        return mBeverages;
    }
}
