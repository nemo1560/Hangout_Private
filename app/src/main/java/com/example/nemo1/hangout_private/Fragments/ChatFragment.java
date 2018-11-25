package com.example.nemo1.hangout_private.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nemo1.hangout_private.Adapters.ChatInputAdapter;
import com.example.nemo1.hangout_private.Adapters.ChatOutputAdapter;
import com.example.nemo1.hangout_private.Entity.eChat;
import com.example.nemo1.hangout_private.Interfaces.CallBack;
import com.example.nemo1.hangout_private.Interfaces.Chat;
import com.example.nemo1.hangout_private.Models.ChatModel;
import com.example.nemo1.hangout_private.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatFragment extends Fragment implements View.OnClickListener, Chat {
    private CallBack callBack;
    @BindView(R.id.chat_output)RecyclerView chat_output;
    @BindView(R.id.chat_input)EditText chat_input;
    @BindView(R.id.send)Button send;
    private ChatOutputAdapter chatOutputAdapter;
    private ChatInputAdapter chatInputAdapter;
    private ChatModel chatModel;
    private LinearLayoutManager linearLayoutManager;
    private String userName,
            userClient,
            content;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.callBack = (CallBack) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        ButterKnife.bind(this,view);
        chatModel = new ChatModel(getActivity(),ChatFragment.this);
        initEvent();
        if(getArguments() != null){
           userName = getArguments().getString("userName","");
           userClient = getArguments().getString("userClient","");
        }
        chatModel.readDataBase(userName,userClient);
        return view;
    }

    private void initEvent() {
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        content = chat_input.getText().toString();
        chatModel.writeDatabase(content,userName,userClient);
        chat_input.setText("");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callBack = null;
    }

    @Override
    public void onSendContent(List<eChat> chats, int send) {
        chatOutputAdapter = new ChatOutputAdapter(getActivity(),chats,send);
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        chat_output.setAdapter(chatOutputAdapter);
        chat_output.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onRecieverContent(List<eChat> chats, int receive) {

    }
}
