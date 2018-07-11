package ch.sheremet.katarina.cocktailspro;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListFragment;
import ch.sheremet.katarina.cocktailspro.model.Beverage;
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

public class MainActivity extends AppCompatActivity implements BeverageListFragment.OnBeverageSelected {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Callback<BeveragesResponse> mBeveragesCallback;
    private ApiManager mApiManager;
    private BeverageListFragment mBeverageListFragment;

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
                    mBeverageListFragment.setBeverageList(response.body().getBeverages());

                } else {
                    Log.e(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<BeveragesResponse> call, Throwable t) {
                //TODO: Show error message to user
                Log.e(TAG, "Error getting movies", t);
            }
        };

        mBeverageListFragment = new BeverageListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.beverage_list_fragment, mBeverageListFragment)
                .commit();


        mApiManager.getNonAlcoholicBeverages(mBeveragesCallback);
        initTabs();
    }

    private void initTabs() {
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mApiManager.getNonAlcoholicBeverages(mBeveragesCallback);
                        break;
                    case 1:
                        mApiManager.getAlcoholicBeverages(mBeveragesCallback);
                        break;
                    case 2:
                        mApiManager.getCocoaBeverages(mBeveragesCallback);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBeverageClicked(Beverage beverage) {
        Log.d(TAG, beverage.toString());
    }

}
