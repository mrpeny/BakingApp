package eu.captaincode.bakingapp.ui;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import eu.captaincode.bakingapp.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityScreenTest {

    private static final String TAG = RecipeListActivity.class.getSimpleName();
    @Rule
    public ActivityTestRule<RecipeListActivity> mRecipeListActivityTestRule =
            new ActivityTestRule<>(RecipeListActivity.class);

    private IdlingRegistry mIdlingRegistry = IdlingRegistry.getInstance();


    @Before
    public void registerIdlingResource() {
        Log.d(TAG, "registerIdlingResource");

        IdlingResource idlingResource = mRecipeListActivityTestRule.getActivity().getIdlingResource();
        mIdlingRegistry.register(idlingResource);
    }

    @Test
    public void clickRecycleViewItem_OpensRecipeDetailActivity() {
        Log.d(TAG, "@Test");

        onData(anything()).inAdapterView(withId(R.id.rv_recipe_list)).atPosition(2).perform(click());
    }

    @After
    public void unregisterIdlingResource() {
        Log.d(TAG, "unregisterIdlingResource");
        IdlingResource idlingResource = mRecipeListActivityTestRule.getActivity().getIdlingResource();
        mIdlingRegistry.unregister(idlingResource);
    }
}