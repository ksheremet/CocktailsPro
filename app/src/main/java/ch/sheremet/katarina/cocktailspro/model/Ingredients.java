package ch.sheremet.katarina.cocktailspro.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import java.util.Locale;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "ingredient_table", foreignKeys = @ForeignKey(entity = BeverageDetails.class,
        parentColumns = "id", childColumns = "beverage_id", onDelete = CASCADE),
        indices = {@Index("beverage_id")})
public class Ingredients implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;
    @ColumnInfo(name = "ingredient")
    private String mIngredient;
    @ColumnInfo(name = "measure")
    private String mMeasure;
    @ColumnInfo(name = "beverage_id")
    private String mBeverageId;


    @Ignore
    public Ingredients(String ingredient, String measure) {
        this.mIngredient = ingredient;
        this.mMeasure = measure;
    }

    public Ingredients(int mId, String mIngredient, String mMeasure, String mBeverageId) {
        this.mId = mId;
        this.mIngredient = mIngredient;
        this.mMeasure = mMeasure;
        this.mBeverageId = mBeverageId;
    }

    @Ignore
    public Ingredients(String mIngredient, String mMeasure, String mBeverageId) {
        this.mIngredient = mIngredient;
        this.mMeasure = mMeasure;
        this.mBeverageId = mBeverageId;
    }

    @Ignore
    protected Ingredients(Parcel in) {
        mId = in.readInt();
        mIngredient = in.readString();
        mMeasure = in.readString();
        mBeverageId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mIngredient);
        dest.writeString(mMeasure);
        dest.writeString(mBeverageId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ingredients> CREATOR = new Creator<Ingredients>() {
        @Override
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        @Override
        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };

    public String getIngredient() {
        return mIngredient;
    }

    public void setIngredient(String ingredient) {
        this.mIngredient = ingredient;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setMeasure(String measure) {
        this.mMeasure = measure;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getBeverageId() {
        return mBeverageId;
    }

    public void setBeverageId(String mBeverageId) {
        this.mBeverageId = mBeverageId;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Ingredients{mId=%d, mIngredient='%s', mMeasure='%s', mBeverageId='%s'}",
                mId, mIngredient, mMeasure, mBeverageId);
    }
}
