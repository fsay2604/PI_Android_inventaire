package com.example.pi_android_inventaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Liste_Rappots extends AppCompatActivity {

    RecyclerView recyclerView;

    String s1[], s2[],s3[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste__rappots);

        recyclerView = findViewById(R.id.recyclerView);

        s1 = getResources().getStringArray(R.array.student);
        s2 = getResources().getStringArray(R.array.cours);
        s3 = getResources().getStringArray(R.array.tele);

        MyAdapter myAdapter= new MyAdapter(this,s1,s2,s3);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}