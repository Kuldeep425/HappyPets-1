package com.example.happypets.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happypets.Activity.PetDetailDisplayActivity;
import com.example.happypets.Model.Pet;
import com.example.happypets.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavouritePetListAdapter extends RecyclerView.Adapter<FavouritePetListAdapter.ViewHolder> {

    private List<Pet> petsListData;
    Context context;

    public FavouritePetListAdapter(List<Pet> petsListData, Context context) {
        this.petsListData = petsListData;
        this.context = context;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.pet_fav_view, parent, false);
        FavouritePetListAdapter.ViewHolder viewHolder = new FavouritePetListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pet pet=petsListData.get(position);
        holder.favPetName.setText(pet.getName());
        holder.favPetCategory.setText(pet.getCategory());
        holder.favPetAddress.setText("Mnnit Allahabad,211004");
        Picasso.get().load(pet.getImageUrl()).into(holder.favPetImage);

        holder.favRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PetDetailDisplayActivity.class);
                intent.putExtra("petId",pet.getId());
                intent.putExtra("ownerId",pet.getOwnerId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return petsListData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView favPetImage;
        public TextView favPetName;
        public TextView favPetAddress;
        public TextView favPetCategory;
        public RelativeLayout favRelativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing the components
            favPetImage=itemView.findViewById(R.id.fav_petImage);
            favPetName=itemView.findViewById(R.id.fav_displayPetName);
            favPetAddress=itemView.findViewById(R.id.fav_displayPetAddress);
            favPetCategory=itemView.findViewById(R.id.fav_displayPetCategory);
            favRelativeLayout=itemView.findViewById(R.id.fav_pet_list_item_layout);
        }
    }

}

