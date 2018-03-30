package com.example.corsista.appmeteo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.corsista.appmeteo.R;
import com.example.corsista.appmeteo.data.GsonRequest;
import com.example.corsista.appmeteo.data.MainSingleton;
import com.example.corsista.appmeteo.data.ServiceQueueSingleton;
import com.example.corsista.appmeteo.data.WeatherOutput;

import org.json.JSONObject;

/**
 * Created by Corsista on 30/03/2018.
 */

public class Meteo  extends AppCompatActivity {

    TextView id_view;
    TextView name_view;
    TextView description_view;
    TextView tempo_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meteo_layout);

        id_view = (TextView) findViewById(R.id.id);
        name_view = (TextView) findViewById(R.id.nome);
        description_view = (TextView) findViewById(R.id.descrizione);
        tempo_view = (TextView) findViewById(R.id.tempo);
        Intent intent = getIntent();
        final int dato = intent.getIntExtra("position", 0);
        /*JsonObjectRequest jsonObjectReq = new JsonObjectRequest("http://api.openweathermap.org/data/2.5/weather?q="+ MainSingleton.getInstance().getItemList().get(dato).getName() +"&appid=2439d518e81cee90fd7a61cfe1109dd4", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("SERVICE", "Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
            }
        });
        ServiceQueueSingleton.getInstance(this).addToRequestQueue(jsonObjectReq);*/
        GsonRequest jsonObjectReq = new GsonRequest("http://api.openweathermap.org/data/2.5/weather?q="+ MainSingleton.getInstance().getItemList().get(dato).getName() +"&appid=2439d518e81cee90fd7a61cfe1109dd4",
                WeatherOutput.class, null,
                new Response.Listener<WeatherOutput>() {
                    @Override
                    public void onResponse(WeatherOutput response) {
                        Log.d("SERVICE", "Response: " + response.toString());
                        id_view.setText(response.getId());
                        name_view.setText(response.getName());
                        description_view.setText(response.getWeather().get(0).getDescription());
                        tempo_view.setText(response.getWeather().get(0).getMain());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("VOLLEY", "Error: " + error.getMessage());
            }
        });

        ServiceQueueSingleton.getInstance(this).addToRequestQueue(jsonObjectReq);


        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
