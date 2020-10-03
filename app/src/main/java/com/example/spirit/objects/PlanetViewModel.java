package com.example.spirit.objects;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.spirit.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlanetViewModel extends AndroidViewModel {

    private MutableLiveData<List<Planet>> planets;

    public PlanetViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Planet>> getPlanets() {
        if (planets == null) {
            planets = new MutableLiveData<>();
            loadData();
        }
        return planets;
    }

    private void loadData() {
        RequestQueue queue = Volley.newRequestQueue(getApplication().getApplicationContext());
        String url = "https://exoplanetarchive.ipac.caltech.edu/TAP/sync?query=select+pl_name,ra,dec,sy_dist,disc_year+from+ps+where+sy_dist+<+4+order+by+sy_dist&format=csv";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        planets.setValue(Utils.parsePlanetCsv(response));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            planets.setValue(new ArrayList<>());
            Log.e("PlanetViewModel", error.toString());
        });

        queue.add(stringRequest);
    }
}
