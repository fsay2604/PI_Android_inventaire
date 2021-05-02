/****************************************
 Fichier : User.java
 Auteur : Philippe Boulanger
 Fonctionnalité : Classe de l'utilisateur avec ses getteurs/setteurs

 Date : 2021-04-29

 Vérification :
 Date           Nom             Approuvé
 =========================================================


 Historique de modifications :
 Date           Nom             Description
 =========================================================
 2021-04-29      Philippe Boulanger
 ****************************************/
package com.example.pi_android_inventaire.models;

import com.google.gson.annotations.SerializedName;

public class User {
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
     * prenom
     */
    @SerializedName("prenom")
    private String prenom;

    /**
     * email
     */
    @SerializedName("email")
    private  String email;
    /**
     * password
     */
    @SerializedName("password")
    private String password;


    public User(int id, String nom, String prenom, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
    }

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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
