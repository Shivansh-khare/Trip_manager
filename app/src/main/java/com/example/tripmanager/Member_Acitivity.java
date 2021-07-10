package com.example.tripmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Member_Acitivity extends AppCompatActivity implements TripAdapter.onclick {

    RecyclerView rv,rv_exp;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager myManager;
    Button btn_trip,btn_submit,btn_expnce,btn_exp_sbt,btn_exp_choose;
    EditText etName,etPrice,etexp;
    Set<String> trips,set;
    ArrayList<String> x;
    TextView tv_name;
    String s,name;
    Boolean rv_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member__acitivity);
        name = getIntent().getStringExtra("name");

        tv_name=findViewById(R.id.tv_show_exp);
        btn_exp_sbt=findViewById(R.id.exp_submit);
        btn_exp_choose=findViewById(R.id.btn_choose);
        rv=findViewById(R.id.rv_member);
        rv_exp=findViewById(R.id.rv_exp);
        etName=findViewById(R.id.ET_memName);
        etPrice=findViewById(R.id.ET_memPrice);
        btn_submit = findViewById(R.id.btn_tripName);
        btn_trip=findViewById(R.id.button3);
        btn_expnce = findViewById(R.id.button2);
        etexp=findViewById(R.id.et_exp);
        rv_check=false;


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(name);
        actionBar.setDisplayShowTitleEnabled(true);

        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().hide(manager.findFragmentById(R.id.frag)).hide(manager.findFragmentById(R.id.fragment2)).commit();

        s = getString(R.string.file);
        SharedPreferences preferences = getSharedPreferences(s,MODE_PRIVATE);
        set=new HashSet<>();
        trips = new HashSet<String>();
        trips = preferences.getStringSet(name,set);
        x= new ArrayList<String>();
        for(String i:trips)
            x.add(i);
        myManager = new LinearLayoutManager(this);
        rv.setLayoutManager(myManager);
        rv_exp.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new TripAdapter(this,x);
        rv.setAdapter(myAdapter);
        rv_exp.setAdapter(myAdapter);


        if(x.size()==0) Toast.makeText(this,"No existing Members, Create one",Toast.LENGTH_LONG).show();
        btn_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().show(manager.findFragmentById(R.id.frag)).commit();
                btn_trip.setVisibility(View.GONE);
                btn_expnce.setVisibility(View.GONE);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trip_name = etName.getText().toString().trim();
                String price = (etPrice.getText().toString().trim());
                if(trip_name.isEmpty() || price.isEmpty()){
                    Toast.makeText(Member_Acitivity.this,"Enter name for trip",Toast.LENGTH_SHORT).show();
                }
                else {
                    trips.add(trip_name);
                    x.add(trip_name);
                    SharedPreferences.Editor editor = getSharedPreferences(s,MODE_PRIVATE).edit();
                    editor.putStringSet(name,trips);
                    editor.putInt(name+"@"+trip_name,Integer.parseInt(price));
                    editor.apply();
                    myAdapter.notifyDataSetChanged();
                    manager.beginTransaction().hide(manager.findFragmentById(R.id.frag)).commit();
                    etName.setText("");
                    etPrice.setText("");
                    btn_trip.setVisibility(View.VISIBLE);
                    btn_expnce.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_expnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.beginTransaction().show(manager.findFragmentById(R.id.fragment2)).commit();
                rv_exp.setVisibility(View.GONE);
                btn_trip.setVisibility(View.GONE);
                btn_expnce.setVisibility(View.GONE);
                tv_name.setVisibility(View.GONE);
                rv.setVisibility(View.GONE);
            }
        });
        btn_exp_sbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ADD_ON_price=etexp.getText().toString().trim();
                if(tv_name.getText().toString().equals("XYZ") || ADD_ON_price.isEmpty())Toast.makeText(Member_Acitivity.this,"please fill all details",Toast.LENGTH_SHORT).show();
                else{
                    SharedPreferences preferences = getSharedPreferences(s,MODE_PRIVATE);
                    int old= preferences.getInt(name+"@"+tv_name.getText().toString(),0);
                    SharedPreferences.Editor editor = getSharedPreferences(s,MODE_PRIVATE).edit();
                    editor.putInt(name+"@"+tv_name.getText().toString(),Integer.parseInt(ADD_ON_price)+old);
                    editor.apply();
                    manager.beginTransaction().hide(manager.findFragmentById(R.id.fragment2)).commit();
                    tv_name.setText("XYZ");
                    etexp.setText("");
                    btn_trip.setVisibility(View.VISIBLE);
                    btn_exp_choose.setVisibility(View.VISIBLE);
                    btn_expnce.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.VISIBLE);
                    tv_name.setVisibility(View.VISIBLE);
                }
            }
        });
        btn_exp_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv_exp.setVisibility(View.VISIBLE);
                rv_check=true;
            }
        });
    }

    @Override
    public void onselected(int i) {
        if(rv_check){
            tv_name.setText(x.get(i));
            rv_exp.setVisibility(View.GONE);
            tv_name.setVisibility(View.VISIBLE);
            btn_exp_choose.setVisibility(View.GONE);
            rv_check=false;
        }else {
            SharedPreferences preferences = getSharedPreferences(s,MODE_PRIVATE);
            Toast.makeText(this,"given Money :"+preferences.getInt(name+"@"+x.get(i),0),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        SharedPreferences preferences = getSharedPreferences(s,MODE_PRIVATE);
        Set<String> trip;
        trip = preferences.getStringSet("trips",set);
        trip.remove(name);
        preferences.edit().putStringSet("trips",trip).apply();
        Intent intent = new Intent(this, End_trip.class);
        intent.putExtra("name",name);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
}