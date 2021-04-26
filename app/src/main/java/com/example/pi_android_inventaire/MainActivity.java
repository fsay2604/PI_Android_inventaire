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

package com.example.pi_android_inventaire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static SQLiteDatabase mydb;

    // Boutons du menu
    private  Button btn_produit;
    private  Button btn_reservation;
    private  Button btn_compte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Database
        setupDBConnection();
        // Menu
        setupMenu();
    }

    private void setupDBConnection() {
        mydb = openOrCreateDatabase("pi_inventaire_android",MODE_PRIVATE,null);
        //mydb.execSQL("DROP TABLE produit");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS produit(id INTEGER PRIMARY KEY,categorie_id INTEGER NOT NULL,nom VARCHAR NOT NULL,description VARCHAR,commentaire VARCHAR,qte_disponible INTEGER NOT NULL,qte_reserve INTEGER NOT NULL,qte_defectueux INTEGER NOT NULL,image VARCHAR,FOREIGN KEY(categorie_id) REFERENCES categorie(id))");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS categorie(id INTEGER PRIMARY KEY,nom VARCHAR)");
        //mydb.execSQL("DROP TABLE reservation");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS reservation(id INTEGER PRIMARY KEY,etat_reservation_id INTEGER NOT NULL,produit_id INTEGER NOT NULL,numero_utilisateur_id INTEGER NOT NULL,date_retour_prevue DATE NOT NULL,quantite INTEGER NOT NULL,date_retour_reel VARCHAR,FOREIGN KEY(etat_reservation_id) REFERENCES type_rapport(id),FOREIGN KEY(produit_id) REFERENCES produit(id))");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS etat_reservation(id INTEGER PRIMARY KEY,libelle VARCHAR NOT NULL)");
        //mydb.execSQL("DROP TABLE rapport");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS rapport(id INTEGER PRIMARY KEY,produit_id INTEGER NOT NULL,user_id INTEGER NOT NULL,type_rapport_id INTEGER NOT NULL,description VARCHAR NOT NULL,FOREIGN KEY(produit_id) REFERENCES produit(id),FOREIGN KEY(type_rapport_id) REFERENCES type_rapport(id))");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS type_rapport(id INTEGER PRIMARY KEY,type VARCHAR NOT NULL)");
    }


    /**
     * Fonction qui initialise les bouttons du menu afin de rediriger vers les bonnes pages
     */
    private void setupMenu()
    {
        // Recuperation des btn
        btn_produit = (Button) findViewById(R.id.btn_produit);
        btn_produit.setText(R.string.btn_produit);
        btn_produit.setOnClickListener(this);

        btn_reservation = (Button) findViewById(R.id.btn_reservation);
        btn_reservation.setText(R.string.btn_reservation);
        btn_reservation.setOnClickListener(this);

        btn_compte = (Button) findViewById(R.id.btn_compte);
        btn_compte.setText(R.string.btn_compte);
        btn_compte.setOnClickListener(this);
    }

    /**
     * Fonction qui gere le listener des boutons.
     * @param v     represente la vue
     */
    @Override
    public void onClick(View v) {

        // Switch case en fonction du bouton appuyer
        switch (v.getId()) {
            case R.id.btn_produit:
                // redirection a la page liste produit
                Intent intentListeProduits = new Intent(this,liste_Produits.class);
                startActivity(intentListeProduits);
                break;
            case R.id.btn_reservation:
                // redirection vers la page pour faire une reservation
                Intent intentReservation = new Intent(this, Reservation_index.class);
                startActivity(intentReservation);
                break;
            case R.id.btn_compte:
                // redirection vers la page de compte
                break;
            default:
                break;
        }
    }
} // Main activity Class ends here