package com.example.happypets.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happypets.Model.Pet;
import com.example.happypets.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllPetsListAdapter extends RecyclerView.Adapter<AllPetsListAdapter.ViewHolder> {

    private List<Pet> petsListData;

    public AllPetsListAdapter(List<Pet> petsListData){
        this.petsListData = petsListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.pet_display_format, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // class is need to be defined which will objectify the api data
        // then methods of that class will be used to bind the data to the layouts in the pets item
        // below view holder is defined which is used to store the pets item data

        //extracting data from list
        Pet pet = petsListData.get(position);

        Picasso.get().load(pet.getImageUrl()).into(holder.pet_imageview);
        holder.pet_type_textview.setText(pet.getCategory());
        holder.pet_name_textview.setText(pet.getName());
        holder.pet_breed_textview.setText(pet.getBreed());
        holder.pet_gender_textview.setText(pet.getGender());

        //setting item click listener
        holder.pets_relative_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //adding functionality
            }
        });

    }


    @Override
    public int getItemCount() {

        return petsListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public CircleImageView pet_imageview;
        public TextView pet_type_textview;
        public TextView pet_name_textview;
        public TextView pet_breed_textview;
        public TextView pet_gender_textview;
        public RelativeLayout pets_relative_layout;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            pet_imageview = (CircleImageView) itemView.findViewById(R.id.petImage);
            pet_type_textview = (TextView) itemView.findViewById(R.id.PetType);
            pet_name_textview = (TextView) itemView.findViewById(R.id.DisplayPetName);
            pet_breed_textview = (TextView) itemView.findViewById(R.id.DisplayPetBreed);
            pet_gender_textview = (TextView) itemView.findViewById(R.id.DisplayPetGender);
            pets_relative_layout = (RelativeLayout) itemView.findViewById(R.id.pet_list_item_layout);

        }
    }
}
