package eu.captaincode.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.model.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_RECIPE_CHANGED =
            "eu.captaincode.bakingapp.action.RECIPE_CHANGED";
    public static final String EXTRA_RECIPE = "recipe";
    private static final String TAG = IngredientsWidgetProvider.class.getSimpleName();
    private Recipe mRecipe;
    private String mRecipeExtra;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction().equals(ACTION_RECIPE_CHANGED)) {
            Log.d(TAG, "onRecieve");
            mRecipeExtra = intent.getStringExtra(EXTRA_RECIPE);
            Log.d(TAG, mRecipeExtra);
            Toast.makeText(context, mRecipeExtra, Toast.LENGTH_SHORT).show();

            onUpdate(context, AppWidgetManager.getInstance(context), getAppWidgetIds(context));
        }
        super.onReceive(context, intent);
    }

    private int[] getAppWidgetIds(Context context) {
        ComponentName name = new ComponentName(context, IngredientsWidgetProvider.class);
        return AppWidgetManager.getInstance(context).getAppWidgetIds(name);
    }

    void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {
        Log.d(TAG, "updateAppWidget");
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);

        Intent widgetAdapterIntent = new Intent(context, ListWidgetService.class);

        widgetAdapterIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        widgetAdapterIntent.putExtra(ListWidgetService.EXTRA_INGREDIENTS, mRecipeExtra);
        widgetAdapterIntent.setData(Uri.parse(widgetAdapterIntent.toUri(Intent.URI_INTENT_SCHEME)));

        views.setRemoteAdapter(R.id.lv_ingredients_widget_ingredients_list, widgetAdapterIntent);
        views.setEmptyView(R.id.lv_ingredients_widget_ingredients_list, R.id.appwidget_empty_text);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.d(TAG, "onUpdate");
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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

