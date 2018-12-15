package com.example.nemo1.hangout_private.Models;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.nemo1.hangout_private.Entity.eChat;
import com.example.nemo1.hangout_private.Entity.eLogin;
import com.example.nemo1.hangout_private.Interfaces.Chat;
import com.example.nemo1.hangout_private.Interfaces.Login;
import com.example.nemo1.hangout_private.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatModel {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Chat chat;
    private Context context;
    private List<eChat> stringList = new ArrayList<>();
    private NotificationManager notificationManager;
    private NotificationChannel notificationChannel;
    private Notification.Builder notification;
    private NotificationCompat.Builder notificationCompat;
    private final String CHANNEL_ID = "Hangout_1";
    private final String CHANNEL_NAME = "Hangout";
    private int NOTIFICATION_ID = 1;

    public ChatModel(Context context,Chat chat) {
        this.chat = chat;
        this.context = context;
    }

    public void writeDatabase(final String content, final String userName,String userClient){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("chats").child(userName+"_"+userClient).push();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                databaseReference.setValue(content);
                stringList.add(new eChat("me",content,1));
                chat.onSendContent(stringList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void readDataBase(final String userName, final String userClient){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("chats").child(userClient+"_"+userName);
        databaseReference.orderByChild(databaseReference.getKey()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String content = dataSnapshot.getValue(String.class);
                stringList.add(new eChat(userClient,content,0));
                createNotification(content);
                chat.onRecieverContent(stringList);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createNotification(String content) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            notificationManager.createNotificationChannel(notificationChannel);

            notification = new Notification.Builder(context, CHANNEL_ID)
                    .setContentTitle("Hangout_Private")
                    .setContentText(content)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setChannelId(CHANNEL_ID);
            notificationManager.notify(NOTIFICATION_ID,notification.build());
        }
        else {
            notificationCompat = new NotificationCompat.Builder(context);
            notificationCompat.setContentTitle("Weather")
                    .setContentText(content)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setAutoCancel(true);
            notificationManager.notify(NOTIFICATION_ID,notificationCompat.build());
        }
    }

}
