package com.example.happypets.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happypets.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatDetailsListAdapter extends RecyclerView.Adapter {

    // to determine the view
    private static final int TYPE_MESSAGE_SENT = 0;
    private static final int TYPE_MESSAGE_RECEIVED = 1;

    private LayoutInflater inflater;
    private List<JSONObject> messages = new ArrayList<>();

    public ChatDetailsListAdapter (LayoutInflater inflater) {
        this.inflater = inflater;
    }

    // this is the view holder for sent messages item
    private class SentMessageHolder extends RecyclerView.ViewHolder {

        TextView messageTxt;

        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);

            messageTxt = itemView.findViewById(R.id.chat_sent_message);
        }
    }

    // this is the view holder for received messages item
    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {

        TextView messageTxt;

        public ReceivedMessageHolder(@NonNull View itemView) {
            super(itemView);
            messageTxt = itemView.findViewById(R.id.chat_received_message);
        }
    }

    // method determines which view holder to use
    @Override
    public int getItemViewType(int position) {
        JSONObject message = messages.get(position);
        try {
            if (message.getBoolean("isSent")) {
                return TYPE_MESSAGE_SENT;
            }
            else {
                return TYPE_MESSAGE_RECEIVED;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

    @NonNull
    @Override
    // used to set the layout of each view holder
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_MESSAGE_SENT:
                view = inflater.inflate(R.layout.chat_details_item_sent_message, parent, false);
                return new SentMessageHolder(view);
            case TYPE_MESSAGE_RECEIVED:
                view = inflater.inflate(R.layout.chat_details_item_recieved_message, parent, false);
                return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        JSONObject message = messages.get(position);
        try {
            if (message.getBoolean("isSent")) {
                SentMessageHolder messageHolder = (SentMessageHolder) holder;
                messageHolder.messageTxt.setText(message.getString("message"));

            }
            else {
                ReceivedMessageHolder messageHolder = (ReceivedMessageHolder) holder;
                messageHolder.messageTxt.setText(message.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // we create our method to add data to the list and notifys the change
    public void addItem (JSONObject jsonObject) {
        messages.add(jsonObject);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}
