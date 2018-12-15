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

public class ChatAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<eChat> eChatList;
    private int index;
    private View view;
    private int total_types;

    //Update multiviewtype adapter RecyclerView
    public ChatAdapter(Context context, List<eChat> eChatList) {
        this.context = context;
        this.eChatList = eChatList;
        this.index = index;
        this.total_types = eChatList.size();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case eChat.IN_CHAT:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_chat_input, viewGroup, false);
                return new LayoutInput(view);

            case eChat.OUT_CHAT:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_chat_ouput, viewGroup, false);
                return new LayoutOutput(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder myViewHolder, int i) {
        eChat eChat = eChatList.get(i);
        switch (eChat.getType()) {
            case com.example.nemo1.hangout_private.Entity.eChat.IN_CHAT:
                ((LayoutInput) myViewHolder).user_input.setText(eChat.getUserName());
                ((LayoutInput) myViewHolder).input_text.setText(eChat.getUserMessage());
                break;
            case com.example.nemo1.hangout_private.Entity.eChat.OUT_CHAT:
                ((LayoutOutput) myViewHolder).user_output.setText(eChat.getUserName());
                ((LayoutOutput) myViewHolder).output_text.setText(eChat.getUserMessage());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return eChatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (eChatList.get(position).getType()) {
            case 0:
                return eChat.IN_CHAT;
            case 1:
                return eChat.OUT_CHAT;
            default:
                return -1;
        }
    }

    public class LayoutInput extends RecyclerView.ViewHolder {
        private TextView user_input,input_text;

        public LayoutInput(@NonNull View itemView) {
            super(itemView);
            user_input = itemView.findViewById(R.id.user_input);
            input_text = itemView.findViewById(R.id.input_text);
        }
    }

    public class LayoutOutput extends RecyclerView.ViewHolder{
        private TextView user_output,output_text;

        public LayoutOutput(@NonNull View itemView) {
            super(itemView);
            user_output = itemView.findViewById(R.id.user_output);
            output_text = itemView.findViewById(R.id.output_text);
        }
    }
}
