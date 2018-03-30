package com.example.corsista.appmeteo.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.corsista.appmeteo.R;
import com.example.corsista.appmeteo.data.MainSingleton;
import com.example.corsista.appmeteo.ui.activity.MainActivity;

/**
 * Created by Corsista on 30/03/2018.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView infoText;

        public MyViewHolder(View view){
            super(view);
            infoText = (TextView) view.findViewById(R.id.info_text);
        }
    }

    @Override
    public MyRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        switch(MainActivity.mCurrentLayoutManagerType){

            case GRID_LAYOUT_MANAGER:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_grid_layout, parent, false);
                break;
            case LINEAR_LAYOUT_MANAGER:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_list_layout, parent, false);
                break;

            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_list_layout, parent, false);
        }

        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return MainSingleton.getInstance().getItemList().size();
    }

    @Override
    public void onBindViewHolder(MyRecyclerAdapter.MyViewHolder holder, int position) {
        holder.infoText.setText(MainSingleton.getInstance().getItemList().get(position).getName());
    }
}
