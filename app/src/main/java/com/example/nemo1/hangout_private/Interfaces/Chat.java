package com.example.nemo1.hangout_private.Interfaces;

import com.example.nemo1.hangout_private.Entity.eChat;

import java.util.List;

public interface Chat {
    void onSendContent(List<eChat> chats, int send);
    void onRecieverContent(List<eChat> chats,int receive);
}
