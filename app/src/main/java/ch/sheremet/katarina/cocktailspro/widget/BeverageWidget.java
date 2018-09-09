package ch.sheremet.katarina.cocktailspro.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import ch.sheremet.katarina.cocktailspro.MainActivity;
import ch.sheremet.katarina.cocktailspro.R;
import ch.sheremet.katarina.cocktailspro.model.BeverageDetails;
import ch.sheremet.katarina.cocktailspro.model.Ingredients;

/**
 * Implementation of App Widget functionality.
 */
public class BeverageWidget extends AppWidgetProvider {

    private static final String TAG = BeverageWidget.class.getSimpleName();

    //Set beverage details to update ingredients
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                BeverageDetails beverageDetails, int appWidgetId) {

        Log.d(TAG, "Update Widget: " + beverageDetails);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.beverage_widget);

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.widget_total, pendingIntent);

        //PopulateView
        if (beverageDetails == null) {
            views.setViewVisibility(R.id.widget_ingredient_list, View.INVISIBLE);
            views.setViewVisibility(R.id.default_widget_image, View.VISIBLE);
            views.setViewVisibility(R.id.widget_add_to_favourites_label, View.VISIBLE);
        } else {
            views.setViewVisibility(R.id.widget_add_to_favourites_label, View.INVISIBLE);
            views.setViewVisibility(R.id.default_widget_image, View.INVISIBLE);
            views.setTextViewText(R.id.beverage_name, beverageDetails.getName());
            views.setViewVisibility(R.id.widget_ingredient_list, View.VISIBLE);

            StringBuilder builder = new StringBuilder();
            for (Ingredients ingredient : beverageDetails.getIngredients()) {
                builder.append(ingredient.getIngredient()).append(" ").append(ingredient.getMeasure()).append("\n");
            }
            views.setTextViewText(R.id.widget_ingredient_list, builder.toString());
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        FavouriteBeverageJobIntentService.startFetchingFavouriteBeverage(context);
    }

    public static void updateBeverage(Context context, AppWidgetManager appWidgetManager,
                                      BeverageDetails beverageDetails, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, beverageDetails, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

