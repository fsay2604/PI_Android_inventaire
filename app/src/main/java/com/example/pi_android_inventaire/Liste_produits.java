/****************************************
 Fichier : Liste_produits.java
 Auteur : Julien Fortier
 Fonctionnalité : Permet d'afficher une liste de produits avec un recycler view
 Date : 2021-04-27
 Vérification :
 Date                       Nom                   Approuvé
 =========================================================

 Historique de modifications :
 Date                       Nom                 Description
 =========================================================

 ****************************************/
package com.example.pi_android_inventaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Liste_produits extends AppCompatActivity {
    //Variables pour listes produits
    private String nom[];
    private String categorie[];
    private String qte[];
    private int image[]={
            //aller chercher avec bd

    };
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_produits);

        //recyclerView = (RecyclerView)findViewById(R.id.RecyclerView);
        //nom = getResources().getStringArray(R.array.nom);
        //categorie = getResources().getStringArray(R.array.cours);
        //qte = getResources().getStringArray(R.array.telephone);

        //Adapter myAdapter = new Adapter(this,nom,categorie,qte,image);
        //recyclerView.setAdapter(myAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}