package com.anahit.movieplace.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anahit.movieplace.R;
import com.anahit.movieplace.models.tbIUser;
import com.anahit.movieplace.remote.IMovieAuthAPI;
import com.anahit.movieplace.remote.RetrofitClient;

import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    IMovieAuthAPI iMovieAuthAPI;
    String username, password, fullName, email, phoneNumber, dateOfBirth,repeatPassword;
    EditText usernameEditText, passwordEditText, fullNameEditText, emailEditText, phoneNumberEditText, dateOfBirthEditText,repeatPasswordEditText;
    Button register_btn;
    int gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        initValues();

        register_btn.setOnClickListener(v -> registerBtnOnClick());
    }

    private void initValues() {
        //Init API
        iMovieAuthAPI = RetrofitClient.getInstance().create(IMovieAuthAPI.class);

        usernameEditText = findViewById(R.id.reg_username);
        passwordEditText = findViewById(R.id.reg_pass);
        register_btn = findViewById(R.id.registerBtn);
        fullNameEditText = findViewById(R.id.reg_full_name);
        emailEditText = findViewById(R.id.reg_email);
        dateOfBirthEditText = findViewById(R.id.reg_birth);
        phoneNumberEditText = findViewById(R.id.reg_phone);
        repeatPasswordEditText = findViewById(R.id.reg_rep_pass);

    }

    private void registerBtnOnClick() {
        //Views
        username = usernameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        fullName = fullNameEditText.getText().toString();
        email = emailEditText.getText().toString();
        dateOfBirth = dateOfBirthEditText.getText().toString();
        phoneNumber = phoneNumberEditText.getText().toString();
        repeatPassword=repeatPasswordEditText.getText().toString();

        if (username.equals("") ||
                password.equals("") ||
                fullName.equals("") ||
                email.equals("") ||
                dateOfBirth.equals("") ||
                phoneNumber.equals("") ||
                repeatPassword.equals("") ||
                !(((RadioButton) findViewById(R.id.MaleRadioButton)).isChecked() ||
                        ((RadioButton) findViewById(R.id.OtherRadioButton)).isChecked() ||
                        ((RadioButton) findViewById(R.id.FemaleRadioButton)).isChecked())) {
            Toast.makeText(RegisterActivity.this, "All data are required.", Toast.LENGTH_LONG).show();
        } else if (!Pattern.matches("^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$", dateOfBirth)) {
            Toast.makeText(RegisterActivity.this, "Date format must be dd/mm/yyyy", Toast.LENGTH_LONG).show();
        } else if (!Pattern.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", password)) {
            Toast.makeText(RegisterActivity.this, "Password must contain at less one Upper case letter, one lower case letter, one digit number and one special symbol.", Toast.LENGTH_LONG).show();
        } else if (!Pattern.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", email)) {
            Toast.makeText(RegisterActivity.this, "Email format is invalid", Toast.LENGTH_LONG).show();
        } else if(!repeatPassword.equals(password)){
            Toast.makeText(RegisterActivity.this, "Repeat password is incorrect.", Toast.LENGTH_LONG).show();
        }else {
            final AlertDialog dialog = new SpotsDialog.Builder()
                    .setContext(RegisterActivity.this)
                    .build();
            dialog.show();
            if (((RadioButton) findViewById(R.id.MaleRadioButton)).isChecked()) {
                gender = 1;
            } else if (((RadioButton) findViewById(R.id.FemaleRadioButton)).isChecked()) {
                gender = 2;
            } else if (((RadioButton) findViewById(R.id.OtherRadioButton)).isChecked()) {
                gender = 3;
            }

            //Create User to login
            tbIUser user = new tbIUser(
                    fullName,
                    email,
                    phoneNumber,
                    dateOfBirth,
                    gender,
                    username,
                    password,
                    ""
            );

            Call<String> call = iMovieAuthAPI.registerUser(user);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(RegisterActivity.this, response.body(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            });
            dialog.dismiss();
        }
    }
}
