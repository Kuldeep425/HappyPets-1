package com.example.happypets.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happypets.Model.Pet;
import com.example.happypets.R;

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
        public TextView pet_category_textview;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            this.pet_imageview = (CircleImageView) itemView.findViewById(R.id.petImage);
            this.pet_type_textview = (TextView) itemView.findViewById(R.id.PetType);
            this.pet_name_textview = (TextView) itemView.findViewById(R.id.DisplayPetName);
            this.pet_breed_textview = (TextView) itemView.findViewById(R.id.DisplayPetBreed);
            this.pet_gender_textview = (TextView) itemView.findViewById(R.id.DisplayPetGender);
            this.pet_category_textview = (TextView) itemView.findViewById(R.id.DisplayPetCategory);

        }
    }
}
