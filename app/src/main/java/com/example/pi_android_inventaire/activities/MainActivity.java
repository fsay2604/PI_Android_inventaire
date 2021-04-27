package com.example.pi_android_inventaire.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.pi_android_inventaire.PIAndroidInventaire;
import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.models.Product;
import com.example.pi_android_inventaire.network.ApiCaller;
import com.example.pi_android_inventaire.network.ApiCallerCallback;
import com.example.pi_android_inventaire.utils.Result;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static SQLiteDatabase mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDBConnection();

        // TEST DU API CALLER
        ApiCaller<Product> apiCaller = new ApiCaller<>(PIAndroidInventaire.executorService);
        ArrayList<Product> products = apiCaller.getList(Product.class,"https://7cb6dae8616b.ngrok.io/api/produits?page=1");
        int alllo = 0;
        // FIN TEST DU API CALLER
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
}