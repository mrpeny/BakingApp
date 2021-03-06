package eu.captaincode.bakingapp.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import eu.captaincode.bakingapp.model.Recipe;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Connects and downloads a list of Recipes from the Web and converts them to a list of Recipe
 * objects asynchronously.
 */
public class RecipesFetcher extends AsyncTask<Void, Void, List<Recipe>> {

    private static final String TAG = RecipesFetcher.class.getSimpleName();
    private OnRecipesFetchedListener mOnRecipesFetchedListener;

    public RecipesFetcher(OnRecipesFetchedListener onRecipesFetchedListener) {
        this.mOnRecipesFetchedListener = onRecipesFetchedListener;
    }

    @Override
    protected List<Recipe> doInBackground(Void... voids) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UdacityRecipeService.BASE_URL_UDACITY)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UdacityRecipeService recipeService = retrofit.create(UdacityRecipeService.class);
        Call<List<Recipe>> call = recipeService.listRecipes();

        try {
            Response<List<Recipe>> response = call.execute();
            if (response.isSuccessful()) {
                List<Recipe> recipeList = response.body();
                if (recipeList != null) {
                    return recipeList;
                } else {
                    Log.d(TAG, "Failed to download recipes. Error: " + response.code());
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Error while connecting to Udacity Recipe service: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Recipe> recipes) {
        mOnRecipesFetchedListener.onRecipesFetched(recipes);
    }

    /**
     * Listener that will be notified once the heavy network operation is done.
     */
    public interface OnRecipesFetchedListener {
        void onRecipesFetched(List<Recipe> recipeList);
    }
}
