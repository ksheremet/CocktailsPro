package ch.sheremet.katarina.cocktailspro.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "beverage_table")
public class Beverage implements Parcelable {
    @PrimaryKey()
    @ColumnInfo(name = "id")
    @NonNull
    @SerializedName("idDrink")
    private String mId;
    @ColumnInfo(name = "name")
    @SerializedName("strDrink")
    private String mName;
    @ColumnInfo(name = "thumbnail")
    @SerializedName("strDrinkThumb")
    private String mThumbnailUrl;

    @Ignore
    protected Beverage(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mThumbnailUrl = in.readString();
    }

    public Beverage(String id, String name, String thumbnailUrl) {
        this.mId = id;
        this.mName = name;
        this.mThumbnailUrl = thumbnailUrl;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeString(mThumbnailUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Beverage> CREATOR = new Creator<Beverage>() {
        @Override
        public Beverage createFromParcel(Parcel in) {
            return new Beverage(in);
        }

        @Override
        public Beverage[] newArray(int size) {
            return new Beverage[size];
        }
    };

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    @Override
    public String toString() {
        return String.format("Beverage{mId='%s', mName='%s', mThumbnailUrl='%s'", mId, mName, mThumbnailUrl);
    }
}
