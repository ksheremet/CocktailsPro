package ch.sheremet.katarina.cocktailspro.di;

import javax.inject.Singleton;

import ch.sheremet.katarina.cocktailspro.beveragedetails.BeverageDetailsFragment;
import dagger.Component;

@Singleton
@Component(modules = BeverageDetailsViewModelModule.class)
public interface BeverageDetailsFragmentComponent {
    void injectBeverageDetailsFragment(BeverageDetailsFragment fragment);
}
