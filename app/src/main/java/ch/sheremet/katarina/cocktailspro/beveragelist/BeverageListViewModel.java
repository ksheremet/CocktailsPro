package ch.sheremet.katarina.cocktailspro.beveragelist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import ch.sheremet.katarina.cocktailspro.model.Beverage;

public class BeverageListViewModel extends ViewModel {

    private static final String TAG = BeverageListViewModel.class.getSimpleName();

    private LiveData<List<Beverage>> mBeverageList;
    private BeverageListRepository mBeverageRepo;

    public BeverageListViewModel(BeverageListRepository beverageRepo) {
        mBeverageRepo = beverageRepo;
        fetchNonAlcoholicBeverages();
        mBeverageList = mBeverageRepo.getBeverageList();
    }

    public LiveData<List<Beverage>> getBeverageList() {
        return mBeverageList;
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

    public void fetchFavouriteBeverages() {
        mBeverageRepo.fetchFavouriteBeverages();
    }
}
