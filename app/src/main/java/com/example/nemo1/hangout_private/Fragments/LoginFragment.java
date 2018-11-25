package com.example.nemo1.hangout_private.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.nemo1.hangout_private.Entity.eChat;
import com.example.nemo1.hangout_private.Entity.eLogin;
import com.example.nemo1.hangout_private.Interfaces.CallBack;
import com.example.nemo1.hangout_private.Interfaces.Login;
import com.example.nemo1.hangout_private.Interfaces.Register;
import com.example.nemo1.hangout_private.Models.LoginModel;
import com.example.nemo1.hangout_private.Models.RegisterModel;
import com.example.nemo1.hangout_private.R;
import com.google.firebase.auth.FirebaseAuth;
import com.pollux.widget.DualProgressView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends Fragment implements View.OnClickListener,Login,Register {
    private CallBack callBack;
    @BindView(R.id.user_input)EditText user_input;
    @BindView(R.id.pass_input)EditText pass_input;
    @BindView(R.id.loading)DualProgressView loading;
    @BindView(R.id.user_register_input)EditText user_register_input;
    @BindView(R.id.pass_register_input)EditText pass_register_input;
    @BindView(R.id.login)Button login;
    @BindView(R.id.register)Button register;
    @BindView(R.id.submit)Button submit;
    @BindView(R.id.back)ImageView back;
    private LinearLayout login_layout;
    private LinearLayout register_layout;
    private String email ="";
    private String pass ="";
    private LoginModel loginModel;
    private RegisterModel registerModel;
    private static int index = 0;

    //Instance fragment
    public static LoginFragment newInstance(int index){
        LoginFragment loginFragment = new LoginFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        loginFragment.setArguments(bundle);
        return loginFragment;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public void onAttach(Context context ) {
        super.onAttach(context);
        this.callBack = (CallBack) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);
        ButterKnife.bind(this,view);
        login_layout = view.findViewById(R.id.login_layout);
        register_layout = view.findViewById(R.id.register_layout);
        login_layout.setVisibility(View.VISIBLE);
        register_layout.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.INVISIBLE);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        submit.setOnClickListener(this);
        back.setOnClickListener(this);
        user_input.setText("");
        pass_input.setText("");
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == login.getId()){
            email = user_input.getText().toString();
            pass = pass_input.getText().toString();
            if(!email.isEmpty()&&Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                if(!pass.isEmpty()){
                    login(email,pass);
                }
                else {
                    pass_input.setHint("Nhập thông tin");
                }
            }
            else {
                user_input.setHint("Nhập thông tin");
            }
        }
        else if (v.getId() == register.getId()){
            login_layout.setVisibility(View.INVISIBLE);
            register_layout.setVisibility(View.VISIBLE);
        }
        else if(v.getId() == back.getId()){
            login_layout.setVisibility(View.VISIBLE);
            register_layout.setVisibility(View.INVISIBLE);
        }
        else if (v.getId() == submit.getId()){
            email = user_register_input.getText().toString();
            pass = pass_register_input.getText().toString();
            if(!email.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                if(!pass.isEmpty()){
                    register(email,pass);
                }
                else {
                    Toast.makeText(getActivity(),"Nhập thông tin password",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(getActivity(),"Nhập thông tin email",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void login(String email, String pass) {
        loginModel = new LoginModel(LoginFragment.this,loading,login);
        loginModel.Login(email,pass,loading);
    }

    private void register(String email, String pass){
        registerModel = new RegisterModel(LoginFragment.this,loading,back);
        registerModel.Register(email,pass,loading);
    }

    @Override
    public void onLoginSuccess(String userName) {
        Toast.makeText(getActivity(),"Xin chào "+userName,Toast.LENGTH_SHORT).show();
        callBack.onCallBackListUser(userName);
    }

    @Override
    public void onLoginFail() {
        index++;
        callBack.onCallBackRegister();
        if(index == 2){
            user_input.setText("");
            pass_input.setText("");
        }
    }

    @Override
    public void onRegisterSuccess() {
        login_layout.setVisibility(View.VISIBLE);
        register_layout.setVisibility(View.INVISIBLE);
        Toast.makeText(getActivity(),"Đăng ký thành công",Toast.LENGTH_SHORT).show();
        user_register_input.setText("");
        pass_register_input.setText("");
    }

    @Override
    public void onRegisterFail() {
        Toast.makeText(getActivity(),"Lổi đăng ký",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callBack = null;
    }
}
