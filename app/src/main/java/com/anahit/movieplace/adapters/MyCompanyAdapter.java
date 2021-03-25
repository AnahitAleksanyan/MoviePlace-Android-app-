package com.anahit.movieplace.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.anahit.movieplace.R;
import com.anahit.movieplace.models.Company;
import com.anahit.movieplace.models.tbIUser;
import com.anahit.movieplace.remote.ICompanyAPI;
import com.anahit.movieplace.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCompanyAdapter extends BaseAdapter {

    private final List<Company> companies;
    private final Context context;
    private final tbIUser user;

    public MyCompanyAdapter(List<Company> items, Context context, tbIUser user) {
        this.context = context;
        this.user = user;
        companies = items;
    }

    @Override
    public int getCount() {
        return companies.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item = inflater.inflate(R.layout.fragment_company_item, null);
        }
        TextView textView = item.findViewById(R.id.company_item);
        textView.setText(companies.get(position).getName());
        View deleteBtn = item.findViewById(R.id.delete_company);
        if (user.getRole() == 2) {
            deleteBtn.setVisibility(View.GONE);
        } else {
            deleteBtn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int id = companies.get(position).getId();
                    ICompanyAPI iCompanyAPI = RetrofitClient.getInstance().create(ICompanyAPI.class);
                    Call<String> callCompany = iCompanyAPI.deleteCompany(id);
                    callCompany.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                            if (response.body().equals("Company was deleted")) {
                                companies.remove(companies.get(position));
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return true;
                }
            });
        }
        return item;
    }
}