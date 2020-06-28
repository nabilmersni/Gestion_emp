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

public class Register extends AppCompatActivity {

    private TextInputLayout firstNameTxt,lastNameTxt,emailTxt,passwordTxt,phoneTxt;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameTxt = findViewById(R.id.firstname);
        lastNameTxt = findViewById(R.id.lastname);
        emailTxt = findViewById(R.id.email);
        passwordTxt = findViewById(R.id.password);
        phoneTxt = findViewById(R.id.phone);

        registerBtn = findViewById(R.id.update);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String first_name = firstNameTxt.getEditText().getText().toString();
                String last_name = lastNameTxt.getEditText().getText().toString();
                String email = emailTxt.getEditText().getText().toString();
                String password = passwordTxt.getEditText().getText().toString();
                String phone = phoneTxt.getEditText().getText().toString();

                if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty()||password.isEmpty()||phone.isEmpty()){
                    Toast.makeText(Register.this, "Please complete all field", Toast.LENGTH_SHORT).show();
                }
                else {
                    RegisterUser(first_name,last_name,email,password,phone);
                }
            }
        });


    }

    private void RegisterUser(String first_name, String last_name, String email, String password, String phone) {
        String url = "https://backend-people-crud-app.herokuapp.com/users/register";

        JsonObject register_data = new JsonObject();
        register_data.addProperty("firstname", first_name);
        register_data.addProperty("lastname", last_name);
        register_data.addProperty("email", email);
        register_data.addProperty("password", password);
        register_data.addProperty("phone", phone);

        Ion.with(Register.this)
                .load(url)
                .setJsonObjectBody(register_data)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (e != null){
                            Toast.makeText(Register.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (!result.has("errmsg")){
                                //register success
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(Register.this, "Error: this Email already used", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
