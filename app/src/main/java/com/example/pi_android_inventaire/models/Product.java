package com.example.pi_android_inventaire.models;

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
}
