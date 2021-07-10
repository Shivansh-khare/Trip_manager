package com.example.tripmanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class End_trip extends AppCompatActivity {
    String name,s;
    RecyclerView rv,rv_toGive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_trip);
        rv = findViewById(R.id.rv_endTrip);
        rv_toGive = findViewById(R.id.rv_endTrip_to_give);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Welcome Home");
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        name = getIntent().getStringExtra("name");

        s = getString(R.string.file);

        SharedPreferences preferences = getSharedPreferences(s,MODE_PRIVATE);
        Set<String> trips= new HashSet<String>() ;
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> prices = new ArrayList<>();
        int Total=0;
        trips = preferences.getStringSet(name,trips);
        for(String i:trips) {
            names.add(i);
            prices.add( preferences.getInt(name + "@" + i, 0));
            Total += preferences.getInt(name + "@" + i, 0);
            preferences.edit().remove(name + "@" + i).apply();
        }
        float avg =  ((float)Total)/prices.size();
        ArrayList<Float> result = new ArrayList<>();
        ArrayList<Float> result2 = new ArrayList<>();
        ArrayList<Float> Transactions = new ArrayList<>();
        ArrayList<String> Transactions_name = new ArrayList<>();
        for(int i=0;i<prices.size();i++)
        {
            result.add((float)prices.get(i)-avg);
            result2.add((float)prices.get(i)-avg);
        }
        for(int i=0;i<result2.size();i++)
        {
            if(result2.get(i)<0)
            {
                for(int j=0;j<result2.size();j++)
                {
                    if(result2.get(j)>0)
                    {
                        Transactions_name.add(names.get(i)+" to "+names.get(j));
                        if(-1*result2.get(i)>result2.get(j)){
                            result2.set(i,result2.get(j)+result2.get(i));
                            Transactions.add(result2.get(j));
                            result2.set(j,0f);
                            continue;
                        }
                        else{
                            result2.set(j,result2.get(j)+result2.get(i));
                            Transactions.add(result2.get(i)*-1);
                            result2.set(i,0f);
                            break;
                        }

                    }
                }
            }
        }
        preferences.edit().remove(name).apply();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        rv.setAdapter(new EndTripAdapter(this,names,result));

        rv_toGive.setLayoutManager(new LinearLayoutManager(this));
        rv_toGive.setHasFixedSize(true);
        rv_toGive.setAdapter(new EndTripAdapter(this,Transactions_name,Transactions));

    }
}