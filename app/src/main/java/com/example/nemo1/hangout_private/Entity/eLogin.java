package com.example.nemo1.hangout_private.Entity;

import java.io.Serializable;
import java.util.Random;

public class eLogin implements Serializable {
    private String userName;
    private String userEmail;
    private static int userId;

    public eLogin(String userName, String userEmail, int userId) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userId = userId;
    }

    public eLogin() {
    }

    public eLogin(String userEmail) {
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

    public void setUserNameByEmail(String userEmail){
        if(userEmail.contains("@")){
            int index = userEmail.indexOf("@");
            userName = userEmail.substring(0,index);
            setUserName(userName);
        }
    }

    public void setRandomId(){
        Random random = new Random();
        int Id = random.nextInt(1000);
        while (userId == Id){
            userId+=Id;
        }
        setUserId(Id);
    }
}
