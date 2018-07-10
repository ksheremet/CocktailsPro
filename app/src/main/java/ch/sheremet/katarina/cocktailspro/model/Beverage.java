package ch.sheremet.katarina.cocktailspro.model;

import com.google.gson.annotations.SerializedName;

public class Beverage {
    @SerializedName("idDrink")
    private String mId;
    @SerializedName("strDrink")
    private String mName;
    @SerializedName("strDrinkThumb")
    private String mThumbnailUrl;

    public String getName() {
        return mName;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    //TODO(ksheremet): Use StringBuilder
    @Override
    public String toString() {
        return "Beverage{" +
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mThumbnailUrl='" + mThumbnailUrl + '\'' +
                '}';
    }
}
