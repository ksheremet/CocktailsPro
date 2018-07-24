package ch.sheremet.katarina.cocktailspro.di;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

import javax.inject.Singleton;

import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListRepository;
import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListViewModel;
import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListViewModelFactory;
import ch.sheremet.katarina.cocktailspro.model.database.AppDatabase;
import ch.sheremet.katarina.cocktailspro.utils.ApiManager;
import dagger.Module;
import dagger.Provides;

@Module(includes = ApiModule.class)
public class BeverageListViewModelModule {

    private FragmentActivity mLiveCircleOwner;

    public BeverageListViewModelModule(FragmentActivity liveCircleOwner) {
        this.mLiveCircleOwner = liveCircleOwner;
    }

    @Singleton
    @Provides
    BeverageListViewModel provideViewModel(BeverageListViewModelFactory factory) {
        return ViewModelProviders.of(mLiveCircleOwner, factory).get(BeverageListViewModel.class);
    }

    @Singleton
    @Provides
    BeverageListViewModelFactory provideViewModelFactory(BeverageListRepository repository) {
        return new BeverageListViewModelFactory(repository);
    }

    @Singleton
    @Provides
    BeverageListRepository provideBeverageListRepository(ApiManager apiManager, AppDatabase appDatabase) {
        return new BeverageListRepository(apiManager, appDatabase);
    }

    @Singleton
    @Provides
    AppDatabase provideDatabase() {
        return AppDatabase.getInstance(mLiveCircleOwner.getApplicationContext());
    }
}
