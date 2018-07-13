package ch.sheremet.katarina.cocktailspro;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListFragment;
import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListRepository;
import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListViewModel;
import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListViewModelFactory;
import ch.sheremet.katarina.cocktailspro.model.Beverage;
import ch.sheremet.katarina.cocktailspro.utils.ApiManager;
import ch.sheremet.katarina.cocktailspro.utils.IBeveragesApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements BeverageListFragment.OnBeverageSelected {

    private static final String TAG = MainActivity.class.getSimpleName();

    private BeverageListFragment mBeverageListFragment;
    private BeverageListViewModel mViewModel;

    public static final String BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO(ksheremet): Init in dagger
        ApiManager mApiManager = new ApiManager(new Retrofit.Builder()
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(IBeveragesApi.class));


        mBeverageListFragment = new BeverageListFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.beverage_list_fragment, mBeverageListFragment)
                .commit();


        //TODO: Maybe move logic to fragment
        BeverageListViewModelFactory factory = new BeverageListViewModelFactory(new BeverageListRepository(mApiManager));

        mViewModel = ViewModelProviders.of(this, factory).get(BeverageListViewModel.class);
        //mViewModel.fetchNonAlcoholicBeverages();
        initTabs();
        mViewModel.getBeverageList().observe(this, new Observer<List<Beverage>>() {
            @Override
            public void onChanged(@Nullable List<Beverage> beverages) {
                mBeverageListFragment.setBeverageList(beverages);
            }
        });
    }

    private void initTabs() {
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mViewModel.fetchNonAlcoholicBeverages();
                        break;
                    case 1:
                        mViewModel.fetchAlcoholicBeverages();
                        break;
                    case 2:
                        mViewModel.fetchCocoaBeverages();
                        break;
                    case 3:
                        mViewModel.fetchFavouriteBeverages();

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
