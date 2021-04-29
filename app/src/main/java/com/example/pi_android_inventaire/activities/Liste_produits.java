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
package com.example.pi_android_inventaire.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.pi_android_inventaire.adapters.produit_adapter;
import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.models.Product;

import java.util.ArrayList;

public class Liste_produits extends AppCompatActivity {
    //Variables pour listes produits
    private ArrayList<Product> products;
    RecyclerView recyclerView_products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_produits);

        products = new ArrayList<Product>();

        // Remplit le array avec des donnees fictives, A changer pour avoir les donnes la BD.
        for(int i =0; i<8; i++) {
            Product p = new Product();
            p.setId(i);
            p.setNom("Un produit");
            p.setCategorie(1);
            p.setImage("allo.png");
            p.setQteDisponible(i);
            p.setDescription("Salut je suis le produit"+i);
            products.add(p);
        }

        recyclerView_products = (RecyclerView)findViewById(R.id.RecyclerView);
        produit_adapter adapter = new produit_adapter(this, products);
        recyclerView_products.setAdapter(adapter);
        recyclerView_products.setLayoutManager(new LinearLayoutManager(this));
    }

}