package eu.captaincode.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.model.Step;

/**
 * Populates AdapterViews or RecyclerViews with a list of {@link Step}s.
 */
public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {

    private Context mContext;
    private List<Step> mStepList;
    private OnStepClickedListener mOnStepClickedListener;

    public StepAdapter(Context mContext, List<Step> mStepList,
                       OnStepClickedListener onStepClickedListener) {
        this.mContext = mContext;
        this.mStepList = mStepList;
        this.mOnStepClickedListener = onStepClickedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.step_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (TextUtils.isEmpty(mStepList.get(position).getThumbnailUrl())) {
            Picasso.get()
                    .load(mStepList.get(position).getThumbnailUrl())
                    .placeholder(R.drawable.ic_cake_vector)
                    .into(holder.thumbnailImageView);
        }
        holder.shortDescriptionTextView.setText(
                mContext.getString(R.string.step_list_short_description,
                        mStepList.get(position).getId(), mStepList.get(position).getShortDescription()));
    }

    @Override
    public int getItemCount() {
        return mStepList.size();
    }

    public interface OnStepClickedListener {
        void onStepClicked(int stepPosition);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView thumbnailImageView;
        private TextView shortDescriptionTextView;

        ViewHolder(View itemView) {
            super(itemView);
            thumbnailImageView = itemView.findViewById(R.id.iv_step_item_thumbnail);
            shortDescriptionTextView = itemView.findViewById(R.id.tv_step_item_short_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnStepClickedListener.onStepClicked(position);
        }
    }
}
