package com.example.nemo1.hangout_private.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.nemo1.hangout_private.Adapters.UsersAdapter;
import com.example.nemo1.hangout_private.Interfaces.CallBack;
import com.example.nemo1.hangout_private.Interfaces.Users;
import com.example.nemo1.hangout_private.Models.UsersModel;
import com.example.nemo1.hangout_private.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFragment extends Fragment implements Users{
    private CallBack callBack;
    private UsersModel usersModel;
    private UsersAdapter usersAdapter;
    private String userName;
    @BindView(R.id.listUsers)ListView listUsers;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.callBack = (CallBack) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users,container,false);
        ButterKnife.bind(this,view);
        getUsers();
        return view;
    }

    public void getUsers(){
        usersModel = new UsersModel(UserFragment.this);
        usersModel.getListUser();
    }

    @Override
    public void getListUsers(List<String> stringList) {
        if(getArguments() != null){
            userName = getArguments().getString("userName","");
        }
        usersAdapter = new UsersAdapter(getActivity(),R.layout.layout_users,stringList,userName,callBack);
        listUsers.setAdapter(usersAdapter);
    }
}
