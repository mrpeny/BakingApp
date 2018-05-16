package eu.captaincode.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.adapter.IngredientAdapter;
import eu.captaincode.bakingapp.adapter.StepAdapter;
import eu.captaincode.bakingapp.model.Recipe;

public class RecipeDetailListFragment extends Fragment {
    private static final String ARG_RECIPE = "recipe";

    private Recipe mRecipe;

    private OnFragmentInteractionListener mListener;

    public RecipeDetailListFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailListFragment newInstance(Recipe recipe) {
        RecipeDetailListFragment fragment = new RecipeDetailListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(ARG_RECIPE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_recipe_detail_list, container, false);

        RecyclerView ingredientRecyclerView = fragmentView.findViewById(R.id.rv_recipe_detail_ingredient_list);
        IngredientAdapter ingredientAdapter = new IngredientAdapter(getContext(), mRecipe.getIngredients());
        ingredientRecyclerView.setAdapter(ingredientAdapter);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView stepRecyclerView = fragmentView.findViewById(R.id.rv_recipe_detail_step_list);
        StepAdapter stepAdapter = new StepAdapter(getContext(), mRecipe.getSteps());
        stepRecyclerView.setAdapter(stepAdapter);
        stepRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return fragmentView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
