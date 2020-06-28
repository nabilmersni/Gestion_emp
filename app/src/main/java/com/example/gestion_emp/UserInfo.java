package com.example.gestion_emp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class UserInfo extends AppCompatActivity {

    private TextInputLayout firstNameTxt,lastNameTxt,phoneTxt;
    private Button updateBtn, removeBtn,cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        firstNameTxt = findViewById(R.id.firstname);
        lastNameTxt = findViewById(R.id.lastname);
        phoneTxt = findViewById(R.id.phone);

        updateBtn = findViewById(R.id.update);
        removeBtn = findViewById(R.id.remove);
        cancelBtn = findViewById(R.id.cancel);

        Intent intent = getIntent();

        final String id_str = intent.getStringExtra("id");
        String first_name = intent.getStringExtra("first");
        String last_name = intent.getStringExtra("last");
        String phone = intent.getStringExtra("phone");


        firstNameTxt.getEditText().setText(first_name, TextView.BufferType.EDITABLE);
        lastNameTxt.getEditText().setText(last_name, TextView.BufferType.EDITABLE);
        phoneTxt.getEditText().setText(phone, TextView.BufferType.EDITABLE);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfo.this,MainActivity.class);
                startActivity(intent);
            }
        });

        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://backend-people-crud-app.herokuapp.com/users/"+id_str;
                Ion.with(UserInfo.this)
                        .load("DELETE",url)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (e != null){
                                    Toast.makeText(UserInfo.this, "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(UserInfo.this, ""+result.get("message"), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(UserInfo.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name = firstNameTxt.getEditText().getText().toString();
                String last_name = lastNameTxt.getEditText().getText().toString();
                String phone = phoneTxt.getEditText().getText().toString();

                String url = "https://backend-people-crud-app.herokuapp.com/users/update";

                JsonObject update_data = new JsonObject();
                update_data.addProperty("_id",id_str);
                update_data.addProperty("firstname",first_name);
                update_data.addProperty("lastname",last_name);
                update_data.addProperty("phone",phone);

                Ion.with(UserInfo.this)
                        .load(url)
                        .setJsonObjectBody(update_data)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (e != null){
                                    Toast.makeText(UserInfo.this, "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(UserInfo.this, ""+result.get("message"), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(UserInfo.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });

            }
        });

    }
}
