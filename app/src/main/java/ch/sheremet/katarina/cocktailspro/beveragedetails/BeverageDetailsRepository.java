package ch.sheremet.katarina.cocktailspro.beveragedetails;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.util.Log;

import ch.sheremet.katarina.cocktailspro.model.Beverage;
import ch.sheremet.katarina.cocktailspro.model.BeverageDetails;
import ch.sheremet.katarina.cocktailspro.model.BeverageDetailsResponse;
import ch.sheremet.katarina.cocktailspro.model.database.AppDatabase;
import ch.sheremet.katarina.cocktailspro.model.database.BeverageDao;
import ch.sheremet.katarina.cocktailspro.utils.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeverageDetailsRepository {

    private static final String TAG = BeverageDetailsRepository.class.getSimpleName();

    private ApiManager mApiManager;
    private AppDatabase mDatabase;
    private Callback<BeverageDetailsResponse> mBeverageDetailsCallback;
    private MutableLiveData<BeverageDetails> mBeverageDetails;
    private MutableLiveData<Throwable> mException;

    public BeverageDetailsRepository(ApiManager apiManager, AppDatabase database) {
        this.mApiManager = apiManager;
        this.mDatabase = database;
        this.mBeverageDetails = new MutableLiveData<>();
        this.mException = new MutableLiveData<>();
        this.mException.setValue(null);

        mBeverageDetailsCallback = new Callback<BeverageDetailsResponse>() {
            @Override
            public void onResponse(Call<BeverageDetailsResponse> call, Response<BeverageDetailsResponse> response) {
                if (response.isSuccessful()) {
                    mBeverageDetails.setValue(response.body().getBeverageDetail());
                } else {
                    if (response.errorBody() != null) {
                        Log.e(TAG, response.errorBody().toString());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BeverageDetailsResponse> call, @NonNull Throwable t) {
                Log.e(TAG, "Error getting beverage details", t);
                mException.setValue(t);
            }
        };
    }

    public void fetchBeverageById(final String id) {
        mApiManager.getBeverageByID(mBeverageDetailsCallback, id);
    }

    public MutableLiveData<BeverageDetails> getBeverageDetails() {
        return mBeverageDetails;
    }

    public MutableLiveData<Throwable> listenException() {
        return mException;
    }

    public BeverageDetails getFavouriteBeverageDetails(String id) {
        return mDatabase.beverageDao().getBeverageDetailsWithIngredients(id);
    }

    public void addBeverageToDb(Beverage beverage, BeverageDetails beverageDetails) {
        new InsertAsyncTask(mDatabase.beverageDao()).execute(new Pair<>(beverage, beverageDetails));
    }

    public void removeBeverageFromDb(Beverage beverage, BeverageDetails beverageDetails) {
        new RemoveAsyncTask(mDatabase.beverageDao()).execute(new Pair<>(beverage, beverageDetails));
    }

    public Beverage fetchBeverageByIdFromDb(String id) {
        return mDatabase.beverageDao().getBeverageById(id);
    }

    private static class InsertAsyncTask extends AsyncTask<Pair<Beverage, BeverageDetails>, Void, Void> {

        private BeverageDao mAsyncBeverageDao;

        InsertAsyncTask(BeverageDao asyncBeverageDao) {
            this.mAsyncBeverageDao = asyncBeverageDao;
        }

        @Override
        protected Void doInBackground(Pair<Beverage, BeverageDetails>... pairs) {
            mAsyncBeverageDao.insert(pairs[0].first, pairs[0].second);
            return null;
        }
    }

    private static class RemoveAsyncTask extends AsyncTask<Pair<Beverage, BeverageDetails>, Void, Void> {

        private BeverageDao mAsyncBeverageDao;

        RemoveAsyncTask(BeverageDao asyncBeverageDao) {
            this.mAsyncBeverageDao = asyncBeverageDao;
        }

        @Override
        protected Void doInBackground(Pair<Beverage, BeverageDetails>... pairs) {
            mAsyncBeverageDao.delete(pairs[0].first, pairs[0].second);
            return null;
        }
    }
}
