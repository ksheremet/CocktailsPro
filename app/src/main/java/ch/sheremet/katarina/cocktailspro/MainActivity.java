package ch.sheremet.katarina.cocktailspro;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.sheremet.katarina.cocktailspro.beveragedetails.BeverageDetailsActivity;
import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListFragment;
import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListViewModel;
import ch.sheremet.katarina.cocktailspro.di.BeverageListViewModelModule;
import ch.sheremet.katarina.cocktailspro.di.DaggerMainActivityComponent;
import ch.sheremet.katarina.cocktailspro.di.MainActivityComponent;
import ch.sheremet.katarina.cocktailspro.model.Beverage;

public class MainActivity extends AppCompatActivity implements BeverageListFragment.OnBeverageSelected {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FRAGMENT_STATE = "beverage_list_state";
    private static final String TAB_STATE = "tab_state";

    private BeverageListFragment mBeverageListFragment;

    private List<Beverage> mFavouriteBeverages;
    private boolean mIsFavouriteShown = false;

    @Inject
    BeverageListViewModel mViewModel;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.error_message)
    TextView mErrorMessage;
    @BindView(R.id.beverage_data)
    NestedScrollView mDataNestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        MainActivityComponent component = DaggerMainActivityComponent.builder()
                .beverageListViewModelModule
                        (new BeverageListViewModelModule(this)).build();
        component.injectMainActivity(this);

        showProgressBar();

        if (savedInstanceState != null) {
            mBeverageListFragment = (BeverageListFragment) getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_STATE);
            initTabs(savedInstanceState.getInt(TAB_STATE));
        } else {
            initTabs(0);
            mBeverageListFragment = new BeverageListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.beverage_list_fragment, mBeverageListFragment)
                    .commit();
        }

        // Check favourite beverages of a user. If current tab is favourite - update Adapter
        // and display updated favourites list.
        mViewModel.fetchFavouriteBeverages().observe(this, new Observer<List<Beverage>>() {
            @Override
            public void onChanged(@Nullable List<Beverage> beverages) {
                mFavouriteBeverages = beverages;
                if (mIsFavouriteShown) {
                    mBeverageListFragment.setBeverageList(beverages);
                    Log.d(TAG, "Favourite Beverages: " + beverages);
                }
            }
        });

        mViewModel.getBeverageList().observe(this, new Observer<List<Beverage>>() {
            @Override
            public void onChanged(@Nullable List<Beverage> beverages) {
                if (!mIsFavouriteShown) {
                    mBeverageListFragment.setBeverageList(beverages);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save the fragment's instance
        getSupportFragmentManager().putFragment(outState, FRAGMENT_STATE, mBeverageListFragment);
        outState.putInt(TAB_STATE, mTabLayout.getSelectedTabPosition());
    }

    private void initTabs(int tabPosition) {
        TabLayout.Tab tab = mTabLayout.getTabAt(tabPosition);
        if (tab != null) {
            tab.select();
        }

        if (tabPosition == 3) {
            mIsFavouriteShown = true;
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        showProgressBar();
                        mIsFavouriteShown = false;
                        mViewModel.fetchNonAlcoholicBeverages();
                        break;
                    case 1:
                        showProgressBar();
                        mIsFavouriteShown = false;
                        mViewModel.fetchAlcoholicBeverages();
                        break;
                    case 2:
                        showProgressBar();
                        mIsFavouriteShown = false;
                        mViewModel.fetchCocoaBeverages();
                        break;
                    case 3:
                        showProgressBar();
                        mIsFavouriteShown = true;
                        mBeverageListFragment.setBeverageList(mFavouriteBeverages);
                }
                mDataNestedScrollView.scrollTo(0, 0);
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
        BeverageDetailsActivity.startActivity(this, beverage);
    }

    public void showProgressBar() {
        mDataNestedScrollView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showData() {
        mDataNestedScrollView.setVisibility(View.VISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(String error) {
        mDataNestedScrollView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

}
