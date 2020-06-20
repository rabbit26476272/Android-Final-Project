package com.manjurulhoque.myrecipes.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.manjurulhoque.myrecipes.R;
import com.manjurulhoque.myrecipes.activity.RecipeDetailsActivity;
import com.manjurulhoque.myrecipes.dbhelper.FavouriteDbHelper;
import com.manjurulhoque.myrecipes.model.Recipe;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.ViewHolder> {

    private List<Recipe> mRecipes;
    private Context mContext;
    private Recipe recipe;
    private FavouriteDbHelper favouriteDbHelper;

    public RecipeRecyclerViewAdapter(Context context, List<Recipe> recipes) {
        this.mContext = context;
        this.mRecipes = recipes;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_row, parent, false);
        //favouriteDbHelper = new FavouriteDbHelper(mContext.getApplicationContext());
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        recipe = mRecipes.get(position);
        holder.title.setText(recipe.getName());
        Picasso
                .get()
                .load(recipe.getImage())
                .error(android.R.drawable.gallery_thumb)
                .into(holder.image);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RecipeDetailsActivity.class);
                intent.putExtra("recipe", mRecipes.get(position));
                mContext.startActivity(intent);
            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favouriteDbHelper = new FavouriteDbHelper(mContext.getApplicationContext());
                addFavorite(mRecipes.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;
        public LinearLayout linearLayout;
        public ImageButton imageButton;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.news_title);
            image = view.findViewById(R.id.news_image);
            linearLayout = view.findViewById(R.id.linearLayout);
            imageButton = view.findViewById(R.id.imageButton_favorite);
        }
    }
    public void addFavorite(Recipe mRecipe){


        //Toast.makeText(mContext.getApplicationContext(), mRecipes.getKey(), Toast.LENGTH_SHORT).show();
        if (favouriteDbHelper.addData(mRecipe)) {
            Toast.makeText(mContext.getApplicationContext(), "已加入最愛", Toast.LENGTH_SHORT).show();
        } else {
            favouriteDbHelper.deleteById(mRecipe.getKey());
            Toast.makeText(mContext.getApplicationContext(), "已取消最愛", Toast.LENGTH_SHORT).show();
        }
    }
}
