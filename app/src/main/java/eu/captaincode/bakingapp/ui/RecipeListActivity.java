package eu.captaincode.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.adapter.RecipeListAdapter;
import eu.captaincode.bakingapp.model.Recipe;
import eu.captaincode.bakingapp.network.RecipesFetcher;

public class RecipeListActivity extends AppCompatActivity
        implements RecipesFetcher.OnRecipesFetchedListener, RecipeListAdapter.OnRecipeClickedListener {

    private List<Recipe> mRecipeList = new ArrayList<>();
    private RecipeListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        RecyclerView recyclerView = findViewById(R.id.rv_recipe_list);
        mAdapter = new RecipeListAdapter(this, mRecipeList, this);
        recyclerView.setAdapter(mAdapter);
        int orientationSpecificSpanCount = getResources().getInteger(R.integer.grid_span_count_recipe_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, orientationSpecificSpanCount));

        new RecipesFetcher(this).execute();
    }

    @Override
    public void onRecipesFetched(List<Recipe> recipeList) {
        if (recipeList != null) {
            mAdapter.swapData(recipeList);
        }
    }

    @Override
    public void onRecipeClicked(int position) {
        Toast.makeText(this, "Clicked: " + mRecipeList.get(position).getName(), Toast.LENGTH_SHORT).show();
    }
}
