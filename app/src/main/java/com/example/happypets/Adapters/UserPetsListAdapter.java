package com.example.happypets.Adapters;

import static com.example.happypets.Activity.LoginActivity.token;
import static com.example.happypets.Activity.LoginActivity.userId;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happypets.Activity.PetDetailDisplayActivity;
import com.example.happypets.Model.Pet;
import com.example.happypets.R;
import com.example.happypets.Retrofit.APICall;
import com.example.happypets.Retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPetsListAdapter extends RecyclerView.Adapter<UserPetsListAdapter.ViewHolder> {

    // list of user pets
    private List<Pet> userPetsList;
    private Context context;
    APICall apiCall;
    public UserPetsListAdapter(List<Pet> userPetsList,Context context){
        this.userPetsList=userPetsList;
        this.context=context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.pets_list_display, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //user pet data
        Pet userPet = userPetsList.get(position);
        //binding data
        Picasso.get().load(userPet.getImageUrl()).into(holder.pet_imageview);
        holder.pet_type_textview.setText(userPet.getCategory());
        holder.pet_name_textview.setText(userPet.getName());
        holder.pet_breed_textview.setText(userPet.getBreed());
        holder.pet_gender_textview.setText(userPet.getGender());
        // setting item click listener
        holder.user_pets_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating intent to go to petdetaildisplay activity
                Intent intent=new Intent(context, PetDetailDisplayActivity.class);
                intent.putExtra("petId",userPet.getId());
                intent.putExtra("ownerId",userPet.getOwnerId());
                context.startActivity(intent);
            }
        });

        // retrofit to call api
        RetrofitService retrofitService=new RetrofitService();
        apiCall=retrofitService.getRetrofit().create(APICall.class);

        // method to delete the pet once user clicks on DeleteImageICon
        holder.petDeleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling function to delete the pet
                Toast.makeText(context, "Deleting...", Toast.LENGTH_SHORT).show();
                deletePostedPet(userPet.getId());
            }
        });
    }

    private void deletePostedPet(String petId) {
        apiCall.deletePet(token,petId,userId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context,context.getClass());
                    context.startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("call : "+call,"Throwable: "+t);
                Toast.makeText(context, "pet or user not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userPetsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView pet_imageview;
        public TextView pet_type_textview;
        public TextView pet_name_textview;
        public TextView pet_breed_textview;
        public TextView pet_gender_textview;
        public LinearLayout user_pets_item_layout;
        public ImageView petDeleteImageView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            pet_imageview = (CircleImageView) itemView.findViewById(R.id.petImage);
            pet_type_textview = (TextView) itemView.findViewById(R.id.PetType);
            pet_name_textview = (TextView) itemView.findViewById(R.id.DisplayPetName);
            pet_breed_textview = (TextView) itemView.findViewById(R.id.DisplayPetBreed);
            pet_gender_textview = (TextView) itemView.findViewById(R.id.DisplayPetGender);
            user_pets_item_layout =  (LinearLayout) itemView.findViewById(R.id.pet_list_item_layout);
            petDeleteImageView= (ImageView) itemView.findViewById(R.id.delete_icon);
        }
    }
}
