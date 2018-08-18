package ch.sheremet.katarina.cocktailspro.beveragedetails;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class BeverageDetailsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private BeverageDetailsRepository mBeverageDetailsRepo;

    public BeverageDetailsViewModelFactory(BeverageDetailsRepository mRepo) {
        this.mBeverageDetailsRepo = mRepo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new BeverageDetailsViewModel(mBeverageDetailsRepo);
    }
}
