package ch.sheremet.katarina.cocktailspro.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import java.util.List;
import java.util.Random;

import ch.sheremet.katarina.cocktailspro.model.Beverage;
import ch.sheremet.katarina.cocktailspro.model.BeverageDetails;
import ch.sheremet.katarina.cocktailspro.model.database.AppDatabase;
import ch.sheremet.katarina.cocktailspro.model.database.BeverageDao;

public class FavouriteBeverageJobIntentService extends JobIntentService {
    public static final String ACTION_FETCH_FAVOURITE_BEVERAGE = "ch.sheremet.katarina.cocktailspro.fetch_favourite_beverage";
    /**
     * Unique job ID for this service.
     */
    static final int BEVERAGE_JOB_ID = 1002;

    public static void startFetchingFavouriteBeverage(Context context) {
        Intent intent = new Intent(context, FavouriteBeverageJobIntentService.class);
        intent.setAction(ACTION_FETCH_FAVOURITE_BEVERAGE);
        enqueueWork(context, FavouriteBeverageJobIntentService.class, BEVERAGE_JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        final String action = intent.getAction();
        if (ACTION_FETCH_FAVOURITE_BEVERAGE.equals(action)) {
            handleFetchingFavouriteBeverage();
        }
    }

    private void handleFetchingFavouriteBeverage() {
        final BeverageDao beverageDao = AppDatabase.getInstance(getApplicationContext()).beverageDao();
        List<Beverage> beverageList = beverageDao.getAllBeveragesValue();
        BeverageDetails beverageDetails = null;

        // Choose random favourite beverage
        if (beverageList != null && beverageList.size() != 0) {
            Random rand = new Random();
            int randValue = rand.nextInt(beverageList.size());
            beverageDetails = beverageDao.getBeverageDetailsWithIngredients(beverageList.get(randValue).getId());
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BeverageWidget.class));
        //Update all widgets
        BeverageWidget.updateBeverage(this, appWidgetManager, beverageDetails, appWidgetIds);
    }
}
