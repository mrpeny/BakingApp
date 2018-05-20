package eu.captaincode.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import eu.captaincode.bakingapp.IdlingResource.SimpleIdlingResource;
import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.adapter.RecipeListAdapter;
import eu.captaincode.bakingapp.model.Recipe;
import eu.captaincode.bakingapp.network.RecipesFetcher;
import eu.captaincode.bakingapp.widget.IngredientsWidgetProvider;

public class RecipeListActivity extends AppCompatActivity
        implements RecipesFetcher.OnRecipesFetchedListener, RecipeListAdapter.OnRecipeClickedListener {

    private List<Recipe> mRecipeList = new ArrayList<>();
    private RecipeListAdapter mAdapter;
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        RecyclerView recyclerView = findViewById(R.id.rv_recipe_list);
        mAdapter = new RecipeListAdapter(this, mRecipeList, this);
        recyclerView.setAdapter(mAdapter);
        int orientationSpecificSpanCount = getResources().getInteger(R.integer.grid_span_count_recipe_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, orientationSpecificSpanCount));

        getIdlingResource();
        setAppIdleState(false);
        new RecipesFetcher(this).execute();
    }

    @Override
    public void onRecipesFetched(List<Recipe> recipeList) {
        if (recipeList != null) {
            mAdapter.swapData(recipeList);
        }

        setAppIdleState(true);
    }

    @Override
    public void onRecipeClicked(int position) {
        Recipe recipe = mRecipeList.get(position);

        sendUpdateWidgetBroadcast(recipe);
        launchDetailActivity(recipe);
    }

    private void sendUpdateWidgetBroadcast(Recipe recipe) {
        Intent intent = new Intent(getApplicationContext(), IngredientsWidgetProvider.class);
        intent.setAction(IngredientsWidgetProvider.ACTION_RECIPE_CHANGED);
        intent.putExtra(IngredientsWidgetProvider.EXTRA_RECIPE, recipe);
        getApplicationContext().sendBroadcast(intent);
    }

    private void launchDetailActivity(Recipe recipe) {
        Intent launchDetailActivityIntent = new Intent(this, RecipeDetailActivity.class);
        launchDetailActivityIntent.putExtra(RecipeDetailActivity.EXTRA_RECIPE, recipe);
        startActivity(launchDetailActivityIntent);
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    private void setAppIdleState(boolean idle) {
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(idle);
        }
    }
}
