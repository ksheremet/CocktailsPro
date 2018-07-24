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

    public void saveBeverage(Beverage beverage) {
        mBeverageDetailsRepo.addBeverageToDB(beverage);
    }

    public void deleteBeverage(Beverage beverage) {
        mBeverageDetailsRepo.removeBeverageFromDb(beverage);
    }

}
