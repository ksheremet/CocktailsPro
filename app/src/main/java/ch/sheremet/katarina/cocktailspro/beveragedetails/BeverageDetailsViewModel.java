package ch.sheremet.katarina.cocktailspro.beveragedetails;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import ch.sheremet.katarina.cocktailspro.model.Beverage;
import ch.sheremet.katarina.cocktailspro.model.BeverageDetails;

public class BeverageDetailsViewModel extends ViewModel {

    private static final String TAG = BeverageDetailsActivity.class.getSimpleName();

    private LiveData<BeverageDetails> mBeverageDetails;
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

    public void fetchBeverageByID(String id) {
        mBeverageDetailsRepo.fetchBeverageById(id);
    }

    public void addBeverageToFavourite(Beverage beverage, BeverageDetails beverageDetails) {
        mBeverageDetailsRepo.addBeverageToDB(beverage, beverageDetails);
    }

    public void deleteBeverageFromFavourite(Beverage beverage, BeverageDetails beverageDetails) {
        mBeverageDetailsRepo.removeBeverageFromDb(beverage, beverageDetails);
    }

    public boolean isBeverageFavourite(Beverage beverage) {
        Beverage fetchedBeverage = mBeverageDetailsRepo.fetchBeverageByIdFromStorage(beverage.getId());
        if (fetchedBeverage==null) {
            return false;
        } else {
            return true;
        }
    }

    public BeverageDetails getFavouriteBeverageDetails(String id) {
        return mBeverageDetailsRepo.getFavouriteBeverageDetails(id);
    }

}
