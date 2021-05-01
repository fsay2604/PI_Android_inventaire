/****************************************
 Fichier : ReservationIndex.java
 Auteur : Francois Charles Hebert
 Fonctionnalité : a-03 - Gestion des réservation
    - Faire l'interface avec les boutons pour:
        1. Voir les reservations de l'utilisateur (amene sur une page avec un recyclerView qui listera toutes les reservations. En appuyant sur une r/servation, on pourra la supprimer ou la modifier si elle est encore en attente.)
        2. Faire une Reservation (Amene vers un formulaire pour faire une reservation)

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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * SERA DESUET, LA PAGE FAIRERESERVATION DOIT ETRE APPELER AVEC LE BOuTON SuR LA PAGE "DETAIL PRODUIT"
 * Donc, le bouton du menu principale "Reservatin" amenera sur la page Voir les reservation. Chaque element sera clicakble pour amener vers la page Modifier Reservation (avec un bouton supprimer si l'etat est encore en attnte).
 */
import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.activities.FaireReservation;

public class Reservation_index extends AppCompatActivity   implements View.OnClickListener{

    // Boutons
    private Button btn_faireReservation;
    private Button btn_voirReservation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_index);

        // Init. les boutons de la view.
        setupBtn();
    }

    /**
     * Fonction qui initialise les boutons du menu afin de rediriger vers les bonnes pages
     */
    private void setupBtn() {
        // Recuperation des boutons
        btn_faireReservation = (Button) findViewById(R.id.btn_faireReservation);
        btn_faireReservation.setOnClickListener(this);

        btn_voirReservation = (Button) findViewById(R.id.btn_voirReservation);
        btn_voirReservation.setOnClickListener(this);
    }
    @Override
    public void onClick(View v)
    {
        // Switch case en fonction du bouton appuyer
        switch (v.getId()) {
            case R.id.btn_faireReservation:
                // redirection a la page liste produit
                Intent intentListeProduits = new Intent(this,FaireReservation.class);
                startActivity(intentListeProduits);
                break;
            case R.id.btn_voirReservation:
                // redirection vers la page pour faire une reservation
                Intent intentReservation = new Intent(this, VoirReservations.class);
                startActivity(intentReservation);
                break;
        }
    }
}