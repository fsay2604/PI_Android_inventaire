/****************************************
 Fichier : Catégorie.java
 Auteur : Marc Antoine Griffiths Lorange
 Fonctionnalité : Classe de modelisation des données d'une Catégorie.
 Date : 2021-04-28
 Vérification :
 Date               Nom                   Approuvé
 =========================================================

 Historique de modifications :
 Date               Nom                   Description
 =========================================================
 ****************************************/
package com.example.pi_android_inventaire.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pi_android_inventaire.PIAndroidInventaire;
import com.example.pi_android_inventaire.interfaces.SyncableModel;
import com.google.gson.annotations.SerializedName;

public class Categorie  implements SyncableModel {

    /**
     * id
     */
    @SerializedName("id")
    private Integer id;
    /**
     * nom
     */
    @SerializedName("nom")
    private String nom;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public SyncableModel initializeFromCursor(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.nom = cursor.getString(1);
        return this;
    }

    @Override
    public void insertIntoDb() {
        // Aller chercher la DB
        SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();
        String countQuery = "SELECT id FROM categorie WHERE id=" + this.id;
        Cursor cursor = DB.rawQuery(countQuery, null);
        int count = cursor.getCount();
        if(count > 0)
        {
            DB.execSQL("UPDATE categorie SET id = ?,nom = ? WHERE id = ?",
                    new String[]{Integer.toString(this.id),this.nom,Integer.toString(this.id)});
        }
        else
        {
            DB.execSQL("INSERT INTO categorie (id,nom) " +
                    "VALUES (?,?)", new String[]{Integer.toString(this.id),this.nom});
        }

        cursor.close();
    }

    @Override
    public void deleteFromDb() {
        // Aller chercher la DB
        SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();

        // Suppression de l'enregistrement
        DB.execSQL("Delete from categorie WHERE id = ?", new String[]{Integer.toString(this.id)});
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Categorie other = (Categorie) obj;
        if (id != other.id)
            return false;
        return nom.equals(other.nom);
    }
}
