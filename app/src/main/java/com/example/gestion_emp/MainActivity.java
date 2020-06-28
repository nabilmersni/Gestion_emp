package com.example.gestion_emp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView userList;
    private User[] user_tab;
    private com.google.android.material.floatingactionbutton.FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = findViewById(R.id.userslist);
        addBtn = findViewById(R.id.add_user);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddUser.class);
                startActivity(intent);
            }
        });

        GetAllUser();


    }

    private void GetAllUser() {
        String url = "https://backend-people-crud-app.herokuapp.com/users";

        Ion.with(MainActivity.this)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, final JsonArray result) {
                        if (e != null){
                            Toast.makeText(MainActivity.this, "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Gson user_gson = new Gson();
                            user_tab = user_gson.fromJson(result.toString(),User[].class);



                            SpecialAdapter adapter = new SpecialAdapter(MainActivity.this,user_tab);
                            userList.deferNotifyDataSetChanged();
                            userList.setAdapter(adapter);

                            userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    String id_str =  result.get(position).getAsJsonObject().get("_id").getAsString();

                                    String first = user_tab[position].getFirstname();
                                    String last = user_tab[position].getLastname();

                                    String phone_str =  result.get(position).getAsJsonObject().get("phone").getAsString();

                                    Intent intent = new Intent(MainActivity.this,UserInfo.class);
                                    intent.putExtra("id",id_str);
                                    intent.putExtra("first",first);
                                    intent.putExtra("last",last);
                                    intent.putExtra("phone",phone_str);

                                    startActivity(intent);

                                }
                            });
                        }
                    }
                });

    }
}
