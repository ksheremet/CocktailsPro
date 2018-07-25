package ch.sheremet.katarina.cocktailspro;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        MainActivityComponent component = DaggerMainActivityComponent.builder()
                .beverageListViewModelModule
                        (new BeverageListViewModelModule(this)).build();
        component.injectMainActivity(this);

        // Check favourite beverages of a user. If current tab is favourite - update Adapter
        // and display updated favourites list.
        mViewModel.fetchFavouriteBeverages().observe(this, new Observer<List<Beverage>>() {
            @Override
            public void onChanged(@Nullable List<Beverage> beverages) {
                mFavouriteBeverages = beverages;
                if (mIsFavouriteShown) {
                    mBeverageListFragment.setBeverageList(beverages);
                }
            }
        });

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
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mIsFavouriteShown = false;
                        mViewModel.fetchNonAlcoholicBeverages();
                        break;
                    case 1:
                        mIsFavouriteShown = false;
                        mViewModel.fetchAlcoholicBeverages();
                        break;
                    case 2:
                        mIsFavouriteShown = false;
                        mViewModel.fetchCocoaBeverages();
                        break;
                    case 3:
                        mIsFavouriteShown = true;
                        mBeverageListFragment.setBeverageList(mFavouriteBeverages);
                }
                mBeverageListFragment.moveToListBeginning();
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
        Log.d(TAG, beverage.toString());
    }

}
