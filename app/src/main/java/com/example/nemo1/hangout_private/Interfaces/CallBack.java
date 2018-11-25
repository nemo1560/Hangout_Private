package com.example.nemo1.hangout_private.Interfaces;

import com.example.nemo1.hangout_private.Entity.eLogin;

public interface CallBack {
    void onCallBackLogout();
    void onCallBackListUser(String userName);
    void onCallBackRegister();
    void onCallBackChat(String userName,String userClient);
}
