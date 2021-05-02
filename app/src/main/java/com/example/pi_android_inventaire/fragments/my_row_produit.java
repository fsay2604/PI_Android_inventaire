/****************************************
 Fichier : my_row_produit.java
 Auteur : Julien Fortier
 Fonctionnalité : Représente une ligne avec un produit
 Date : 2021-04-27
 Vérification :
 Date                       Nom                   Approuvé
 =========================================================

 Historique de modifications :
 Date                       Nom                 Description
 =========================================================
 2021-05-02         Philippe Boulanger              Oui
 2021-05-02         David Marcoux                   Oui

 ****************************************/
package com.example.pi_android_inventaire.fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pi_android_inventaire.R;

public class my_row_produit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_row_produit);
    }
}