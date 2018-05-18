package eu.captaincode.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.model.Recipe;

public class StepDetailActivity extends AppCompatActivity {
    public static final String ARG_RECIPE = "recipe";
    public static final String ARG_STEP_POSITION = "step-position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Recipe recipe = extras.getParcelable(ARG_RECIPE);
            int stepPosition = extras.getInt(ARG_STEP_POSITION);
            showFragment(recipe, stepPosition);
        }
    }

    private void showFragment(Recipe recipe, int stepPosition) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.container_step_detail) == null) {
            StepDetailFragment stepDetailFragment = StepDetailFragment.newInstance(recipe,
                    stepPosition);
            fragmentManager.beginTransaction()
                    .add(R.id.container_step_detail, stepDetailFragment)
                    .commit();
        }
    }
}
