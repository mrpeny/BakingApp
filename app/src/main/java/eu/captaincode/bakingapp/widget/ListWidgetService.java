package eu.captaincode.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import eu.captaincode.bakingapp.R;

public class ListWidgetService extends RemoteViewsService {
    public static final String EXTRA_INGREDIENTS = "ingredients";
    private static final String TAG = ListWidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        if (intent != null && intent.hasExtra(EXTRA_INGREDIENTS)) {
            Log.d(TAG, "onGetViewFactory with extra");

            return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
        }
        Log.d(TAG, "onGetViewFactory without extra");

        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = ListRemoteViewsFactory.class.getSimpleName();
    public static final String EXTRA_INGREDIENTS = "ingredients";

    Context mContext;
    String mRecipeExtra = "";

    public ListRemoteViewsFactory(Context mContext) {
        Log.d(TAG, "ListRemoteViewsFactory constructor");
        this.mContext = mContext;
    }

    public ListRemoteViewsFactory(Context mContext, Intent intent) {
        this.mContext = mContext;
        if (intent.hasExtra(EXTRA_INGREDIENTS)) {
            Log.d(TAG, "ListRemoteViewsFactory constructor");
            this.mRecipeExtra = intent.getStringExtra(EXTRA_INGREDIENTS);
            Log.d(TAG, "Has extra: " + mRecipeExtra);
        }
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged");
        //mRecipeExtra = "Recipe extra in ODSC";
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(TAG, "getViewAt");
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_ingredient_item);

        remoteViews.setTextViewText(R.id.tv_ingredient_item_ingredient, mRecipeExtra);
        remoteViews.setTextViewText(R.id.tv_ingredient_item_measure, mRecipeExtra);
        remoteViews.setTextViewText(R.id.tv_ingredient_item_quantity, mRecipeExtra);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
