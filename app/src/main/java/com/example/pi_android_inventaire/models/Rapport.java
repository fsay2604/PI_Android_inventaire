package com.example.pi_android_inventaire.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pi_android_inventaire.PIAndroidInventaire;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Rapport implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("produit_id")
    private int produit_id;

    @SerializedName("user_id")
    private int user_id;

    @SerializedName("type_rapport_id")
    private int type_rapport_id;

    @SerializedName("description")
    private String description;

    private Product p;

    /**
     * @param id
     * @param produit_id
     * @param user_id
     * @param type_rapport_id
     * @param description
     */
    public Rapport(int id, int produit_id, int user_id, int type_rapport_id, String description){
        this.id = id;
        this.produit_id = produit_id;
        this.user_id = user_id;
        this.type_rapport_id = type_rapport_id;
        this.description = description;
    }

    public Rapport(){
        this.id = 0;
        this.produit_id = 0;
        this.user_id = 0;
        this.type_rapport_id = 0;
        this.description = "";
    }

    public Rapport get_rapport(int numero_utilisateur){
        //Query to DB locale
        SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();

        Cursor c = DB.rawQuery("SELECT * FROM rapport INNER JOIN produit as p WHERE p.id = produit_id and numero_utilisateur_id = ?", new String[] {Integer.toString(numero_utilisateur)} );
        //String query = "SELECT * FROM `rapport`INNER JOIN produit as p WHERE p.id = produit_id and user_id = numero_utilisateur";
        //queryResponse = db.execSQL(query);
        //r = queryResponse;

        return this;
    }

    /**
     * Fonction qui va permettre de supprimer un produit à la bd
     */
    public void delete_from_bd()
    {
        // Aller chercher la DB
        SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();

        // Suppression de l'enregistrement
        String query = "Delete from rapport WHERE id = ?";
        DB.execSQL(query, new String[] {Integer.toString(this.id)} );


        // Fermeture du curseur.
        //cursor.close();
        
    }

    public static ArrayList<Rapport> get_all_rapport(int numero_utilisateur)
    {
        // Conteneur des reservations a retourné
        ArrayList<Rapport> all_rapports = new ArrayList<Rapport>();

        // Aller chercher la DB
        SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();

        // Query
        Cursor c = DB.rawQuery("SELECT * FROM rapport INNER JOIN produit as p WHERE p.id = produit_id and numero_utilisateur_id = ?", new String[] {Integer.toString(numero_utilisateur)} );

        // Parcours l'ensemble de la reponse du Select contenu dans le cursor c
        if(c.moveToFirst())
        {
            do {
                Rapport r = new Rapport();

                r.setId(c.getInt(0));
                r.setProduit_id(c.getInt(1));
                r.setUser_id(c.getInt(2));
                r.setType_rapport_id(c.getInt(3));
                r.setDescription(c.getString(4));

                // Ajout du produit dans l'array
                all_rapports.add(r);
            }while(c.moveToNext());
        }

        return all_rapports;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getType_rapport_id() {
        return type_rapport_id;
    }

    public void setType_rapport_id(int type_rapport_id) {
        this.type_rapport_id = type_rapport_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getP() {
        return p;
    }

    public void setP(Product p) {
        this.p = p;
    }
}
