package com.example.nemo1.hangout_private.Interfaces;

import com.example.nemo1.hangout_private.Entity.eChat;
import com.example.nemo1.hangout_private.Entity.eLogin;

public interface Login {
    void onLoginSuccess(String userName);
    void onLoginFail();

}
