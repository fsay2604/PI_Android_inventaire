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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import com.example.pi_android_inventaire.PIAndroidInventaire;
import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.models.Product;
import com.example.pi_android_inventaire.models.Reservation;

public class FaireReservation extends AppCompatActivity implements View.OnClickListener {
    // Selection de date avec fenetre popup
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    // Boutons
    private Button btn_reserver;
    private Button btn_annuler;

    private TextView nom_etudiant;
    private TextView nom_produit;
    private EditText qty;

    private Reservation r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faire_reservation);

        // Gere la fenetre popup pour la selectionner une date
        SelectDateWithPopup();
        SetupButton();

        // Recuperation de l'id du produit passer dans l'intent
        // Et assignation de cette id a la reservation.
        // La variable Product p de l'objet Reservation s'initialise automatiquement lors de l'appel de r.setProduit_id().
        r = new Reservation();
        Product p = (Product) getIntent().getSerializableExtra("product");
        r.setProduit_id(p.getId());

        nom_produit = (TextView) findViewById(R.id.textView_faireReservation_nomProduit);
        nom_produit.setText(p.getNom());

        nom_etudiant = (TextView) findViewById(R.id.textView_faireReservation_NomClient);
        nom_etudiant.setText(MainActivity.currentUser.getFirstName() + " " + MainActivity.currentUser.getLastName());

        qty = (EditText) findViewById(R.id.editTextNumber_faireReservation_quantite);
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
                month = month+1; // +1 parce que janvier = 0
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
                // Batir l'objet r en fonction des champs du formulaire
                qty = (EditText) findViewById(R.id.editTextNumber_faireReservation_quantite);
                r.setQuantite(Integer.parseInt(String.valueOf(qty.getText())));
                r.setEtat_id(1);
                r.setDate_retour_prevue(String.valueOf(mDisplayDate.getText()));
                r.setNumero_utilisateur(MainActivity.currentUser.getId());
                r.setDate_retour_reel("");

                r.put_in_db();  // Enregistre dans la BD la réservation

                //PIAndroidInventaire.apiCaller.putSingleOrDefault(r, PIAndroidInventaire.apiUrlDomain + "");
                Toast.makeText(this, "Reservation ajouter avec succès.", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.btn_faireReservation_annuler:
                finish(); // ferme cette activite sans sauvegarder la reservation
                break;
        }
    }
}