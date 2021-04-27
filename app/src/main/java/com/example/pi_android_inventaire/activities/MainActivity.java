/****************************************
 Fichier : MainActivity.java
 Auteur : Francois Charles Hebert
 Fonctionnalité :
    - Page d'accueil avec un menu.

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
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pi_android_inventaire.PIAndroidInventaire;
import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.activities.Liste_produits;
import com.example.pi_android_inventaire.models.Product;
import com.example.pi_android_inventaire.network.ApiCaller;
import com.example.pi_android_inventaire.network.ApiCallerCallback;
import com.example.pi_android_inventaire.utils.Result;

import org.json.JSONObject;

import java.util.ArrayList;

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

        // TEST DU API CALLER
        ApiCaller<Product> apiCaller = new ApiCaller<>(PIAndroidInventaire.executorService);
        ArrayList<Product> products = apiCaller.getList(Product.class,"https://7cb6dae8616b.ngrok.io/api/produits?page=1");

        Product product = apiCaller.getSingleOrDefault(Product.class, "https://7cb6dae8616b.ngrok.io/api/produits/2");

        int alllo = 0;
        // FIN TEST DU API CALLER
        // Menu
        setupMenu();


    }

    private void setupDBConnection() {
        mydb = openOrCreateDatabase("pi_inventaire_android",MODE_PRIVATE,null);
        //mydb.execSQL("DROP TABLE produit");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS produit(id INTEGER PRIMARY KEY,categorie_id INTEGER NOT NULL,nom VARCHAR NOT NULL,description VARCHAR,commentaire VARCHAR,qte_disponible INTEGER NOT NULL,qte_reserve INTEGER NOT NULL,qte_defectueux INTEGER NOT NULL,image VARCHAR,FOREIGN KEY(categorie_id) REFERENCES categorie(id))");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS categorie(id INTEGER PRIMARY KEY,nom VARCHAR)");
        //mydb.execSQL("DROP TABLE reservation");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS reservation(id INTEGER PRIMARY KEY, etat_reservation_id INTEGER NOT NULL,produit_id INTEGER NOT NULL,numero_utilisateur_id INTEGER NOT NULL, date_retour_prevue varchar NOT NULL, quantite INTEGER NOT NULL, date_retour_reel VARCHAR, FOREIGN KEY(etat_reservation_id) REFERENCES type_rapport(id),FOREIGN KEY(produit_id) REFERENCES produit(id))");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS etat_reservation(id INTEGER PRIMARY KEY, libelle VARCHAR NOT NULL)");
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
                Intent intentListeProduit = new Intent(this, Liste_produits.class);
                startActivity(intentListeProduit);
                break;
            case R.id.btn_reservation:
                // redirection vers la page pour faire une reservation
                Intent intent = new Intent(this,FaireReservation.class);
                startActivity(intent);
                break;
            case R.id.btn_compte:
                // redirection vers la page de compte
                break;
            default:
                break;
        }
    }
} // Main activity Class ends here