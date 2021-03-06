/****************************************
 Fichier : PIAndroidInventaire.java
 Auteur : Marc Antoine Griffiths Lorange
 Fonctionnalité : Classe de base de l'application contient une référence a la base de données
 ainsi que le pool de thread utilisé lors de l'exécution de l'application.
 Date : 2021-04-28
 Vérification :
 Date               Nom                   Approuvé
 =========================================================

 Historique de modifications :
 Date               Nom                   Description
 =========================================================
 ****************************************/
package com.example.pi_android_inventaire;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.sax.StartElementListener;

import com.example.pi_android_inventaire.activities.MainActivity;
import com.example.pi_android_inventaire.models.Product;
import com.example.pi_android_inventaire.models.Reservation;
import com.example.pi_android_inventaire.network.ApiCaller;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PIAndroidInventaire extends Application {
    private static SQLiteDatabase mydb;

    // Thread pool for background services execution
    public static ExecutorService executorService = Executors.newFixedThreadPool(4);

    // ApiCaller to make calls to the api using the background threads
    public static ApiCaller apiCaller;
    public static final String apiUrlDomain = "https://d9dad5efccc2.ngrok.io/api/";

    @Override
    public void onCreate() {
        super.onCreate();
        // Creating the apiCaller
        PIAndroidInventaire.apiCaller = new ApiCaller(PIAndroidInventaire.executorService);


        // Initializing/Creating the database
        mydb = openOrCreateDatabase("pi_inventaire_android",MODE_PRIVATE,null);
        //mydb.execSQL("DROP TABLE produit");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS produit(id INTEGER PRIMARY KEY,categorie_id INTEGER NOT NULL,nom VARCHAR NOT NULL,description VARCHAR,commentaire VARCHAR,qte_disponible INTEGER NOT NULL,image VARCHAR,FOREIGN KEY(categorie_id) REFERENCES categorie(id))");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS categorie(id INTEGER PRIMARY KEY ,nom VARCHAR)");
        //mydb.execSQL("DROP TABLE reservation");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS reservation(id INTEGER PRIMARY KEY, etat_reservation_id INTEGER NOT NULL,produit_id INTEGER NOT NULL,numero_utilisateur_id INTEGER NOT NULL, date_retour_prevue VARCHAR NOT NULL, quantite INTEGER NOT NULL, date_retour_reel VARCHAR, FOREIGN KEY(etat_reservation_id) REFERENCES etat_reservation(id),FOREIGN KEY(produit_id) REFERENCES produit(id))");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS etat_reservation(id INTEGER PRIMARY KEY , libelle VARCHAR NOT NULL)");
        //mydb.execSQL("DROP TABLE rapport");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS rapport(id INTEGER PRIMARY KEY ,produit_id INTEGER NOT NULL,user_id INTEGER NOT NULL,type_rapport_id INTEGER NOT NULL,description VARCHAR NOT NULL,FOREIGN KEY(produit_id) REFERENCES produit(id),FOREIGN KEY(type_rapport_id) REFERENCES type_rapport(id))");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS type_rapport(id INTEGER PRIMARY KEY ,type VARCHAR NOT NULL)");

        //test
        // mydb.execSQL("INSERT INTO categorie (id,nom) VALUES (1,'Diode')");
        //mydb.execSQL("INSERT INTO produit (id,categorie_id,nom,description,commentaire,qte_disponible,image) VALUES (1,1,'LED','Une description','un commentaire',10,'led.jpg')");

    }

    /**
     * Fonction qui renvoit la base de donnees static de l'application
     * @return
     */
    public static SQLiteDatabase getDatabaseInstance(){
        return mydb;
    }
}