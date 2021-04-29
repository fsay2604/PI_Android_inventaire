package com.example.pi_android_inventaire.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.adapters.Rapport_adapter;

public class Liste_Rappots extends AppCompatActivity {

    RecyclerView recyclerView;

    String s1[], s2[],s3[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste__rappots);

        recyclerView = findViewById(R.id.recyclerView);

        /*s1 = getResources().getStringArray(R.array.nom);
        s2 = getResources().getStringArray(R.array.etat);
        s3 = getResources().getStringArray(R.array.commentaire);*/

        Rapport_adapter myAdapter= new Rapport_adapter(this,s1,s2,s3);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}