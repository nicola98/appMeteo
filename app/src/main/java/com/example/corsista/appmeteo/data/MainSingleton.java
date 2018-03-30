package com.example.corsista.appmeteo.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Corsista on 30/03/2018.
 */

public class MainSingleton {

    private static MainSingleton ourInstance = new MainSingleton();

    public static MainSingleton getInstance() {
        return ourInstance;
    }

    private List<Citta> itemList;

    private MainSingleton() {
        this.itemList = new ArrayList<Citta>();
    }

    public List<Citta> getItemList() {
        return itemList;
    }

    public void setItemList(List<Citta> itemList) {
        this.itemList = itemList;
    }
}
