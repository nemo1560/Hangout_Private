package com.example.nemo1.hangout_private.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nemo1.hangout_private.Interfaces.CallBack;
import com.example.nemo1.hangout_private.R;

import java.util.List;

public class UsersAdapter extends ArrayAdapter<String> {
    private List<String> users;
    private int resource;
    private Context context;
    private String userClient;
    private CallBack callBack;
    private String userName;
    private ViewHoler viewHolder;

    public UsersAdapter(@NonNull Context context, int resource, @NonNull List<String> objects,String userName,CallBack callBack) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.users = objects;
        this.userName = userName;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(resource,parent,false);
        viewHolder = new ViewHoler();
        viewHolder.user = convertView.findViewById(R.id.users);
        initEvent(position);
        return convertView;
    }

    private void initEvent(final int position) {
        userClient = users.get(position);
        viewHolder.user.setText(userClient);
        viewHolder.user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onCallBackChat(userName,users.get(position));
                Log.d("checkuser",users.get(position));
            }
        });
    }

    static class ViewHoler{
        private TextView user;
    }
}

