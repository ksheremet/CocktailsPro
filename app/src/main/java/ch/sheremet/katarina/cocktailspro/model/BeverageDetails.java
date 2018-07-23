package ch.sheremet.katarina.cocktailspro.model;


import java.util.List;

public class BeverageDetails {
    private String mId;
    private String mName;
    private String mInstructions;
    private String mGlassType;
    private String mThumbnailUrl;
    private List<Ingredients> mIngredients;

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

    public List<Ingredients> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredients> mIngredients) {
        this.mIngredients = mIngredients;
    }

    @Override
    public String toString() {
        return "BeverageDetails{" +
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mInstructions='" + mInstructions + '\'' +
                ", mGlassType='" + mGlassType + '\'' +
                ", mThumbnailUrl='" + mThumbnailUrl + '\'' +
                ", mIngredients=" + mIngredients +
                '}';
    }
}
