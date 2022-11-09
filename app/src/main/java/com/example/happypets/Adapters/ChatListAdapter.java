package com.example.happypets.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happypets.Activity.ChatDetailActivity;
import com.example.happypets.Model.User;
import com.example.happypets.R;
import com.example.happypets.databinding.ChatUserListItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private List<User> userList;

    private Context context;

    public ChatListAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // here we are creating a view for each list view item
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.chat_user_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // getting the user data at a position
        User user = userList.get(position);
        // binding that data
        Picasso.get().load(user.getImageUrl()).into(holder.userImage);
        holder.userName.setText(user.getName());
        //setting onClickItem event listener
        holder.chatLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatDetailActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public CircleImageView userImage;
        public TextView userName;
        public LinearLayout chatLinearLayout;

        // here we are defining the variables in the list item view which will be used to bind data in them
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userImage = (CircleImageView) itemView.findViewById(R.id.chat_list_item_user_image);
            userName = (TextView) itemView.findViewById(R.id.chat_list_item_user_name);
            chatLinearLayout = (LinearLayout) itemView.findViewById(R.id.user_list_item_layout);
        }
    }
}
