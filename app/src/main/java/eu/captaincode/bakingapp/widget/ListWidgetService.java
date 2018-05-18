package eu.captaincode.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.model.Ingredient;
import eu.captaincode.bakingapp.model.Recipe;

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory,
        SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String PREF_KEY_RECIPE = "recipe";

    private Context mContext;
    private List<Ingredient> mIngredients = new ArrayList<>();

    ListRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        final SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDataSetChanged() {
        String recipeJson = PreferenceManager.getDefaultSharedPreferences(mContext)
                .getString(PREF_KEY_RECIPE, null);
        if (recipeJson != null) {
            Recipe recipe = new Gson().fromJson(recipeJson, Recipe.class);
            mIngredients = recipe.getIngredients();
        }
    }

    @Override
    public void onDestroy() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public int getCount() {
        return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_ingredient_item);

        Ingredient ingredient = mIngredients.get(position);
        remoteViews.setTextViewText(R.id.tv_ingredient_item_ingredient, ingredient.getIngredient());
        remoteViews.setTextViewText(R.id.tv_ingredient_item_measure, ingredient.getMeasure());
        remoteViews.setTextViewText(R.id.tv_ingredient_item_quantity,
                String.valueOf(ingredient.getQuantity()));

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PREF_KEY_RECIPE)) {
            onDataSetChanged();
        }
    }
}
