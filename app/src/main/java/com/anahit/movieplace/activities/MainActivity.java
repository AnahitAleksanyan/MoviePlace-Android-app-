package com.anahit.movieplace.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anahit.movieplace.R;
import com.anahit.movieplace.models.tbIUser;
import com.anahit.movieplace.remote.IMovieAuthAPI;
import com.anahit.movieplace.remote.RetrofitClient;

import java.io.IOException;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {


    EditText edit_username, edit_password;
    Button login_btn;
    TextView sign_up;
    IMovieAuthAPI iMovieAuthApi;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initValues();
        checkAndRedirectToHome();

        //Event
        sign_up.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RegisterActivity.class)));

        login_btn.setOnClickListener(v -> loginOnClick());
    }

    private void initValues() {
        //Views
        edit_username = findViewById(R.id.login_username_edit_text);
        edit_password = findViewById(R.id.login_password_edit_text);
        login_btn = findViewById(R.id.login_btn);
        sign_up = findViewById(R.id.login_sign_up_text);

        //Retrofit
        retrofit = RetrofitClient.getInstance();
        iMovieAuthApi = retrofit.create(IMovieAuthAPI.class);
    }

    private void loginOnClick() {
        final AlertDialog dialog = new SpotsDialog.Builder()
                .setContext(MainActivity.this)
                .build();
        dialog.show();

        //Create User to login
        tbIUser user1 = new tbIUser(edit_username.getText().toString(),
                edit_password.getText().toString(), "");

        Call<String> call = iMovieAuthApi.loginUser(user1);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dialog.dismiss();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("user", response.body());
                if (response.code()!=200) {
                    try {
                        Toast.makeText(MainActivity.this, response.errorBody().string(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkAndRedirectToHome() {
        //Check log in or not
        SharedPreferences myPref = getSharedPreferences("myPref", MODE_PRIVATE);
        String user = myPref.getString("user", "");
        if (!user.equals("")) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }
}