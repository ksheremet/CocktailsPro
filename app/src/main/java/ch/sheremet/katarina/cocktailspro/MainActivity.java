package ch.sheremet.katarina.cocktailspro;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListFragment;
import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListViewModel;
import ch.sheremet.katarina.cocktailspro.di.DaggerMainActivityComponent;
import ch.sheremet.katarina.cocktailspro.di.MainActivityComponent;
import ch.sheremet.katarina.cocktailspro.di.BeverageListViewModelModule;
import ch.sheremet.katarina.cocktailspro.model.Beverage;

public class MainActivity extends AppCompatActivity implements BeverageListFragment.OnBeverageSelected {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String FRAGMENT_STATE = "beverage_list_state";
    private static final String TAB_STATE = "tab_state";

    private BeverageListFragment mBeverageListFragment;

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
        Log.d(TAG, beverage.toString());
    }

}
