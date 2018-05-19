package eu.captaincode.bakingapp.IdlingResource;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleIdlingResource implements IdlingResource {

    private static final String TAG = SimpleIdlingResource.class.getSimpleName();
    @Nullable
    private volatile ResourceCallback mCallback;

    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        Log.d(TAG, "isIdleNow: " + mIsIdleNow.get());

        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        Log.d(TAG, "registerIdleTransitionCallback");

        this.mCallback = callback;
    }

    public void setIdleState(boolean isIdleNow) {
        Log.d(TAG, "setIdleState: " + isIdleNow);

        mIsIdleNow.set(isIdleNow);
        if (isIdleNow && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }
}
