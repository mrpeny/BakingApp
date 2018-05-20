package eu.captaincode.bakingapp.ui;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import eu.captaincode.bakingapp.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityScreenTest {

    @Rule
    public IntentsTestRule<RecipeListActivity> mRecipeListActivityTestRule =
            new IntentsTestRule<>(RecipeListActivity.class);

    private IdlingRegistry mIdlingRegistry = IdlingRegistry.getInstance();


    @Before
    public void registerIdlingResource() {
        IdlingResource idlingResource = mRecipeListActivityTestRule.getActivity().getIdlingResource();
        mIdlingRegistry.register(idlingResource);
    }

    @Test
    public void clickRecycleViewItem_startsIntentWithExtraRecipe() {
        // when
        onView(withId(R.id.rv_recipe_list)).perform(actionOnItemAtPosition(2, click()));

        // then
        intended(allOf(
                hasComponent(RecipeDetailActivity.class.getCanonicalName()),
                hasExtraWithKey(RecipeDetailActivity.EXTRA_RECIPE)));
    }

    @Test
    public void clickRecycleViewItem_opensRecipeDetailActivity() {
        // when
        onView(withId(R.id.rv_recipe_list)).perform(actionOnItemAtPosition(2, click()));

        // then
        onView(withId(R.id.rv_recipe_detail_step_list)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        IdlingResource idlingResource = mRecipeListActivityTestRule.getActivity().getIdlingResource();
        mIdlingRegistry.unregister(idlingResource);
    }
}