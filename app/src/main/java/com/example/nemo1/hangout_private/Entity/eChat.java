package com.example.nemo1.hangout_private.Entity;

import java.util.Random;

public class eChat {
    public static final int IN_CHAT = 0;
    public static final int OUT_CHAT = 1;

    private String userName;
    private String userEmail;
    private static int userId;
    private String userMessage;
    private int type;

    public eChat() {
    }

    public eChat(String userName, String userEmail, int userId, String userMessage) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userId = userId;
        this.userMessage = userMessage;
    }

    public eChat(String userName, String userMessage, int type) {
        this.userName = userName;
        this.userMessage = userMessage;
        this.type = type;
    }

    public eChat(String userEmail) {
        this.userEmail = userEmail;
        setRandomId();
        setUserNameByEmail(userEmail);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public void setRandomId(){
        Random random = new Random();
        int Id = random.nextInt(1000);
        while (userId == Id){
            userId+=Id;
        }
        setUserId(Id);
    }

    public void setUserNameByEmail(String userEmail){
        if(userEmail.contentEquals("@")){
            userName = userEmail.substring(0,userEmail.indexOf("@"));
            setUserName(userName);
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
