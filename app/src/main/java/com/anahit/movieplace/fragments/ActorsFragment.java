package com.anahit.movieplace.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.anahit.movieplace.R;
import com.anahit.movieplace.activities.AddActorActivity;
import com.anahit.movieplace.adapters.MyActorAdapter;
import com.anahit.movieplace.models.Actor;
import com.anahit.movieplace.models.tbIUser;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ActorsFragment extends Fragment {

    private static ActorsFragment INSTANCE;
    Context context;
    MyActorAdapter adapter;
    tbIUser user;
    private final List<Actor> actors;

    public ActorsFragment(Context context, List<Actor> actors, tbIUser user) {
        this.actors = actors;
        this.context = context;
        this.user = user;
    }

    public static ActorsFragment getINSTANCE() {
        return INSTANCE;
    }

    public static void setINSTANCE(ActorsFragment INSTANCE) {
        ActorsFragment.INSTANCE = INSTANCE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_actor_item_list, container, false);
        view.findViewById(R.id.add_actor).setOnClickListener(v -> startActivity(new Intent(context, AddActorActivity.class)));

        ListView viewById = view.findViewById(R.id.actor_list);

        adapter = new MyActorAdapter(actors, context, user);
        viewById.setAdapter(adapter);
        return view;
    }

    public void addActorIntoListAndNotify(Actor actor) {
        actors.add(actor);
        adapter.notifyDataSetChanged();
    }
}