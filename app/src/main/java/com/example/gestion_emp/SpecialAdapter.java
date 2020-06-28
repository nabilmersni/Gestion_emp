package com.example.gestion_emp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SpecialAdapter extends ArrayAdapter<User> {
    Context ctx;

    private TextView nameTxt,emailTxt;
    private de.hdodenhof.circleimageview.CircleImageView userImg;


    public SpecialAdapter(@NonNull Context context, @NonNull User[] objects) {
        super(context, R.layout.specialuser, objects);
        ctx = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(ctx).inflate(R.layout.specialuser,parent,false);

        userImg = convertView.findViewById(R.id.userimg);
        nameTxt = convertView.findViewById(R.id.username);
        emailTxt = convertView.findViewById(R.id.useremail);

        User currentUser = getItem(position);

        Picasso.with(ctx).load("https://ui-avatars.com/api/?name="+currentUser.getFirstname()+"+"+currentUser.getLastname()).into(userImg);
        nameTxt.setText(currentUser.getFirstname() + " "+currentUser.getLastname());
        emailTxt.setText(currentUser.getEmail());

        return convertView;
    }
}
