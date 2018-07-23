package ch.sheremet.katarina.cocktailspro.model;

import com.google.gson.annotations.SerializedName;

public class BeverageDetailsResponse {
    @SerializedName("drinks")
    private BeverageDetails mBeverageDetail;

    public BeverageDetails getBeverageDetail() {
        return mBeverageDetail;
    }
}
