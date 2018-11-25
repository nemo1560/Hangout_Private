package com.example.nemo1.hangout_private.Models;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.nemo1.hangout_private.Interfaces.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersModel {
    private DatabaseReference databaseReference;
    private List<String> stringList;
    private Users users;

    public UsersModel(Users users) {
        this.users = users;
    }
    //set list username from DB so set action click to chat.
    public void getListUser(){
        stringList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("");
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot getUsers:dataSnapshot.getChildren()){
                    //add in List
                    stringList.add((String) getUsers.child("userName").getValue());
                    //add in interface
                    users.getListUsers(stringList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
