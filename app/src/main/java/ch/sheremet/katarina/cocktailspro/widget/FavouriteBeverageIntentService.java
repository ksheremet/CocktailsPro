package ch.sheremet.katarina.cocktailspro.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.List;
import java.util.Random;

import ch.sheremet.katarina.cocktailspro.model.Beverage;
import ch.sheremet.katarina.cocktailspro.model.BeverageDetails;
import ch.sheremet.katarina.cocktailspro.model.database.AppDatabase;
import ch.sheremet.katarina.cocktailspro.model.database.BeverageDao;

public class FavouriteBeverageIntentService extends IntentService {
    public static final String ACTION_FETCH_FAVOURITE_BEVERAGE = "ch.sheremet.katarina.cocktailspro.fetch_favourite_beverage";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public FavouriteBeverageIntentService() {
        super("FavouriteBeverageIntentService");
    }

    public static void startFetchingFavouriteBeverage(Context context) {
        Intent intent = new Intent(context, FavouriteBeverageIntentService.class);
        intent.setAction(ACTION_FETCH_FAVOURITE_BEVERAGE);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FETCH_FAVOURITE_BEVERAGE.equals(action)) {
                handleFetchingFavouriteBeverage();
            }
        }
    }

    private void handleFetchingFavouriteBeverage() {
        final BeverageDao beverageDao = AppDatabase.getInstance(getApplicationContext()).beverageDao();
        List<Beverage> beverageList = beverageDao.getAllBeveragesValue();
        BeverageDetails beverageDetails = null;

        // Choose random favourite beverage
        if (beverageList != null && beverageList.size()!=0) {
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
