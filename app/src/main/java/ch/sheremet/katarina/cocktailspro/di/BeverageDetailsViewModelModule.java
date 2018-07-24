package ch.sheremet.katarina.cocktailspro.di;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

import javax.inject.Singleton;

import ch.sheremet.katarina.cocktailspro.beveragedetails.BeverageDetailsRepository;
import ch.sheremet.katarina.cocktailspro.beveragedetails.BeverageDetailsViewModel;
import ch.sheremet.katarina.cocktailspro.beveragedetails.BeverageDetailsViewModelFactory;
import ch.sheremet.katarina.cocktailspro.model.database.AppDatabase;
import ch.sheremet.katarina.cocktailspro.utils.ApiManager;
import dagger.Module;
import dagger.Provides;

@Module(includes = ApiModule.class)
public class BeverageDetailsViewModelModule {

    private FragmentActivity mLiveCircleOwner;

    public BeverageDetailsViewModelModule(FragmentActivity liveCircleOwner) {
        this.mLiveCircleOwner = liveCircleOwner;
    }

    @Singleton
    @Provides
    BeverageDetailsViewModel provideViewModel(BeverageDetailsViewModelFactory factory) {
        return ViewModelProviders.of(mLiveCircleOwner, factory).get(BeverageDetailsViewModel.class);
    }

    @Singleton
    @Provides
    BeverageDetailsViewModelFactory provideViewModelFactory(BeverageDetailsRepository repository) {
        return new BeverageDetailsViewModelFactory(repository);
    }

    @Singleton
    @Provides
    BeverageDetailsRepository provideBeverageListRepository(ApiManager apiManager, AppDatabase appDatabase) {
        return new BeverageDetailsRepository(apiManager, appDatabase);
    }

    @Singleton
    @Provides
    AppDatabase provideDatabase() {
        return AppDatabase.getInstance(mLiveCircleOwner.getApplicationContext());
    }
}
