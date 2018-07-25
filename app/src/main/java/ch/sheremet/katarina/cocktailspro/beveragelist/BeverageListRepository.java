package ch.sheremet.katarina.cocktailspro.beveragelist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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
    //TODO: Implement cashing
    // https://androidresearch.wordpress.com/2013/04/07/caching-objects-in-android-internal-storage/
    // https://github.com/googlesamples/android-architecture-components/issues/55
    // https://developer.android.com/reference/android/util/LruCache

    public BeverageListRepository(ApiManager apiManager, AppDatabase appDatabase) {
        this.mApiManager = apiManager;
        this.mDatabase = appDatabase;

        mBeverageList = new MutableLiveData<>();

        mBeveragesCallback = new Callback<BeveragesResponse>() {
            @Override
            public void onResponse(final Call<BeveragesResponse> call,
                                   final Response<BeveragesResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.body().getBeverages().toString());
                    mBeverageList.setValue(response.body().getBeverages());

                } else {
                    Log.e(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<BeveragesResponse> call, Throwable t) {
                //TODO: Show error message to user
                Log.e(TAG, "Error getting beverages", t);
            }
        };
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
