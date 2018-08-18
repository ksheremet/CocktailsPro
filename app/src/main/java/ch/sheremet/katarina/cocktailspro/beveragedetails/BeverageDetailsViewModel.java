package ch.sheremet.katarina.cocktailspro.beveragedetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import ch.sheremet.katarina.cocktailspro.model.Beverage;
import ch.sheremet.katarina.cocktailspro.model.BeverageDetails;

public class BeverageDetailsViewModel extends ViewModel {

    private static final String TAG = BeverageDetailsActivity.class.getSimpleName();

    private LiveData<BeverageDetails> mBeverageDetails;
    private LiveData<Throwable> mException;
    private BeverageDetailsRepository mBeverageDetailsRepo;

    public BeverageDetailsViewModel(BeverageDetailsRepository beverageDetailsRepo) {
        this.mBeverageDetailsRepo = beverageDetailsRepo;
    }

    public LiveData<BeverageDetails> getBeverageDetails() {
        if (mBeverageDetails == null) {
            mBeverageDetails = mBeverageDetailsRepo.getBeverageDetails();
        }
        return mBeverageDetails;
    }

    public LiveData<Throwable> listenException() {
        if (mException == null) {
            mException = mBeverageDetailsRepo.listenException();
        }
        return mException;
    }

    public void fetchBeverageByID(String id) {
        mBeverageDetailsRepo.fetchBeverageById(id);
    }

    public void addBeverageToFavourite(Beverage beverage, BeverageDetails beverageDetails) {
        mBeverageDetailsRepo.addBeverageToDb(beverage, beverageDetails);
    }

    public void deleteBeverageFromFavourite(Beverage beverage, BeverageDetails beverageDetails) {
        mBeverageDetailsRepo.removeBeverageFromDb(beverage, beverageDetails);
    }

    public boolean isBeverageFavourite(Beverage beverage) {
        Beverage fetchedBeverage = mBeverageDetailsRepo.fetchBeverageByIdFromDb(beverage.getId());
        return fetchedBeverage != null;
    }

    public BeverageDetails getFavouriteBeverageDetails(String id) {
        return mBeverageDetailsRepo.getFavouriteBeverageDetails(id);
    }

}
