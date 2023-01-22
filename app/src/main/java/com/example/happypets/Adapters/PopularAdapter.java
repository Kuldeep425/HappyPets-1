package com.example.happypets.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happypets.Model.Pet;
import com.example.happypets.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    List<Pet> pets;
    Context context;
    public PopularAdapter(List<Pet>pets,Context context){
        this.pets=pets;
        this.context=context;
    }

    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_popular,parent,false);
        return new PopularAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
           holder.petName.setText(pets.get(position).getName());
           String picUrl=pets.get(position).getImageUrl();
           if(picUrl!=null){
               Picasso.get().load(picUrl).into(holder.pic);
           }
           else{
               holder.pic.setImageResource(R.drawable.profile_image);
           }
           holder.viewMore.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

               }
           });
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView petName;
        CircleImageView pic;
        Button viewMore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            petName=itemView.findViewById(R.id.textViewName_popular);
            pic=itemView.findViewById(R.id.popular_imageView);
            viewMore=itemView.findViewById(R.id.view_popular);
        }
    }
}

