package com.anahit.movieplace.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anahit.movieplace.R;
import com.anahit.movieplace.activities.MainActivity;
import com.anahit.movieplace.models.tbIUser;

public class MyPageFragment extends Fragment {

    private final tbIUser user;
    private final Context context;

    public MyPageFragment(tbIUser tbIUser,Context context) {
        this.user =tbIUser;
        this.context=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_my_page, container, false);

        //Init Values
        TextView fullName = inflate.findViewById(R.id.my_page_full_name);
        TextView dateOfBirth = inflate.findViewById(R.id.my_page_date_of_birth);
        TextView email = inflate.findViewById(R.id.my_page_email);
        TextView phoneNumber = inflate.findViewById(R.id.my_page_phone_number);
        TextView gender = inflate.findViewById(R.id.my_page_gender);
        TextView role = inflate.findViewById(R.id.my_page_role);
        View logout = inflate.findViewById(R.id.my_page_logout);

        logout.setOnClickListener(v -> {
            Intent intent = new Intent(context, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            SharedPreferences myPref = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
            boolean user = myPref.edit().remove("user").commit();
            startActivity(intent);
        });

        fullName.setText(user.getFullName());
        dateOfBirth.setText(user.getDateOfBirth().substring(0,10));
        email.setText(user.getEmail());
        phoneNumber.setText(user.getPhoneNumber());

        switch (user.getGender()){
            case 1:
                gender.setText("Male");
                break;
            case 2:
                gender.setText("Female");
                break;
            case 3:
                gender.setText("Other");
                break;
        }
        switch (user.getRole()){
            case 1:
                role.setText("Administrator");
                break;
            case 2:
                role.setText("User");
                break;
        }
        return inflate;
    }
}