package eu.captaincode.bakingapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;

import eu.captaincode.bakingapp.model.Recipe;
import eu.captaincode.bakingapp.widget.IngredientsWidgetProvider;
import eu.captaincode.bakingapp.widget.ListRemoteViewsFactory;

public class WidgetUpdateService extends IntentService {
    public static final String ACTION_RECIPE_CHANGED =
            "eu.captaincode.bakingapp.action.RECIPE_CHANGED";
    public static final String EXTRA_RECIPE = "recipe";

    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && intent.getAction() != null &&
                intent.getAction().equals(ACTION_RECIPE_CHANGED)) {
            Recipe recipe = intent.getParcelableExtra(EXTRA_RECIPE);
            updateRecipeInSharedPreferences(recipe);
            sendUpdateWidgetBroadcast();
        }
    }

    private void updateRecipeInSharedPreferences(@NonNull Recipe recipe) {
        String recipeJson = new Gson().toJson(recipe);
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putString(ListRemoteViewsFactory.PREF_KEY_RECIPE, recipeJson).apply();
    }

    private void sendUpdateWidgetBroadcast() {
        Intent updateWidgetIntent = new Intent(getApplicationContext(),
                IngredientsWidgetProvider.class);
        updateWidgetIntent.setAction(ACTION_RECIPE_CHANGED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(updateWidgetIntent);
    }
}
