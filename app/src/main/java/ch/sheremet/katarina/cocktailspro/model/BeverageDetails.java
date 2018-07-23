package ch.sheremet.katarina.cocktailspro.model;


import com.google.gson.annotations.SerializedName;

public class BeverageDetails {
    @SerializedName("idDrink")
    private String mId;
    @SerializedName("strDrink")
    private String mName;
    @SerializedName("strInstructions")
    private String mInstructions;
    @SerializedName("strGlass")
    private String mGlassType;
    @SerializedName("strDrinkThumb")
    private String mThumbnailUrl;

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getInstructions() {
        return mInstructions;
    }

    public void setInstructions(String mInstructions) {
        this.mInstructions = mInstructions;
    }

    public String getGlassType() {
        return mGlassType;
    }

    public void setGlassType(String mGlassType) {
        this.mGlassType = mGlassType;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    public void setThumbnailUrl(String mThumbnailUrl) {
        this.mThumbnailUrl = mThumbnailUrl;
    }

    //TODO: StringBuilder
    @Override
    public String toString() {
        return "BeverageDetails{" +
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mInstructions='" + mInstructions + '\'' +
                ", mGlassType='" + mGlassType + '\'' +
                ", mThumbnailUrl='" + mThumbnailUrl + '\'' +
                '}';
    }
}
