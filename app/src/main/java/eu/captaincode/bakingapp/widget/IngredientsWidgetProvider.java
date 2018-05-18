package eu.captaincode.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.model.Recipe;

/**
 * Implementation of BakingApp Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_RECIPE_CHANGED =
            "eu.captaincode.bakingapp.action.RECIPE_CHANGED";
    public static final String EXTRA_RECIPE = "recipe";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals(ACTION_RECIPE_CHANGED)) {
            Recipe recipe = intent.getParcelableExtra(EXTRA_RECIPE);
            String recipeJson = new Gson().toJson(recipe);
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putString(ListRemoteViewsFactory.PREF_KEY_RECIPE, recipeJson).apply();
            AppWidgetManager.getInstance(context).notifyAppWidgetViewDataChanged(
                    getAppWidgetIds(context), R.id.lv_ingredients_widget_ingredients_list);
        }
        super.onReceive(context, intent);
    }

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        Intent widgetAdapterIntent = new Intent(context, ListWidgetService.class);
        views.setRemoteAdapter(R.id.lv_ingredients_widget_ingredients_list, widgetAdapterIntent);
        views.setEmptyView(R.id.lv_ingredients_widget_ingredients_list, R.id.appwidget_empty_text);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // No implementation needed
    }

    @Override
    public void onDisabled(Context context) {
        // No implementation needed
    }

    private int[] getAppWidgetIds(Context context) {
        ComponentName name = new ComponentName(context, IngredientsWidgetProvider.class);
        return AppWidgetManager.getInstance(context).getAppWidgetIds(name);
    }
}

