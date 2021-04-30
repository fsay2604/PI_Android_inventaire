package com.example.pi_android_inventaire.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pi_android_inventaire.R;

public class infos_Rapport extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste__rappots);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("rapport");

        String nom = intent.getStringExtra("nom");
        String cours = intent.getStringExtra("etat");
        String tele = intent.getStringExtra("commentaire");
    }
}
