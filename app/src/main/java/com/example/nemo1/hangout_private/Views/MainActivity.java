package com.example.nemo1.hangout_private.Views;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.nemo1.hangout_private.Entity.eLogin;
import com.example.nemo1.hangout_private.Fragments.ChatFragment;
import com.example.nemo1.hangout_private.Fragments.LoginFragment;
import com.example.nemo1.hangout_private.Fragments.UserFragment;
import com.example.nemo1.hangout_private.Interfaces.CallBack;
import com.example.nemo1.hangout_private.R;

public class MainActivity extends AppCompatActivity implements CallBack {
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;
    private static final int REQUEST_LOCATION_PERMISSION = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_layout, new LoginFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onCallBackLogout() {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_layout, new LoginFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onCallBackListUser(String userName) {
        Bundle bundle = new Bundle();
        fragment = new UserFragment();
        bundle.putString("userName",userName);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_layout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onCallBackRegister() {
        Toast.makeText(this,"Mời đăng ký thông tin",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCallBackChat(String userName, String userClient) {
        Bundle bundle = new Bundle();
        fragment = new ChatFragment();
        bundle.putString("userClient",userClient);
        bundle.putString("userName",userName);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //Kiem tra permission yeu cau
    public void checkPermission(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode) {
                case REQUEST_LOCATION_PERMISSION:
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    } else {
                        checkPermission();
                    }
                    break;
                default:
                    break;
            }
    }
}
