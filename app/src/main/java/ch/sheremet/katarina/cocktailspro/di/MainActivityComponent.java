package ch.sheremet.katarina.cocktailspro.di;

import javax.inject.Singleton;

import ch.sheremet.katarina.cocktailspro.MainActivity;
import dagger.Component;

@Singleton
@Component(modules = {BeverageListViewModelModule.class, ApiModule.class})
public interface MainActivityComponent {
    void injectMainActivity(MainActivity mainActivity);
}
