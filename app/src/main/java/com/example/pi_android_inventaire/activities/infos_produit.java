/****************************************
 Fichier : infos_produit.java
 Auteur : Julien Fortier
 Fonctionnalité : Affiche info sur un produit
 Date : 2021-04-26
 Vérification :
 Date                       Nom                   Approuvé
 =========================================================

 Historique de modifications :
 Date                       Nom                 Description
 =========================================================

 ****************************************/
package com.example.pi_android_inventaire.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.models.Product;

public class infos_produit extends AppCompatActivity {
    private Product produit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos_produit);

        Intent data = getIntent();

        TextView nomView = (TextView)findViewById(R.id.produit);
        TextView descriptionView = (TextView)findViewById(R.id.description);
        TextView categorieView = (TextView)findViewById(R.id.categorie);
        TextView quantiteView = (TextView) findViewById(R.id.quantite);


        produit = (Product) getIntent().getSerializableExtra("produit");

        nomView.setText(produit.getNom());
        descriptionView.setText(produit.getDescription());
        categorieView.setText(produit.getCategorie().toString());
        quantiteView.setText(produit.getQteDisponible().toString());
        //image.setImageResource(sourceImage);

        Button btn_reserver = (Button) findViewById(R.id.reserver);

        btn_reserver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(infos_produit.this, FaireReservation.class);
                intent.putExtra("product",produit);
                startActivity(intent);
            }
        });
    }
}