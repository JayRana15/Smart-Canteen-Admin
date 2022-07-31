package com.example.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Controller.Controller;
import ResponseModels.ResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        EditText name,pass;
        Button verifyBtn;

        name = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        verifyBtn = findViewById(R.id.verifyButton);

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = name.getText().toString();
                String password = pass.getText().toString();

                if (username.equals("") || password.equals("")){
                    Toast.makeText(LogInActivity.this, "Enter Data", Toast.LENGTH_SHORT).show();
                }
                else if (password.length() != 8){
                    Toast.makeText(LogInActivity.this, "Enter 8 length password", Toast.LENGTH_SHORT).show();
                }
                else {
                    checkUser(username,password);
                }
            }
        });

    }

    void checkUser(String username,String password){
        Log.d("username",username);
        Log.d("password",password);

        Call<ResponseModel> call = Controller.getInstance().getAPI().logIn(username, password);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel obj = response.body();
                int id = obj.getAdmin_id();

                if (id != 100){
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sp.edit().putInt("adminId",id).apply();

                    Log.d("signIn","success");

                    startActivity(new Intent(LogInActivity.this,DashBoardActivity.class));
                    finish();

                }  else {
                    Toast.makeText(LogInActivity.this, "Sign In failed", Toast.LENGTH_SHORT).show();
                    Log.e("signIn","failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("signInError",t.getMessage());
            }
        });

    }

}