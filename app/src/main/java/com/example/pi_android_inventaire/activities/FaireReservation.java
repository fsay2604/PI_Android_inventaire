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

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import com.example.pi_android_inventaire.R;
public class FaireReservation extends AppCompatActivity implements View.OnClickListener {
    // Selection de date avec fenetre popup
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    // Boutons
    private Button btn_reserver;
    private Button btn_annuler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faire_reservation);

        // Gere la fenetre popup pour la date
        SelectDateWithPopup();
    }

    /**
     * Initialise les bouttons de cette view
     */
    private void SetupButton()
    {
        btn_reserver = (Button) findViewById(R.id.btn_faireReservation_reserve);
        btn_reserver.setText(R.string.btn_faireReservation_reserver);
        btn_reserver.setOnClickListener(this);
        btn_annuler = (Button) findViewById(R.id.btn_faireReservation_annuler);
        btn_annuler.setText(R.string.btn_faireReservation_annuler);
        btn_annuler.setOnClickListener(this);
    }

    /**
     * Fonction qui permet de selectionner une date avec une fenetre popup.
     */
    private void SelectDateWithPopup()
    {
        mDisplayDate = (TextView) findViewById(R.id.textView_faireReservation_selectDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(FaireReservation.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = Integer.toString(year) + '-' + Integer.toString(month) + '-' + Integer.toString(dayOfMonth);
                mDisplayDate.setText(date);
            }
        };
    }


    /**
     * Gere les clicks des boutons reserver/annuler de la view.
     * @param v
     */
    @Override
    public void onClick(View v) {
        // Switch case en fonction du bouton appuyer
        switch (v.getId()) {
            case R.id.btn_faireReservation_reserve:
                // Lance la requete api pour ajouter une reservation / stock dans la BD la reservation si pas de connection
                // put_in_db();
                break;
            case R.id.btn_faireReservation_annuler:
                finish(); // ferme cette activite sans sauvegarder la reservation
                break;
        }
    }
}