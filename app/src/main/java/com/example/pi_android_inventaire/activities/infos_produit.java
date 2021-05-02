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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos_produit);

        Intent data = getIntent();

        TextView nomView = (TextView)findViewById(R.id.produit);
        TextView descriptionView = (TextView)findViewById(R.id.description);
        TextView categorieView = (TextView)findViewById(R.id.categorie);
        TextView quantiteView = (TextView) findViewById(R.id.quantite);
        //ImageView image = (ImageView) findViewById(R.id.imageView);

        String nom = data.getStringExtra("nom");
        String description = data.getStringExtra("description");
        String categorie = data.getStringExtra("categorie");
        String qte = data.getStringExtra("qte");
        String id = data.getStringExtra("id");
        //int sourceImage = data.getIntExtra("image",0);

        Product product = new Product(Integer.parseInt(id),Integer.parseInt(categorie),nom,description,"",Integer.parseInt(qte),"test.png");
        nomView.setText(nom);
        descriptionView.setText(description);
        categorieView.setText(categorie);
        quantiteView.setText(qte);
        //image.setImageResource(sourceImage);

        Button btn_reserver = (Button) findViewById(R.id.reserver);

        btn_reserver.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(infos_produit.this, FaireReservation.class);
                intent.putExtra("product",product);
                startActivity(intent);
            }
        });
    }
}