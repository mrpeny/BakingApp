package eu.captaincode.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeDetailListFragment.OnStepSelectedListener {
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
            setTitle(mRecipe.getName());
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

    @Override
    public void onStepSelected(int stepPosition) {
        if (mTwoPane) {
            StepDetailFragment stepDetailFragment = StepDetailFragment.newInstance(mRecipe,
                    stepPosition);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_step_detail, stepDetailFragment)
                    .commit();
        } else {
            Intent startStepDetailActivityIntent = new Intent(this, StepDetailActivity.class);
            Bundle extras = new Bundle();
            extras.putParcelable(StepDetailActivity.ARG_RECIPE, mRecipe);
            extras.putInt(StepDetailActivity.ARG_STEP_POSITION, stepPosition);
            startStepDetailActivityIntent.putExtras(extras);
            startActivity(startStepDetailActivityIntent);
        }
    }
}
