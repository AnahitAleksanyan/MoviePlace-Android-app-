package com.anahit.movieplace.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anahit.movieplace.R;
import com.anahit.movieplace.fragments.ActorsFragment;
import com.anahit.movieplace.models.Actor;
import com.anahit.movieplace.models.Country;
import com.anahit.movieplace.remote.IActorAPI;
import com.anahit.movieplace.remote.ICountryAPI;
import com.anahit.movieplace.service.APIService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActorActivity extends AppCompatActivity {

    private List<Country> countries;
    private Spinner countriesSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_actor_activity);

        initValuesAndGetData();

        findViewById(R.id.save_actor_btn).setOnClickListener(v -> saveActorOnClick());
    }

    private void closeActivity() {
        this.finish();
    }

    private void saveActorOnClick() {
        String actorName = ((TextView) findViewById(R.id.add_actor_name)).getText().toString();
        String actorSurname = ((TextView) findViewById(R.id.add_actor_surname)).getText().toString();
        String actorDateOfBirth = ((TextView) findViewById(R.id.add_actor_date_of_birth)).getText().toString();
        Country selectedCountry = (Country) countriesSpinner.getSelectedItem();
        Actor actor = new Actor();
        actor.setName(actorName);
        actor.setSurname(actorSurname);
        actor.setDateOfBirth(actorDateOfBirth);
        actor.setCountryId(selectedCountry.getId());
        actor.setCountry(selectedCountry);

        if (actorName.equals("") || actorDateOfBirth.equals("") || actorSurname.equals("")) {
            Toast.makeText(AddActorActivity.this, "All actor data are required.", Toast.LENGTH_LONG).show();
        } else if (!Pattern.matches("^(0?[1-9]|[12][0-9]|3[01])[\\/\\-](0?[1-9]|1[012])[\\/\\-]\\d{4}$",actorDateOfBirth)) {
            Toast.makeText(AddActorActivity.this, "Date format must be dd/mm/yyyy", Toast.LENGTH_LONG).show();
        } else {
            IActorAPI iActorAPI = APIService.iActorAPI;
            Call<String> callActor = iActorAPI.addActor(actor);
            callActor.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call1, Response<String> response) {
                    ActorsFragment.getINSTANCE().addActorIntoListAndNotify(actor);
                    closeActivity();
                }

                @Override
                public void onFailure(Call<String> call1, Throwable t) {
                    Toast.makeText(AddActorActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void initValuesAndGetData() {
        ICountryAPI iCountryAPI = APIService.iCountryAPI;
        final Type listType = new TypeToken<List<Country>>() {
        }.getType();

        countriesSpinner = findViewById(R.id.country_spinner);

        Call<String> callCountry = iCountryAPI.getCountry();
        callCountry.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                countries = new Gson().fromJson(response.body(), listType);
                assert countries != null;
                ArrayAdapter<Country> dataAdapter = new ArrayAdapter<>(AddActorActivity.this,
                        android.R.layout.simple_spinner_item, countries);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                countriesSpinner.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AddActorActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}