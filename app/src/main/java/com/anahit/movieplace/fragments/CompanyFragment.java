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
import com.anahit.movieplace.adapters.MyCompanyAdapter;
import com.anahit.movieplace.models.Company;
import com.anahit.movieplace.models.tbIUser;
import com.anahit.movieplace.remote.ICompanyAPI;
import com.anahit.movieplace.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyFragment extends Fragment {

    Context context;
    private List<Company> companies;
     MyCompanyAdapter adapter;
    tbIUser user;

    public CompanyFragment(Context context,List<Company> companies,tbIUser user) {
        this.context=context;
        this.companies=companies;
        this.user=user;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_company_item_list, container, false);

        ListView viewById = view.findViewById(R.id.Company_list);

        adapter = new MyCompanyAdapter(companies, context, user);
        viewById.setAdapter(adapter);


        view.findViewById(R.id.add_company).setOnClickListener(v -> view.findViewById(R.id.company_add_layout).setVisibility(View.VISIBLE));

        view.findViewById(R.id.add_company_add_btn).setOnClickListener(v -> {
            Company company = new Company();
            EditText companyName = view.findViewById(R.id.add_company_name);
            company.setName(companyName.getText().toString());
            ICompanyAPI iCompanyAPI = RetrofitClient.getInstance().create(ICompanyAPI.class);
            Call<String> call = iCompanyAPI.addCompany(company);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    companies.add(company);
                    adapter.notifyDataSetChanged();
                    view.findViewById(R.id.company_add_layout).setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(context,"Error",Toast.LENGTH_LONG).show();
                }
            });
        });

        return view;
    }
}