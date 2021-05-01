package com.example.pi_android_inventaire.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pi_android_inventaire.PIAndroidInventaire;
import com.example.pi_android_inventaire.interfaces.SyncableModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Rapport implements Serializable, SyncableModel {

    @SerializedName("id")
    private int id;

    @SerializedName("produitId")
    private int produit_id;

    @SerializedName("userId")
    private int user_id;

    @SerializedName("typeRapportId")
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

    @Override
    public SyncableModel initializeFromCursor(Cursor cursor) {

        this.setId(cursor.getInt(0));
        this.setProduit_id(cursor.getInt(1));
        this.setUser_id(cursor.getInt(2));
        this.setType_rapport_id(cursor.getInt(3));
        this.setDescription(cursor.getString(4));

        return this;
    }

    /**
     * Update de l'enregistrement
     */
    public void update_db()
    {
        // Aller chercher la DB
        SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();

        // Construction du conteneur des informations a update
        //ValuesToUpdate.put("db_col_name", values_to_put);
        ContentValues ValuesToUpdate = new ContentValues();
        ValuesToUpdate.put("id",Integer.toString(this.id));
        ValuesToUpdate.put("produit_id",Integer.toString(this.produit_id));
        ValuesToUpdate.put("user_id",Integer.toString(this.user_id));
        ValuesToUpdate.put("type_rapport_id",Integer.toString(this.type_rapport_id));
        ValuesToUpdate.put("description",this.description);

        // Update
        DB.update("rapport", ValuesToUpdate,"id = ?", new String[] {Integer.toString(this.id)});
    }

    @Override
    public void insertIntoDb() {
        SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();
        String countQuery = "SELECT id FROM rapport WHERE id=" + this.id;
        Cursor cursor = DB.rawQuery(countQuery, null);
        int count = cursor.getCount();
        if(count > 0)
        {
            // Update dans la BD
            this.update_db();
        }
        else
        {
            // Ajout dans la BD
            String query = "INSERT INTO rapport (id,produit_id, user_id, type_rapport_id, description) VALUES (?, ?, ?, ?, ?)";
            DB.execSQL(query, new String[]{ Integer.toString(this.id) , Integer.toString(this.produit_id), Integer.toString(this.user_id), Integer.toString(this.type_rapport_id), this.description});
        }
        cursor.close();


    }

    @Override
    public void deleteFromDb() {

    }
}
