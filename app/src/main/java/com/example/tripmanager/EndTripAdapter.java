package com.example.tripmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EndTripAdapter extends RecyclerView.Adapter<EndTripAdapter.TripViewHolder> {
    ArrayList<String> name; ArrayList<Float> integers;
    Context cn;
    float avgPrice;
    public EndTripAdapter(Context context, ArrayList<String> n, ArrayList<Float> i)
    {
        name=n;
        integers=i;
        cn=context;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item,parent,false);
        return new TripViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        holder.Tvname.setText(name.get(position));
        if(integers.get(position)>0)
        {
            holder.TVprice.setText("+"+(integers.get(position)));
            holder.TVprice.setTextColor(cn.getResources().getColor(R.color.Green));
        }
        else {
            holder.TVprice.setText(""+(integers.get(position)));
            holder.TVprice.setTextColor(cn.getResources().getColor(R.color.Red));
        }
    }

    @Override
    public int getItemCount() {
        return integers.size();
    }

    static class TripViewHolder extends RecyclerView.ViewHolder
    {

        TextView Tvname,TVprice;
        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            Tvname = itemView.findViewById(R.id.TV_name);
            TVprice = itemView.findViewById(R.id.TV_price);
        }
    }

}
