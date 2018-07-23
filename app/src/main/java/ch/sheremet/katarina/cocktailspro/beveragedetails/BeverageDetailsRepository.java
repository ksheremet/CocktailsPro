package ch.sheremet.katarina.cocktailspro.beveragedetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import ch.sheremet.katarina.cocktailspro.model.BeverageDetails;
import ch.sheremet.katarina.cocktailspro.model.BeverageDetailsResponse;
import ch.sheremet.katarina.cocktailspro.utils.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeverageDetailsRepository {

    private static final String TAG = BeverageDetailsRepository.class.getSimpleName();

    private ApiManager mApiManager;
    private Callback<BeverageDetailsResponse> mBeverageDetailsCallback;
    private MutableLiveData<BeverageDetails> mBeverageDetails;

    public BeverageDetailsRepository(ApiManager apiManager) {
        this.mApiManager = apiManager;
        mBeverageDetails = new MutableLiveData<>();

        mBeverageDetailsCallback = new Callback<BeverageDetailsResponse>() {
            @Override
            public void onResponse(Call<BeverageDetailsResponse> call, Response<BeverageDetailsResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, response.body().getBeverageDetail().toString());
                    mBeverageDetails.setValue(response.body().getBeverageDetail());
                }
            }

            @Override
            public void onFailure(Call<BeverageDetailsResponse> call, Throwable t) {
                //TODO: Show error message to user
                Log.e(TAG, "Error getting beverage details", t);
                }
        };
    }

    public void fetchBeverageById(final String id) {
        mApiManager.getBeverageByID(mBeverageDetailsCallback, id);
    }

    public LiveData<BeverageDetails> getBeverageDetails() {
        return mBeverageDetails;
    }
}
