package eu.captaincode.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.adapter.StepAdapter;
import eu.captaincode.bakingapp.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {
    public static final String EXTRA_RECIPE = "recipe";

    private boolean mTwoPane;
    private Recipe mRecipe;
    private StepAdapter mStepAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent startingIntent = getIntent();
        if (startingIntent != null && startingIntent.hasExtra(EXTRA_RECIPE)) {
            mRecipe = startingIntent.getParcelableExtra(EXTRA_RECIPE);
        }

        if (findViewById(R.id.fl_recipe_detail_container) != null) {
            mTwoPane = true;
        }

        RecyclerView stepRecyclerView = findViewById(R.id.rv_recipe_detail_list);
        mStepAdapter = new StepAdapter(this, mRecipe.getSteps());
        stepRecyclerView.setAdapter(mStepAdapter);
        stepRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
