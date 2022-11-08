
package com.example.happypets.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happypets.Model.Pet;
import com.example.happypets.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder>{

    private Context context;
    private List<Pet> petList;

    public PetAdapter(Context context, List<Pet> petList) {
        this.context = context;
        this.petList = petList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pet_display_format,parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pet pet= petList.get(position);
        holder.petName.setText(pet.getName());
        holder.gender.setText(pet.getGender());
        holder.category.setText(pet.getCategory());
        holder.breed.setText(pet.getBreed());
        Picasso.get().load(pet.getImageUrl()).into(holder.petImage);
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView petImage;
        public TextView petName,breed,gender,category;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            petName=itemView.findViewById(R.id.DisplayPetName);
            breed=itemView.findViewById(R.id.DisplayPetBreed);
            gender=itemView.findViewById(R.id.DisplayPetGender);
            petImage = itemView.findViewById(R.id.petImage);
            category=itemView.findViewById(R.id.DisplayPetCategory);
        }
    }
}
