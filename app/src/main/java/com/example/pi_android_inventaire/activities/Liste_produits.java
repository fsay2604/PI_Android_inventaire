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





        /**
         * TODO: Query qui va chercher all_products() plutot qu'un array de produit fictifs.
         */
        products = Product.selectProducts();


        recyclerView_products = (RecyclerView)findViewById(R.id.RecyclerView);
        produit_adapter adapter = new produit_adapter(this, products);
        recyclerView_products.setAdapter(adapter);
        recyclerView_products.setLayoutManager(new LinearLayoutManager(this));
    }

}