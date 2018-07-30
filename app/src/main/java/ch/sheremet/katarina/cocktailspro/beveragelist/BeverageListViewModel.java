package ch.sheremet.katarina.cocktailspro.beveragelist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import ch.sheremet.katarina.cocktailspro.model.Beverage;

public class BeverageListViewModel extends ViewModel {

    private static final String TAG = BeverageListViewModel.class.getSimpleName();

    private LiveData<List<Beverage>> mBeverageList;
    private BeverageListRepository mBeverageRepo;
    private LiveData<Throwable> mException;

    public BeverageListViewModel(BeverageListRepository beverageRepo) {
        mBeverageRepo = beverageRepo;
        }

    public LiveData<List<Beverage>> getBeverageList() {
        if (mBeverageList == null) {
            fetchNonAlcoholicBeverages();
            mBeverageList = mBeverageRepo.getBeverageList();
        }
        return mBeverageList;
    }

    public LiveData<Throwable> listenException() {
        if (mException == null) {
            mException = mBeverageRepo.listenException();
        }
        return mException;
    }

    public void fetchNonAlcoholicBeverages() {
        mBeverageRepo.fetchNonAlcoholicBeverages();
    }

    public void fetchAlcoholicBeverages() {
        mBeverageRepo.fetchAlcoholicBeverages();
    }

    public void fetchCocoaBeverages() {
        mBeverageRepo.fetchCocoaBeverages();
    }

    public LiveData<List<Beverage>> fetchFavouriteBeverages() {
        return mBeverageRepo.getFavouriteBeverages();
    }
}
