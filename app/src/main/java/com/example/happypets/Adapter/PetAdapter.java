
package com.example.happypets.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.happypets.Model.User;
import com.example.happypets.R;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder>{

    private Context context;
    private List<User> userList;

    public PetAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_pet_display,parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final User user = userList.get(position);

        holder.userEmail.setText(user.getEmail());
        holder.phoneNumber.setText(user.getPhoneNumber());
        holder.userName.setText(user.getName());


      //  Glide.with(context).load(user.getProfilepictureurl()).into(holder.userProfileImage);






    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView userProfileImage;
        public TextView type, userName ,phoneNumber , userEmail;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            userProfileImage = itemView.findViewById(R.id.userProfileImage);



        }
    }


}
