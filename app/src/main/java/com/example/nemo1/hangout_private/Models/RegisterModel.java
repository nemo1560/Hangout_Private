package com.example.nemo1.hangout_private.Models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.nemo1.hangout_private.Entity.eChat;
import com.example.nemo1.hangout_private.Entity.eLogin;
import com.example.nemo1.hangout_private.Interfaces.CallBack;
import com.example.nemo1.hangout_private.Interfaces.Register;
import com.example.nemo1.hangout_private.Views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pollux.widget.DualProgressView;

import java.util.Random;
import java.util.concurrent.Executor;

public class RegisterModel {
    private Register register;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DualProgressView loading;
    private ImageView back;
    private eLogin eLogin;

    public RegisterModel(Register register, DualProgressView loading, ImageView back) {
        this.register = register;
        this.loading = loading;
        this.back = back;
    }

    public void Register(final String email, String pass, final DualProgressView loading){
        loading.setVisibility(View.VISIBLE);
        loading.startAnimation();
        firebaseAuth = FirebaseAuth.getInstance();
        //create child note in db
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        //Register by interface Firebase
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //Add info's user in db
                eLogin = new eLogin(email);
                databaseReference.child(String.valueOf(eLogin.getUserName())).setValue(eLogin);
                //Check register Authentication Firebase successfully
                if(task.isSuccessful()){
                    register.onRegisterSuccess();
                    loading.stopAnimation();
                    loading.setVisibility(View.INVISIBLE);
                }
                else {
                    register.onRegisterFail();
                    loading.stopAnimation();
                    loading.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
