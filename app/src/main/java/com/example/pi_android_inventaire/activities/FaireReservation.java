/****************************************
 Fichier : FaireReservation.java
 Auteur : Francois Charles Hebert
 Fonctionnalité : a-03 - Gestion des réservation
    - Faire une une réservation avec le statut en attente (pour l'utilisateur connecté)
    - Modifier une réservation en attente (de l'utilisateur connecté)
    - Supprimer une réservation en attente (de l'utilisateur connecté)

 Date : 2021-04-26

 Vérification :
 Date           Nom             Approuvé
 =========================================================


 Historique de modifications :
 Date           Nom             Description
 =========================================================

 ****************************************/

package com.example.pi_android_inventaire.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pi_android_inventaire.R;

public class FaireReservation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faire_reservation);
    }
}