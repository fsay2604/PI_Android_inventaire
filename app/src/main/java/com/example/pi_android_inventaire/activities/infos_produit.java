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

import android.os.Bundle;

import com.example.pi_android_inventaire.R;

public class infos_produit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos_produit);
    }
}