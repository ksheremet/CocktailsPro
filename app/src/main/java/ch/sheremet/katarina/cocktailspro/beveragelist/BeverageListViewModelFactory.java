package ch.sheremet.katarina.cocktailspro.beveragelist;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class BeverageListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private BeverageListRepository mBeverageRepo;

    public BeverageListViewModelFactory(BeverageListRepository mRepo) {
        this.mBeverageRepo = mRepo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new BeverageListViewModel(mBeverageRepo);
    }
}
