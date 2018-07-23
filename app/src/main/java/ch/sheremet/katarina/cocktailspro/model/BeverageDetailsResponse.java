package ch.sheremet.katarina.cocktailspro.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BeverageDetailsResponse {
    @SerializedName("drinks")
    private List<BeverageDetails> mBeverageDetail;

    public List<BeverageDetails> getBeverageDetail() {
        return mBeverageDetail;
    }
}
