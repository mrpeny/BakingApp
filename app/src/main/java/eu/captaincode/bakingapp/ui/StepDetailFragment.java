package eu.captaincode.bakingapp.ui;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.databinding.FragmentStepDetailBinding;
import eu.captaincode.bakingapp.model.Recipe;
import eu.captaincode.bakingapp.model.Step;

public class StepDetailFragment extends Fragment {
    private static final String ARG_RECIPE = "recipe";
    private static final String ARG_STEP_POSITION = "step-position";
    public static final String STATE_VIDEO_POSITION = "video-position";
    public static final String STATE_PLAY_WHEN_READY = "play-when-ready";

    private SimpleExoPlayer mPlayer;
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private long mVideoPosition;
    private Step mStep;
    private MediaSource mVideoSource;
    private TrackSelector mTrackSelector;
    private boolean mPlayWhenReadyState;

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
            Recipe recipe = getArguments().getParcelable(ARG_RECIPE);
            int stepPosition = getArguments().getInt(ARG_STEP_POSITION);
            mStep = recipe.getSteps().get(stepPosition);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentStepDetailBinding stepDetailBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_step_detail, container, false);
        if (mStep != null) {
            bindStepToUi(stepDetailBinding);
        }
        if (savedInstanceState != null) {
            mVideoPosition = savedInstanceState.getLong(STATE_VIDEO_POSITION, 0);
            mPlayWhenReadyState = savedInstanceState.getBoolean(STATE_PLAY_WHEN_READY, false);
        }
        return stepDetailBinding.getRoot();
    }

    private void bindStepToUi(FragmentStepDetailBinding stepDetailBinding) {
        stepDetailBinding.setStep(mStep);
        mSimpleExoPlayerView = stepDetailBinding.exoplayerStepDetail;
        if (TextUtils.isEmpty(mStep.getVideoUrl())) {
            stepDetailBinding.exoplayerStepDetail.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    private void initializePlayer() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        mTrackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), mTrackSelector);
        mSimpleExoPlayerView.setPlayer(mPlayer);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), getString(R.string.app_name)));
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        Uri videoUri = Uri.parse(mStep.getVideoUrl());
        mVideoSource = new ExtractorMediaSource(videoUri, dataSourceFactory,
                extractorsFactory, null, null);

        mPlayer.prepare(mVideoSource);
        mPlayer.setPlayWhenReady(mPlayWhenReadyState);
        mPlayer.seekTo(mVideoPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        mVideoPosition = mPlayer.getCurrentPosition();
        mPlayWhenReadyState = mPlayer.getPlayWhenReady();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
            mVideoSource = null;
            mTrackSelector = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        long currentPosition = mPlayer.getCurrentPosition();
        outState.putLong(STATE_VIDEO_POSITION, currentPosition);
        outState.putBoolean(STATE_PLAY_WHEN_READY, mPlayWhenReadyState);
        super.onSaveInstanceState(outState);
    }
}
