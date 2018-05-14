package eu.captaincode.bakingapp.network;


import java.util.List;

import eu.captaincode.bakingapp.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface UdacityRecipeService {
    String BASE_URL_UDACITY = "http://go.udacity.com/";

    @GET("android-baking-app-json")
    Call<List<Recipe>> listRecipes();
}
