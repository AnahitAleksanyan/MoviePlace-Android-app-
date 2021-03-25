package com.anahit.movieplace.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.anahit.movieplace.R;
import com.anahit.movieplace.activities.HomeActivity;
import com.anahit.movieplace.models.Actor;
import com.anahit.movieplace.models.Category;
import com.anahit.movieplace.models.Company;
import com.anahit.movieplace.models.tbIUser;
import com.anahit.movieplace.remote.IActorAPI;
import com.anahit.movieplace.remote.ICategoryAPI;
import com.anahit.movieplace.remote.ICompanyAPI;
import com.anahit.movieplace.remote.RetrofitClient;
import com.anahit.movieplace.service.APIService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SetupFragment extends Fragment {


    Context context;
    tbIUser user;

    public SetupFragment(Context context,tbIUser user) {
        this.context=context;
        this.user=user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_setup, container, false);

        view.findViewById(R.id.setup_actors).setOnClickListener(new View.OnClickListener() {
            List<Actor> actors;
            @Override
            public void onClick(View v) {
                IActorAPI iActorAPI = APIService.iActorAPI;
                final Type listType = new TypeToken<List<Actor>>() {}.getType();

                Call<String> call = iActorAPI.getActor();
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        actors= new Gson().fromJson(response.body(),listType);
                        ActorsFragment fragment = new ActorsFragment(context, actors,user);
                        ActorsFragment.setINSTANCE(fragment);
                        ((HomeActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        view.findViewById(R.id.setup_categories).setOnClickListener(new View.OnClickListener() {
            List<Category> categories;
            @Override
            public void onClick(View v) {
                ICategoryAPI iCategoryAPI = RetrofitClient.getInstance().create(ICategoryAPI.class);
                final Type listType = new TypeToken<List<Category>>() {}.getType();

                Call<String> call = iCategoryAPI.getCategory();
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        categories= new Gson().fromJson(response.body(),listType);
                        ((HomeActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, new CategoryFragment(context,categories,user)).commit();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        view.findViewById(R.id.setup_companies).setOnClickListener(new View.OnClickListener() {
            List<Company> companies;
            @Override
            public void onClick(View v) {
                    ICompanyAPI iCompanyAPI = RetrofitClient.getInstance().create(ICompanyAPI.class);
                    final Type listType = new TypeToken<List<Company>>() {}.getType();

                    Call<String> call = iCompanyAPI.getCompany();
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            companies= new Gson().fromJson(response.body(),listType);
                            ((HomeActivity)getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.container, new CompanyFragment(context,companies,user)).commit();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();
                        }
                    });
            }
        });
        return view;
    }
}