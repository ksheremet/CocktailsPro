package ch.sheremet.katarina.cocktailspro.di;

import javax.inject.Singleton;

import ch.sheremet.katarina.cocktailspro.beveragelist.BeverageListFragment;
import dagger.Component;

@Singleton
@Component(modules = BeverageListViewModelModule.class)
public interface BeverageListFragmentComponent {
    void injectBeverageListFragment(BeverageListFragment fragment);
}
