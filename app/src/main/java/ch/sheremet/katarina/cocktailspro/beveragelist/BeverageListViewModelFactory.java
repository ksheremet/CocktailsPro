package ch.sheremet.katarina.cocktailspro.beveragelist;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

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
