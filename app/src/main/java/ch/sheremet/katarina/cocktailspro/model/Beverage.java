package ch.sheremet.katarina.cocktailspro.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Beverage implements Parcelable {
    @SerializedName("idDrink")
    private String mId;
    @SerializedName("strDrink")
    private String mName;
    @SerializedName("strDrinkThumb")
    private String mThumbnailUrl;

    protected Beverage(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mThumbnailUrl = in.readString();
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

    public String getName() {
        return mName;
    }

    public String getThumbnailUrl() {
        return mThumbnailUrl;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Beverage{");
        stringBuilder.append("mId='").append(mId).append('\'')
                .append(", mName='").append(mName).append('\'')
                .append(", mThumbnailUrl='").append(mThumbnailUrl).append('\'');
        return stringBuilder.toString();
    }
}
