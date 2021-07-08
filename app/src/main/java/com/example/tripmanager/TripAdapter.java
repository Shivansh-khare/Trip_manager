package com.example.tripmanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    ArrayList<String> list;
    Context cntx;
    interface onclick
    {
        void onselected(int i);
    }
    onclick pq;
    public TripAdapter(Context context,ArrayList<String> names){
        list = names;
        pq=(onclick) context;
    }
    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview,parent,false);
        return new TripViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        holder.itemView.setTag(list.get(position));
        holder.Tvname.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TripViewHolder extends RecyclerView.ViewHolder
    {

        TextView Tvname;
        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            Tvname = itemView.findViewById(R.id.listName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pq.onselected(list.indexOf(itemView.getTag()));
                }
            });
        }
    }
}
