package ch.sheremet.katarina.cocktailspro.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.List;

@Entity(tableName = "beverage_detail_table")
public class BeverageDetails implements Parcelable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String mId;
    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "instructions")
    private String mInstructions;
    @ColumnInfo(name = "glass_type")
    private String mGlassType;
    @ColumnInfo(name = "thumbnail")
    private String mThumbnailUrl;
    @ColumnInfo(name = "category")
    private String mCategory;
    @ColumnInfo(name = "iba")
    private String mIBA;
    @Ignore
    private List<Ingredients> mIngredients;

    @Ignore
    public BeverageDetails() {
    }

    public BeverageDetails(@NonNull String mId, String mName, String mInstructions, String mGlassType, String mThumbnailUrl, String mCategory, String mIBA) {
        this.mId = mId;
        this.mName = mName;
        this.mInstructions = mInstructions;
        this.mGlassType = mGlassType;
        this.mThumbnailUrl = mThumbnailUrl;
        this.mCategory = mCategory;
        this.mIBA = mIBA;
    }

    protected BeverageDetails(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mInstructions = in.readString();
        mGlassType = in.readString();
        mThumbnailUrl = in.readString();
        mCategory = in.readString();
        mIBA = in.readString();
        mIngredients = in.createTypedArrayList(Ingredients.CREATOR);
    }

    public static final Creator<BeverageDetails> CREATOR = new Creator<BeverageDetails>() {
        @Override
        public BeverageDetails createFromParcel(Parcel in) {
            return new BeverageDetails(in);
        }

        @Override
        public BeverageDetails[] newArray(int size) {
            return new BeverageDetails[size];
        }
    };

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

    public String getIBA() {
        return mIBA;
    }

    public void setIBA(String mIBA) {
        this.mIBA = mIBA;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("BeverageDetails{mId='%s', mName='%s', mInstructions='%s', mGlassType='%s', " +
                        "mThumbnailUrl='%s', mCategory='%s', mIBA='%s', mIngredients=%s}",
                mId, mName, mInstructions, mGlassType, mThumbnailUrl, mCategory, mIBA, mIngredients);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mInstructions);
        dest.writeString(mGlassType);
        dest.writeString(mThumbnailUrl);
        dest.writeString(mCategory);
        dest.writeString(mIBA);
        dest.writeTypedList(mIngredients);
    }
}
