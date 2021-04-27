package com.example.pi_android_inventaire.models;

import com.google.gson.annotations.SerializedName;

public class Product {
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
}
