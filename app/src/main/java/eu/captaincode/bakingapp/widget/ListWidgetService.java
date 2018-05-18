package eu.captaincode.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.model.Ingredient;
import eu.captaincode.bakingapp.provider.BakingProvider;

public class ListWidgetService extends RemoteViewsService {
    public static final String EXTRA_INGREDIENTS = "ingredients";
    private static final String TAG = ListWidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        if (intent != null && intent.hasExtra(EXTRA_INGREDIENTS)) {
            Log.d(TAG, "onGetViewFactory with extra");

            List<Ingredient> ingredients = intent.getParcelableArrayListExtra(EXTRA_INGREDIENTS);
            return new ListRemoteViewsFactory(this.getApplicationContext());
        }
        Log.d(TAG, "onGetViewFactory without extra");

        return new ListRemoteViewsFactory(this.getApplicationContext());
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = ListRemoteViewsFactory.class.getSimpleName();
    Context mContext;
    private Cursor mCursor = null;

    public ListRemoteViewsFactory(Context mContext) {
        Log.d(TAG, "ListRemoteViewsFactory constructor");
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate");
    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged");
        mCursor = mContext.getContentResolver().query(BakingProvider.Ingredients.CONTENT_URI,
                null, null, null, null);
        Log.d(TAG, "onDataSetChanged end");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(TAG, "getViewAt");
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_ingredient_item);

        mCursor.moveToPosition(position);

        remoteViews.setTextViewText(R.id.tv_ingredient_item_ingredient, mCursor.getString(1));
        remoteViews.setTextViewText(R.id.tv_ingredient_item_measure, mCursor.getString(2));
        remoteViews.setTextViewText(R.id.tv_ingredient_item_quantity,
                String.valueOf(mCursor.getFloat(3)));

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
