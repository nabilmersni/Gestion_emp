package com.example.gestion_emp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class AddUser extends AppCompatActivity {

    private TextInputLayout firstNameTxt,lastNameTxt,phoneTxt;
    private Button addBtn, cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        firstNameTxt = findViewById(R.id.firstname);
        lastNameTxt = findViewById(R.id.lastname);
        phoneTxt = findViewById(R.id.phone);

        addBtn = findViewById(R.id.update);
        cancelBtn = findViewById(R.id.remove);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(AddUser.this,MainActivity.class);
                startActivity(intent);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String first_name = firstNameTxt.getEditText().getText().toString();
                String last_name = lastNameTxt.getEditText().getText().toString();
                String phone = phoneTxt.getEditText().getText().toString();

                if (first_name.isEmpty() || last_name.isEmpty() ||phone.isEmpty()){
                    Toast.makeText(AddUser.this, "Please complete all field", Toast.LENGTH_SHORT).show();
                }
                else {
                    AddUserFn(first_name,last_name,phone);
                }
            }
        });



    }

    private void AddUserFn(String first_name, String last_name, String phone) {
        String url = "https://backend-people-crud-app.herokuapp.com/users/add";

        JsonObject user_add = new JsonObject();
        user_add.addProperty("firstname",first_name);
        user_add.addProperty("lastname",last_name);
        user_add.addProperty("phone",phone);

        Ion.with(AddUser.this)
                .load(url)
                .setJsonObjectBody(user_add)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null){
                            Toast.makeText(AddUser.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String msg = result.get("message").getAsString();
                            Toast.makeText(AddUser.this, msg, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddUser.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }
}
