package com.anahit.movieplace.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.anahit.movieplace.R;
import com.anahit.movieplace.models.Actor;

import java.util.List;

public class MyMovieActorAdapter extends BaseAdapter {

    private final List<Actor> actors;
    private final Context context;

    public MyMovieActorAdapter(List<Actor> items, Context context) {
        this.context = context;
        actors = items;
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
        View item = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            item = inflater.inflate(R.layout.fragment_movie_actor_item, null);
        }
        TextView actorName = item.findViewById(R.id.actor_name);
        actorName.setText(actors.get(position).getName() + " " + actors.get(position).getSurname());

        return item;
    }
}