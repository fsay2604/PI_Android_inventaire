package com.example.pi_android_inventaire.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pi_android_inventaire.PIAndroidInventaire;
import com.google.gson.annotations.SerializedName;

public class Product {
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
     * Fonction qui va permettre d'insérer un produit à la bd si il n'est pas déjà dans la bd
     */
    public void insert_from_bd()
    {
            // Aller chercher la DB
            SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();
            String countQuery = "SELECT COUNT(*) FROM produit WHERE id=" + this.id;
            Cursor cursor = DB.rawQuery(countQuery, null);
            int count = cursor.getCount();
            if(count > 0)
            {
                DB.rawQuery("UPDATE produit SET id = ?,categorie_id = ?,nom = ?,description = ?,commentaire = ?,qte_disponible = ?, image = ? WHERE id = ?",
                        new String[]{Integer.toString(this.id),Integer.toString(this.categorie),this.nom,this.description,this.commentaire,Integer.toString(this.qteDisponible),this.image,Integer.toString(this.id)});
            }
            else
            {
                DB.rawQuery("INSERT INTO produit (id,categorie_id,nom,description,commentaire,qte_disponible,image) " +
                        "VALUES (?,?,?,?,?,?,?,?,?)", new String[]{Integer.toString(this.id),Integer.toString(this.categorie),this.nom,this.description,this.commentaire,Integer.toString(this.qteDisponible),this.image});
            }
            cursor.close();


    }
    /**
     * Fonction qui va permettre de supprimer un produit à la bd
     */
    public void delete_from_bd()
    {
        // Aller chercher la DB
        SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();

        // Suppression de l'enregistrement
        Cursor cursor = DB.rawQuery("Delete from produit WHERE id = ?", new String[]{Integer.toString(this.id)});

        // Fermeture du curseur.
        cursor.close();


    }
}
