package com.example.nemo1.hangout_private.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nemo1.hangout_private.Entity.eChat;
import com.example.nemo1.hangout_private.R;

import java.util.List;

public class ChatInputAdapter extends RecyclerView.Adapter<ChatInputAdapter.MyViewHolder>{
    private Context context;
    private List<eChat> eChatList;
    private int index;
    private View view;

    public ChatInputAdapter(Context context, List<eChat> eChatList, int index) {
        this.context = context;
        this.eChatList = eChatList;
        this.index = index;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_chat_input,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        eChat eChat = eChatList.get(i);
        myViewHolder.input_text.setText(eChat.getUserMessage());
        myViewHolder.user_input.setText(eChat.getUserName());
    }

    @Override
    public int getItemCount() {
        return eChatList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView user_input,input_text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user_input = itemView.findViewById(R.id.user_input);
            input_text = itemView.findViewById(R.id.input_text);
        }
    }
}
