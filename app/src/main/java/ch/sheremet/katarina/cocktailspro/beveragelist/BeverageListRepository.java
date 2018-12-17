package ch.sheremet.katarina.cocktailspro.beveragelist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;
import android.util.Log;

import java.util.List;

import ch.sheremet.katarina.cocktailspro.model.Beverage;
import ch.sheremet.katarina.cocktailspro.model.BeveragesResponse;
import ch.sheremet.katarina.cocktailspro.model.database.AppDatabase;
import ch.sheremet.katarina.cocktailspro.utils.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeverageListRepository {

    private static final String TAG = BeverageListRepository.class.getSimpleName();

    private ApiManager mApiManager;
    private AppDatabase mDatabase;
    private Callback<BeveragesResponse> mBeveragesCallback;
    private MutableLiveData<List<Beverage>> mBeverageList;
    private MutableLiveData<Throwable> mException;

    public BeverageListRepository(ApiManager apiManager, AppDatabase appDatabase) {
        this.mApiManager = apiManager;
        this.mDatabase = appDatabase;
        this.mException = new MutableLiveData<>();
        this.mException.setValue(null);

        mBeverageList = new MutableLiveData<>();

        mBeveragesCallback = new Callback<BeveragesResponse>() {
            @Override
            public void onResponse(@NonNull final Call<BeveragesResponse> call,
                                   @NonNull final Response<BeveragesResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.body().getBeverages().toString());
                    mBeverageList.setValue(response.body().getBeverages());
                } else {
                    Log.e(TAG, response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<BeveragesResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Error getting beverages", t);
                mException.setValue(t);
            }
        };
    }

    public MutableLiveData<Throwable> listenException() {
        return mException;
    }

    public void fetchNonAlcoholicBeverages() {
        mApiManager.getNonAlcoholicBeverages(mBeveragesCallback);
    }

    public void fetchAlcoholicBeverages() {
        mApiManager.getAlcoholicBeverages(mBeveragesCallback);
    }

    public void fetchCocoaBeverages() {
        mApiManager.getCocoaBeverages(mBeveragesCallback);
    }

    public LiveData<List<Beverage>> getBeverageList() {
        return mBeverageList;
    }

    public LiveData<List<Beverage>> getFavouriteBeverages() {
        return mDatabase.beverageDao().getAllBeverages();
    }
}
