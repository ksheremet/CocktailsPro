package ch.sheremet.katarina.cocktailspro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import ch.sheremet.katarina.cocktailspro.model.BeveragesResponse;
import ch.sheremet.katarina.cocktailspro.utils.ApiManager;
import ch.sheremet.katarina.cocktailspro.utils.IBeveragesApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Callback<BeveragesResponse> mBeveragesCallback;
    private ApiManager mApiManager;

    public static final String BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // TODO(ksheremet): Init in dagger
        mApiManager = new ApiManager(new Retrofit.Builder()
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(IBeveragesApi.class));

        mBeveragesCallback = new Callback<BeveragesResponse>() {
            @Override
            public void onResponse(final Call<BeveragesResponse> call,
                                   final Response<BeveragesResponse> response) {
                if (response.isSuccessful()) {
                    Log.v(TAG, response.body().getBeverages().toString());
                } else {
                    Log.e(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<BeveragesResponse> call, Throwable t) {
                Log.e(TAG, "Error getting movies", t);
            }
        };


        //mApiManager.getAlcoholicBeverages(mBeveragesCallback);
        //mApiManager.getNonAlcoholicBeverages(mBeveragesCallback);
        mApiManager.getCocoaBeverages(mBeveragesCallback);
    }
}
