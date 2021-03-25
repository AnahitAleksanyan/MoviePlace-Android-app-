package com.anahit.movieplace.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.anahit.movieplace.R;
import com.anahit.movieplace.models.Actor;
import com.anahit.movieplace.models.tbIUser;
import com.anahit.movieplace.remote.IActorAPI;
import com.anahit.movieplace.service.APIService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyActorAdapter extends BaseAdapter {

    private final List<Actor> actors;
    private final Context context;
    private final tbIUser user;

    public MyActorAdapter(List<Actor> items, Context context, tbIUser user) {
        this.context = context;
            actors = items;
        this.user = user;
    }

    @Override
    public int getCount() {
        return actors.size();
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
        View item=convertView;
        if(convertView ==null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item = inflater.inflate(R.layout.fragment_actor_item, null);
        }
        TextView actorName = item.findViewById(R.id.actor_name);
        actorName.setText(actors.get(position).getName());

        TextView actorSurname = item.findViewById(R.id.actor_surname);
        actorSurname.setText(actors.get(position).getSurname());

        TextView actorBirth = item.findViewById(R.id.actor_date_of_birth);
        actorBirth.setText(actors.get(position).getDateOfBirth().substring(0,10));

        TextView actorCountry = item.findViewById(R.id.actor_country);
        actorCountry.setText(actors.get(position).getCountry().getName());

        View deleteBtn = item.findViewById(R.id.delete_actor);
        if(user.getRole()==2){
            deleteBtn.setVisibility(View.GONE);
        }else {
            deleteBtn.setOnLongClickListener(v -> {
                int id = actors.get(position).getId();
                IActorAPI iActorAPI = APIService.iActorAPI;
                Call<String> callActor = iActorAPI.deleteActor(id);
                callActor.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show();
                        if (response.body().toString().equals("Actor was deleted")) {
                            actors.remove(actors.get(position));
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
                return true;
            });
        }
        return item;
    }
}