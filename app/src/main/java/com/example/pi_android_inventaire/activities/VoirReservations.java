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

package com.example.pi_android_inventaire.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.adapters.Reservation_adapter;
import com.example.pi_android_inventaire.models.Reservation;

import java.util.ArrayList;

public class VoirReservations extends AppCompatActivity {
    private ArrayList<Reservation> all_reservations;    // Remplir avec requetes a la BD

    private RecyclerView RecyclerView_VoirReservations;

    /**
     * Setup l'activité a sa creation
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_reservations);

        // Init du array
        all_reservations = new ArrayList<>();

        all_reservations = Reservation.get_all_reservations(MainActivity.currentUser.getId());

        // Init du recycler
        if(!all_reservations.isEmpty())
        {
            RecyclerView_VoirReservations = (RecyclerView) findViewById(R.id.Recycler_VoirReservation);
            Reservation_adapter adapter = new Reservation_adapter(this, all_reservations);
            RecyclerView_VoirReservations.setAdapter(adapter);
            RecyclerView_VoirReservations.setLayoutManager(new LinearLayoutManager(this));
        }
    }


    /**
     * Update la liste des reservations quand on revient sur la page.
     */
    @Override
    protected void onResume() {
        super.onResume();
        all_reservations = new ArrayList<Reservation>();

        all_reservations = Reservation.get_all_reservations(MainActivity.currentUser.getId());

        // Update le recyclerView
        if(!all_reservations.isEmpty())
        {
            RecyclerView_VoirReservations = (RecyclerView) findViewById(R.id.Recycler_VoirReservation);
            Reservation_adapter adapter = new Reservation_adapter(this, all_reservations);
            RecyclerView_VoirReservations.setAdapter(adapter);
            RecyclerView_VoirReservations.setLayoutManager(new LinearLayoutManager(this));
        }
    }
}