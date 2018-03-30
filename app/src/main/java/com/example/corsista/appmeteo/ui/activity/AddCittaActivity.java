package com.example.corsista.appmeteo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.corsista.appmeteo.R;
import com.example.corsista.appmeteo.data.Citta;
import com.example.corsista.appmeteo.logic.DataAccessUtils;

import java.util.List;

/**
 * Created by Corsista on 30/03/2018.
 */

public class AddCittaActivity extends AppCompatActivity {
    EditText nomeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_citta);

        nomeEditText = (EditText) findViewById(R.id.edit_name);

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContatto();
            }
        });

    }


    private void saveContatto() {

        Citta newContact = new Citta(nomeEditText.getText().toString());

        // Add item to datasource
        List<Citta> updatedList = DataAccessUtils.addItemToDataSource(getApplicationContext(), newContact);
        finish();
    }
}
