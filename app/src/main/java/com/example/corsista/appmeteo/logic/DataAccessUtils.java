package com.example.corsista.appmeteo.logic;

import android.content.Context;

import com.example.corsista.appmeteo.data.Citta;
import com.example.corsista.appmeteo.data.MainSingleton;

import java.util.List;

/**
 * Created by Corsista on 30/03/2018.
 */

public class DataAccessUtils {

    public static List<Citta> getDataSourceItemList(Context context) {

        return MainSingleton.getInstance().getItemList();
    }

    public static List<Citta> addItemToDataSource(Context context, Citta itemToAdd) {
        List<Citta> datasource = DataAccessUtils.getDataSourceItemList(context);
        datasource.add(itemToAdd);

        MainSingleton.getInstance().setItemList(datasource);
        return datasource;
    }

    public static Citta getItemAtIndex(Context context, int index) {
        List<Citta> datasource = DataAccessUtils.getDataSourceItemList(context);
        return datasource.get(index);
    }

    public static List<Citta> removeItemAtIndex(Context context, int index) {
        List<Citta> datasource = DataAccessUtils.getDataSourceItemList(context);
        datasource.remove(index);

        MainSingleton.getInstance().setItemList(datasource);
        return datasource;
    }

    public static void initDataSource(Context context) {
        if(getDataSourceItemList(context) == null || getDataSourceItemList(context).size() == 0){
            addItemToDataSource(context, new Citta("Milano"));
            addItemToDataSource(context, new Citta("Roma"));
            addItemToDataSource(context, new Citta("Firenze"));
        }
    }
}
