/****************************************
 Fichier : Produit.java
 Auteur : Julien Fortier
 Fonctionnalité : L'objet produit qui permet de construire, get et set un produit
 Date : 2021-04-27
 Vérification :
 Date                       Nom                   Approuvé
 =========================================================

 Historique de modifications :
 Date                       Nom                 Description
 =========================================================

 ****************************************/
package com.example.pi_android_inventaire;

public class Produit {
    private int id;
    private int categorie;
    private String nom;
    private String description;
    private String commentaire;
    private int qte_disponible;
    private String image;

    public Produit(int id, int categorie, String nom, String description, String commentaire, int qte_disponible, String image) {
        this.id = id;
        this.categorie = categorie;
        this.nom = nom;
        this.description = description;
        this.commentaire = commentaire;
        this.qte_disponible = qte_disponible;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
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

    public int getQte_disponible() {
        return qte_disponible;
    }

    public void setQte_disponible(int qte_disponible) {
        this.qte_disponible = qte_disponible;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
