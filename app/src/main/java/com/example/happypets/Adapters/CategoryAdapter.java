package com.example.happypets.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.happypets.Activity.AllPetsListActivity;
import com.example.happypets.Model.Category;
import com.example.happypets.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<Category>categories;
   Context context;
    public CategoryAdapter(ArrayList<Category> categories,Context context) {
        this.categories = categories;
        this.context=context;
    }

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_category,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.categoryName.setText(categories.get(position).getTitle());
        String picUrl="";
        switch (position){
            case 0:
                picUrl="dog";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_dog_backgroud));
                break;
            case 1:
                picUrl="cat";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_cat_backgroud));
                break;
            case 2:
                picUrl="horse";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_horse_backgroud));
                break;
            case 3:
                picUrl="bird";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_bird_backgroud));
                break;
            case 4:
                picUrl="fish";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_fish_backgroud));
                break;
            case 5:
                picUrl="rabbit";
                holder.mainLayout.setBackground(ContextCompat.getDrawable(holder.itemView.getContext(),R.drawable.category_rabbit_backgroud));
                break;
        }
        int drawerResourcesId=holder.itemView.getContext().getResources().getIdentifier(picUrl,"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawerResourcesId)
                .into(holder.categoryPic);
        holder.categoryPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,AllPetsListActivity.class);
                intent.putExtra("selectedValue",position);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
         TextView categoryName;
         ImageView categoryPic;
         ConstraintLayout mainLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName=itemView.findViewById(R.id.category_title);
            categoryPic=itemView.findViewById(R.id.category_image);
            mainLayout=itemView.findViewById(R.id.mainLayout);
        }
    }
}
