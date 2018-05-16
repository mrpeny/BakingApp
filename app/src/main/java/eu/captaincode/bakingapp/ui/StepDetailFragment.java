package eu.captaincode.bakingapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.model.Recipe;

public class StepDetailFragment extends Fragment {
    private static final String ARG_RECIPE = "recipe";
    private static final String ARG_STEP_POSITION = "step-position";

    private Recipe mRecipe;
    private int mStepPosition;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    public static StepDetailFragment newInstance(Recipe recipe, int stepPosition) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_RECIPE, recipe);
        args.putInt(ARG_STEP_POSITION, stepPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(ARG_RECIPE);
            mStepPosition = getArguments().getInt(ARG_STEP_POSITION);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ((TextView) fragmentView.findViewById(R.id.tv_step_detail_video_url))
                .setText(mRecipe.getSteps().get(mStepPosition).getVideoUrl());
        ((TextView) fragmentView.findViewById(R.id.tv_step_detail_detail_description))
                .setText(mRecipe.getSteps().get(mStepPosition).getDescription());
        ((TextView) fragmentView.findViewById(R.id.tv_step_detail_thumbnail_url))
                .setText(mRecipe.getSteps().get(mStepPosition).getmThumbnailUrl());

        return fragmentView;
    }
}
