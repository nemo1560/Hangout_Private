package com.example.nemo1.hangout_private.Models;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nemo1.hangout_private.Entity.eChat;
import com.example.nemo1.hangout_private.Entity.eLogin;
import com.example.nemo1.hangout_private.Interfaces.Login;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pollux.widget.DualProgressView;

import java.util.ArrayList;
import java.util.List;

public class LoginModel {
    private Login login;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DualProgressView loading;
    private Button button;
    private eLogin eLogin;

    public LoginModel(Login login, DualProgressView loading, Button button) {
        this.login = login;
        this.loading = loading;
        this.button = button;
    }

    public void Login(final String email, String pass, final DualProgressView loading){
        button.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
        loading.startAnimation();
        firebaseAuth = FirebaseAuth.getInstance();
        //Read value in node "users"
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        //if login success get value
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String userKey = email.substring(0,email.indexOf("@"));
                databaseReference.child(userKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        eLogin = dataSnapshot.getValue(com.example.nemo1.hangout_private.Entity.eLogin.class);
                        login.onLoginSuccess(eLogin.getUserName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                loading.stopAnimation();
                loading.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loading.stopAnimation();
                loading.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
                login.onLoginFail();
            }
        });
    }

}
