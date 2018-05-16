package eu.captaincode.bakingapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import eu.captaincode.bakingapp.R;
import eu.captaincode.bakingapp.model.Step;

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
        holder.shortDescriptionTextView.setText(mStepList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mStepList.size();
    }

    public interface OnStepClickedListener {
        void onStepClicked(int stepPosition);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView shortDescriptionTextView;

        ViewHolder(View itemView) {
            super(itemView);
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
