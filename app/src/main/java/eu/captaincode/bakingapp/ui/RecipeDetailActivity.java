package eu.captaincode.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {
    public static final String EXTRA_RECIPE = "recipe";

    private boolean mTwoPane;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent startingIntent = getIntent();
        if (startingIntent != null && startingIntent.hasExtra(EXTRA_RECIPE)) {
            mRecipe = startingIntent.getParcelableExtra(EXTRA_RECIPE);
        }

        if (findViewById(R.id.container_step_detail) != null) {
            mTwoPane = true;
        }

        RecipeDetailListFragment recipeDetailListFragment =
                RecipeDetailListFragment.newInstance(mRecipe);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_recipe_detail_list, recipeDetailListFragment)
                .commit();
    }
}
