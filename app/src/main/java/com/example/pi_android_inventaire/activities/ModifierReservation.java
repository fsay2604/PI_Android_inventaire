/****************************************
 Fichier : ModifierReservation.java
 Auteur : Francois Charles Hebert
 Fonctionnalité : a-03 - Gestion des réservation
 - Modifier une réservation en attente (de l'utilisateur connecté)
 - Supprimer une réservation en attente (de l'utilisateur connecté)

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

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.models.Reservation;

import org.w3c.dom.Text;

import java.util.Calendar;

public class ModifierReservation extends AppCompatActivity implements View.OnClickListener{
    // Selection de date avec fenetre popup
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    // Boutons
    private Button btn_modifier;
    private Button btn_annuler;

    private TextView nomProduit;
    private TextView date;
    private EditText qte;

    private Reservation r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_reservation);

        SetupButton();
        SelectDateWithPopup();

        // Recuperation de l'objet reservation pour preremplir les champs.
        r = (Reservation) getIntent().getSerializableExtra("Reservation");

        nomProduit = (TextView) findViewById(R.id.textView_modifierReservation_nomProduit);
        /**
         * TODO: Changer ce setText pour avoir le nom du produit a la place de son id -> nomProduit.setText(r.getProduit().getNom());
         */
        nomProduit.setText(Integer.toString(r.getProduit_id()));

        date = (TextView) findViewById(R.id.textView_modifierReservation_selectDate);
        date.setText(r.getDate_retour_prevue());

        qte = (EditText) findViewById(R.id.editTextNumber_modifierReservation_quantite);
        qte.setText(Integer.toString(r.getQuantite()));
    }

    /**
     * Fonction qui initialise les bouttons.
     */
    private void SetupButton()
    {
        btn_modifier = (Button) findViewById(R.id.btn_modifierReservation_confirmer);
        btn_modifier.setText(R.string.btn_modifierReservation_confirmer);
        btn_modifier.setOnClickListener(this);
        btn_annuler = (Button) findViewById(R.id.btn_modifierReservation_supprimer);
        btn_annuler.setText(R.string.btn_modifierReservation_supprimer);
        btn_annuler.setOnClickListener(this);
    }

    /**
     * Fonction qui permet de selectionner une date avec une fenetre popup.
     */
    private void SelectDateWithPopup()
    {
        mDisplayDate = (TextView) findViewById(R.id.textView_modifierReservation_selectDate);
        mDisplayDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(ModifierReservation.this, android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year,month,day);
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
     * Gere les clicks des boutons confirmer/supprimer de la view.
     * @param v
     *
     */
    @Override
    public void onClick(View v) {
        // Switch case en fonction du bouton appuyer
        switch (v.getId()) {
            case R.id.btn_modifierReservation_confirmer:
                // Update la reservation dans la base de données
                EditText qty = (EditText) findViewById(R.id.editTextNumber_modifierReservation_quantite);
                TextView date_retour = (TextView) findViewById(R.id.textView_modifierReservation_selectDate);

                r.setQuantite(Integer.parseInt(String.valueOf(qty.getText())));
                r.setDate_retour_prevue(String.valueOf(date_retour.getText()));
                r.insertIntoDb();
                Toast.makeText(this, "Reservation mise à jour avec succès.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_modifierReservation_supprimer:
                // Supprime la reservation de la BD
                r.deleteFromDb();
                Toast.makeText(this, "Reservation supprimer avec succès.", Toast.LENGTH_SHORT).show();
                finish(); // ferme cette activite puisque cette reservation n'existe plus.
                break;
        }
    }
}