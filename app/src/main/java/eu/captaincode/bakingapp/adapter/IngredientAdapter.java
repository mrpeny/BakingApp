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
import eu.captaincode.bakingapp.model.Ingredient;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<Ingredient> mIngredients;
    private Context mContext;

    public IngredientAdapter(Context mContext, List<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ingredient_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = mIngredients.get(position);

        holder.ingredientTextView.setText(ingredient.getIngredient());
        holder.quantityTextView.setText(String.valueOf(ingredient.getQuantity()));
        holder.measureTextView.setText(ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientTextView;
        TextView quantityTextView;
        TextView measureTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ingredientTextView = itemView.findViewById(R.id.tv_ingredient_item_ingredient);
            quantityTextView = itemView.findViewById(R.id.tv_ingredient_item_quantity);
            measureTextView = itemView.findViewById(R.id.tv_ingredient_item_measure);
        }
    }
}
