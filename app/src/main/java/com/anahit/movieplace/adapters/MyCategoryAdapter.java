package com.anahit.movieplace.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.anahit.movieplace.R;
import com.anahit.movieplace.models.Category;
import com.anahit.movieplace.models.tbIUser;
import com.anahit.movieplace.remote.ICategoryAPI;
import com.anahit.movieplace.remote.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCategoryAdapter extends BaseAdapter {

    private final List<Category> categories;
    private final Context context;
    private final tbIUser user;

    public MyCategoryAdapter(List<Category> items, Context context, tbIUser user) {
        this.context = context;
        this.user = user;
        categories = items;
    }

    @Override
    public int getCount() {
        return categories.size();
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
            item = inflater.inflate(R.layout.fragment_category_item, null);
        }
        TextView textView = item.findViewById(R.id.category_item);
        textView.setText(categories.get(position).getName());
        View deleteBtn = item.findViewById(R.id.delete_category);
        if (user.getRole() == 2) {
            deleteBtn.setVisibility(View.GONE);
        } else {
            deleteBtn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int id = categories.get(position).getId();
                    ICategoryAPI iCategoryAPI = RetrofitClient.getInstance().create(ICategoryAPI.class);
                    Call<String> callCategory = iCategoryAPI.deleteCategory(id);
                    callCategory.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                            if (response.body().equals("Category was deleted")) {
                                categories.remove(categories.get(position));
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