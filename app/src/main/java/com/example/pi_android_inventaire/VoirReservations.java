/****************************************
 Fichier : VoirReservation.java
 Auteur : Francois Charles Hebert
 Fonctionnalité : a-03 - Gestion des réservation
    - Liste l'ensemble des reservation de l'utilisateur connecté.
    - Permet d'accéder aux pages modifier/canceler des reservation qui sont encore en attente.

 Date : 2021-04-27

 Vérification :
 Date           Nom             Approuvé
 =========================================================


 Historique de modifications :
 Date           Nom             Description
 =========================================================

 ****************************************/

package com.example.pi_android_inventaire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class VoirReservations extends AppCompatActivity {
    private ArrayList<Reservation> all_reservations;    // Remplir avec requetes a la BD

    private RecyclerView RecyclerView_VoirReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_reservations);

        // Init du array
        all_reservations = new ArrayList<Reservation>();

        // Remplit le array avec des donnees fictives, A changer pour avoir les donnes la BD.
        for(int i =0; i<10; i++) {
           Reservation r = new Reservation();
           r.setId(i);
           r.setEtat_id(1);
           r.setNumero_utilisateur(123);
           r.setProduit_id(10);
           r.setQuantite(25);
           r.setDate_retour_prevue("2021-05-26");
           r.setDate_retour_reel("2021-05-26");
           all_reservations.add(r);
        }

        // Init du recycler
        RecyclerView_VoirReservations = (RecyclerView) findViewById(R.id.Recycler_VoirReservation);
        Reservation_adapter adapter = new Reservation_adapter(this, all_reservations);
        RecyclerView_VoirReservations.setAdapter(adapter);
        RecyclerView_VoirReservations.setLayoutManager(new LinearLayoutManager(this));

    }
}