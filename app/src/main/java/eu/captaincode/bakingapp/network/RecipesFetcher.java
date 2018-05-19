package eu.captaincode.bakingapp.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import eu.captaincode.bakingapp.IdlingResource.SimpleIdlingResource;
import eu.captaincode.bakingapp.model.Recipe;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipesFetcher extends AsyncTask<Void, Void, List<Recipe>> {
    private static final String TAG = RecipesFetcher.class.getSimpleName();

    private OnRecipesFetchedListener mOnRecipesFetchedListener;
    private SimpleIdlingResource mIdlingResource;

    public RecipesFetcher(OnRecipesFetchedListener onRecipesFetchedListener,
                          SimpleIdlingResource idlingResource) {
        this.mOnRecipesFetchedListener = onRecipesFetchedListener;
        mIdlingResource = idlingResource;
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

    public interface OnRecipesFetchedListener {
        void onRecipesFetched(List<Recipe> recipeList);
    }
}
