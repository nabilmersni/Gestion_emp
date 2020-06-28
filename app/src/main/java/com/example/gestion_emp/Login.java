package com.example.gestion_emp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class Login extends AppCompatActivity {

    private TextInputLayout emailTxt,passwordTxt;
    private Button logBtn, registerBtn ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTxt = findViewById(R.id.email);
        passwordTxt = findViewById(R.id.password);

        logBtn = findViewById(R.id.login);
        registerBtn = findViewById(R.id.create);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_str = emailTxt.getEditText().getText().toString();
                String pass_str = passwordTxt.getEditText().getText().toString();
                if (email_str.isEmpty() || pass_str.isEmpty()){
                    Toast.makeText(Login.this, "Please complete all info !", Toast.LENGTH_SHORT).show();
                }
                else {
                    UserLogin(email_str,pass_str);
                }
            }
        });


    }

    private void UserLogin(String email_str, String pass_str) {
        String url = "https://backend-people-crud-app.herokuapp.com/users/login";

        JsonObject login_data = new JsonObject();
        login_data.addProperty("email",email_str);
        login_data.addProperty("password",pass_str);

        Ion.with(Login.this)
                .load(url)
                .setJsonObjectBody(login_data)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e != null){
                            Toast.makeText(Login.this, "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(!result.has("message")){
                                //login success
                                Intent intent = new Intent(Login.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else {
                                //login error
                                String msg = result.get("message").getAsString();
                                Toast.makeText(Login.this, "Err: " + msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
