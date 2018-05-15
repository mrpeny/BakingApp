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
import eu.captaincode.bakingapp.model.Recipe;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private Context mContext;
    private List<Recipe> mRecipeList;
    private OnRecipeClickedListener mRecipeClickListener;

    public RecipeListAdapter(Context mContext, List<Recipe> recipeList,
                             OnRecipeClickedListener listener) {
        this.mContext = mContext;
        this.mRecipeList = recipeList;
        this.mRecipeClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = mRecipeList.get(position);
        holder.nameTextView.setText(recipe.getName());
        holder.servingsTextView.setText(mContext.getResources()
                .getString(R.string.recipe_list_servings, recipe.getServings()));
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public void swapData(List<Recipe> newRecipes) {
        mRecipeList.clear();
        mRecipeList.addAll(newRecipes);
        notifyDataSetChanged();
    }

    public interface OnRecipeClickedListener {
        void onRecipeClicked(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView nameTextView;
        private TextView servingsTextView;

        ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tv_recipe_item_recipe_name);
            servingsTextView = itemView.findViewById(R.id.tv_recipe_item_recipe_servings);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mRecipeClickListener.onRecipeClicked(position);
        }
    }
}
