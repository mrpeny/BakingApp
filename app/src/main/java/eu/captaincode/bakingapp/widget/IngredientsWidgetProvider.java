package eu.captaincode.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.service.WidgetUpdateService;
import eu.captaincode.bakingapp.ui.RecipeDetailActivity;

/**
 * Implements the BakingApp Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null &&
                intent.getAction().equals(WidgetUpdateService.ACTION_RECIPE_CHANGED)) {
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

        addPendingIntentTemplate(context, views);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void addPendingIntentTemplate(Context context, RemoteViews views) {
        Intent launchDetailActivityIntent = new Intent(context, RecipeDetailActivity.class);
        PendingIntent launchDetailActivityPendingIntent = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(launchDetailActivityIntent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.lv_ingredients_widget_ingredients_list,
                launchDetailActivityPendingIntent);
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

