package com.anahit.movieplace.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.anahit.movieplace.R;
import com.anahit.movieplace.adapters.MyCategoryAdapter;
import com.anahit.movieplace.models.Category;
import com.anahit.movieplace.models.tbIUser;
import com.anahit.movieplace.remote.ICategoryAPI;
import com.anahit.movieplace.remote.RetrofitClient;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CategoryFragment extends Fragment {

    private List<Category> categories;
    Context context;
    MyCategoryAdapter adapter;
    tbIUser user;

    public CategoryFragment(Context context,List<Category> categories,tbIUser user) {
        this.context=context;
        this.categories=categories;
        this.user=user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_category_item_list, container, false);

        view.findViewById(R.id.add_category).setOnClickListener(v -> view.findViewById(R.id.category_add_layout).setVisibility(View.VISIBLE));

        view.findViewById(R.id.add_category_add_btn).setOnClickListener(v -> {
            Category category = new Category();
            EditText categoryName = view.findViewById(R.id.add_category_name);
            category.setName(categoryName.getText().toString());
            ICategoryAPI iCategoryAPI = RetrofitClient.getInstance().create(ICategoryAPI.class);
            Call<String> call = iCategoryAPI.addCategory(category);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Category savedCategory= new Gson().fromJson(response.body(),Category.class);
                    categories.add(savedCategory);
                    adapter.notifyDataSetChanged();
                    view.findViewById(R.id.category_add_layout).setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();
                }
            });
        });
        // Set the adapter
        ListView viewById = view.findViewById(R.id.Category_listView);

         adapter = new MyCategoryAdapter(categories, context, user);
        viewById.setAdapter(adapter);
        return view;
    }
}