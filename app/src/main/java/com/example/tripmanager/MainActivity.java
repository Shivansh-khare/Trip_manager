package com.example.tripmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements TripAdapter.onclick {
    RecyclerView rv;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager myManager;
    Button btn_trip,btn_submit;
    EditText etName,et_exp;
    Set<String> trips,set;
    ArrayList<String>x;
    String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_trip = findViewById(R.id.button);
        rv=findViewById(R.id.rv_trip);
        etName=findViewById(R.id.ET_tripName);
        btn_submit = findViewById(R.id.btn_tripName);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().hide(manager.findFragmentById(R.id.fragment)).commit();

        s = getString(R.string.file);
        SharedPreferences preferences = getSharedPreferences(s,MODE_PRIVATE);
        set=new HashSet<>();
        trips = new HashSet<String>();
        trips = preferences.getStringSet("trips",set);
        x= new ArrayList<String>();
        for(String i:trips)
            x.add(i);
        myManager = new LinearLayoutManager(this);
        rv.setLayoutManager(myManager);
        myAdapter = new TripAdapter(this,x);
        rv.setAdapter(myAdapter);

        if(x.size()==0)Toast.makeText(this,"No existing trip, Create one",Toast.LENGTH_LONG).show();

        btn_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().show(manager.findFragmentById(R.id.fragment)).commit();
                btn_trip.setVisibility(View.GONE);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trip_name = etName.getText().toString().trim();
                if(trip_name.isEmpty()){
                    Toast.makeText(MainActivity.this,"Enter name for trip",Toast.LENGTH_SHORT).show();
                }
                else {
                    trips.add(trip_name);
                    x.add(trip_name);
                    SharedPreferences.Editor editor = getSharedPreferences(s,MODE_PRIVATE).edit();
                    editor.putStringSet("trips",trips);
                    editor.apply();
                    myAdapter.notifyDataSetChanged();
                    manager.beginTransaction().hide(manager.findFragmentById(R.id.fragment)).commit();
                    btn_trip.setVisibility(View.VISIBLE);
                    etName.setText("");
                }
            }
        });

    }

    @Override
    public void onselected(int i) {
        Intent in = new Intent(this,Member_Acitivity.class);
        in.putExtra("name",x.get(i));
        startActivity(in);
    }
}