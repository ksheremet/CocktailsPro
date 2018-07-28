package ch.sheremet.katarina.cocktailspro.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "ingredient_table", foreignKeys = @ForeignKey(entity = BeverageDetails.class,
        parentColumns = "id", childColumns = "beverage_id", onDelete = CASCADE),
        indices = {@Index("beverage_id")})
public class Ingredients {
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

    //TODO: StringBuilder
    @Override
    public String toString() {
        return "Ingredients{" +
                "mId=" + mId +
                ", mIngredient='" + mIngredient + '\'' +
                ", mMeasure='" + mMeasure + '\'' +
                ", mBeverageId='" + mBeverageId + '\'' +
                '}';
    }
}
