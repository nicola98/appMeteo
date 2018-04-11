package com.example.corsista.appmeteo.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.corsista.appmeteo.R;
import com.example.corsista.appmeteo.data.MainSingleton;
import com.example.corsista.appmeteo.logic.DataAccessUtils;
import com.example.corsista.appmeteo.logic.RecyclerItemClickListener;
import com.example.corsista.appmeteo.ui.adapter.MyRecyclerAdapter;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final int raquestCodeLocation = 1;
    private final int raquestCodeLocation2 = 2;
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    public static LayoutManagerType mCurrentLayoutManagerType;

    public enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataAccessUtils.initDataSource(getApplicationContext());
        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        myAdapter = new MyRecyclerAdapter();
        myLayoutManager = new LinearLayoutManager(this);
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, raquestCodeLocation);
        } else {
            MainSingleton.getInstance().getItemList().get(0).setName(getCurrentCityName(this));
        }

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        if(position==0)
                        {
                            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, raquestCodeLocation2);
                            } else {
                                Intent intent = new Intent(getApplicationContext(), Meteo.class);
                                intent.putExtra("position", position);
                                startActivity(intent);
                            }
                        }
                        else{
                            Intent intent = new Intent(getApplicationContext(), Meteo.class);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == raquestCodeLocation) {
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MainSingleton.getInstance().getItemList().get(0).setName(getCurrentCityName(this));
            } else{
                Toast.makeText(this, "permesso negato",
                        Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == raquestCodeLocation2) {
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MainSingleton.getInstance().getItemList().get(0).setName(getCurrentCityName(this));
                Intent intent = new Intent(getApplicationContext(), Meteo.class);
                intent.putExtra("position", 0);
                startActivity(intent);
            } else{
                Toast.makeText(this, "permesso negato",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        myAdapter.notifyDataSetChanged();
    }

    public static String getCurrentCityName(Context context){
        LocationManager locationManager=(LocationManager)context.getSystemService(LOCATION_SERVICE);
        Criteria criteria=new Criteria();
        //location manager will take the best location from the criteria
        locationManager.getBestProvider(criteria, true);

        @SuppressLint("MissingPermission") Location location=locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria,true));
        Geocoder gcd=new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        String cityName = "";
        try {
            addresses=gcd.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            if(addresses.size()>0){
                cityName = addresses.get(0).getLocality().toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cityName;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_change: {

                switch (mCurrentLayoutManagerType) {
                    case GRID_LAYOUT_MANAGER:
                        setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
                        break;
                    case LINEAR_LAYOUT_MANAGER:
                        setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
                        break;
                    default:
                        setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
                }


                return true;
            }
            case R.id.action_add:{
                Intent intent = new Intent(getApplicationContext(), AddCittaActivity.class);
                startActivity(intent);
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }


    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (myRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) myRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                myLayoutManager = new GridLayoutManager(this, 2);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                break;
            case LINEAR_LAYOUT_MANAGER:
                myLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                myLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        myAdapter = new MyRecyclerAdapter();
        myRecyclerView.setAdapter(myAdapter);

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.scrollToPosition(scrollPosition);
    }
}
