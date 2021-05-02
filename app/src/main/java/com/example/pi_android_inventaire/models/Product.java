package com.example.pi_android_inventaire.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pi_android_inventaire.PIAndroidInventaire;
import com.example.pi_android_inventaire.interfaces.SyncableModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable, SyncableModel {
    public Product(int id, int categorie, String nom, String description, String commentaire, int qteDisponible, String image) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.commentaire = commentaire;
        this.qteDisponible = qteDisponible;
        this.categorie = categorie;
        this.image = image;
    }

    public Product() {
        this.id = 0;
        this.nom = "";
        this.description = "";
        this.commentaire = "";
        this.qteDisponible = 0;
        this.categorie = 0;
        this.image = "";
    }

    /**
     * id
     */
    @SerializedName("id")
    private int id;
    /**
     * nom
     */
    @SerializedName("nom")
    private String nom;
    /**
     * description
     */
    @SerializedName("description")
    private String description;
    /**
     * commentaire
     */
    @SerializedName("commentaire")
    private String commentaire;
    /**
     * qteDisponible
     */
    @SerializedName("qte_disponible")
    private Integer qteDisponible;
    /**
     * categorie
     */
    @SerializedName("categorieId")
    private Integer categorie;

    /**
     * image
     */
    @SerializedName("image")
    private String image;
    /**
     * qteReserve
     */


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Integer getQteDisponible() {
        return qteDisponible;
    }

    public void setQteDisponible(Integer qteDisponible) {
        this.qteDisponible = qteDisponible;
    }

    public Integer getCategorie() {
        return categorie;
    }

    public void setCategorie(Integer categorie) {
        this.categorie = categorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Fonction qui va permettre l'initialisation a partir d'un curseur
     */
    @Override
    public Product initializeFromCursor(Cursor cursor) {
        this.id = cursor.getInt(0);
        this.categorie = cursor.getInt(1);
        this.nom = cursor.getString(2);
        this.description = cursor.getString(3);
        this.commentaire = cursor.getString(4);
        this.qteDisponible = cursor.getInt(5);
        this.image = cursor.getString(6);
        return this;
    }

    /**
     * Fonction qui va permettre d'insérer un produit à la bd si il n'est pas déjà dans la bd
     */
    @Override
    public void insertIntoDb()
    {
            // Aller chercher la DB
            SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();
            String countQuery = "SELECT id FROM produit WHERE id=" + this.id;
            Cursor cursor = DB.rawQuery(countQuery, null);
            int count = cursor.getCount();
            if(count > 0)
            {
                DB.execSQL("UPDATE produit SET id = ?,categorie_id = ?,nom = ?,description = ?,commentaire = ?,qte_disponible = ?, image = ? WHERE id = ?",
                        new String[]{Integer.toString(this.id),Integer.toString(this.categorie),this.nom,this.description,this.commentaire,Integer.toString(this.qteDisponible),this.image,Integer.toString(this.id)});
            }
            else
            {
                DB.execSQL("INSERT INTO produit (id,categorie_id,nom,description,commentaire,qte_disponible,image) " +
                        "VALUES (?,?,?,?,?,?,?)", new String[]{Integer.toString(this.id),Integer.toString(this.categorie),this.nom,this.description,this.commentaire,Integer.toString(this.qteDisponible),this.image});
            }

            cursor.close();
    }
    /**
     * Fonction qui va permettre de supprimer un produit à la bd
     */
    @Override
    public void deleteFromDb()
    {
        // Aller chercher la DB
        SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();

        // Suppression de l'enregistrement
        DB.execSQL("Delete from produit WHERE id = ?", new String[]{Integer.toString(this.id)});
    }

    public Product get_produit_by_id(int id) {
        int id_produit = 0;
        int categorie = 0;
        String nom = "";
        String description = "";
        String commentaire = "";
        int qte_disponible = 0;
        String image = "";

        SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();
        Cursor c = DB.rawQuery("SELECT  * FROM produit WHERE id = ?", new String[]{Integer.toString(id)}, null);

        if (c.moveToFirst()) {
            do {

                //assing values
                id_produit = c.getInt(0);
                categorie = c.getInt(1);
                nom = c.getString(2);
                description = c.getString(3);
                commentaire = c.getString(4);
                qte_disponible = c.getInt(5);
                image = c.getString(6);


            } while (c.moveToNext());
        }
        Product produit = new Product(id_produit, categorie, nom, description, commentaire, qte_disponible, image);
        c.close();
        return produit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (id != other.id)
            return false;
        if (!categorie.equals(other.categorie))
            return false;
        if (!nom.equals(other.nom))
            return false;
        if (!description.equals(other.description))
            return false;
        if (!commentaire.equals(other.commentaire))
            return false;
        if (!qteDisponible.equals(other.qteDisponible))
            return false;
        return image.equals(other.image);
    }



}
