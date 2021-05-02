/****************************************
 Fichier : ApiCaller.java
 Auteur : Marc Antoine Griffiths Lorange
 Fonctionnalité : Classe de modelisation des données d'un utilisateur.
 Date : 2021-04-28
 Vérification :
 Date               Nom                   Approuvé
 =========================================================

 Historique de modifications :
 Date               Nom                   Description
 =========================================================
 ****************************************/
package com.example.pi_android_inventaire.models;

import com.example.pi_android_inventaire.activities.MainActivity;
import com.google.gson.annotations.SerializedName;

public class User {
    /**
     * id
     */
    @SerializedName("id")
    private int id;
    /**
     * email
     */
    @SerializedName("email")
    private String email;
    /**
     * firebaseToken
     */
    @SerializedName("firebaseToken")
    private String firebaseToken;
    /**
     * firstName
     */
    @SerializedName("firstName")
    private String firstName;
    /**
     * lastName
     */
    @SerializedName("lastName")
    private String lastName;

    public User(int id, String email, String fireBaseToken) {
        this.id = id;
        this.email = email;
        this.firebaseToken = fireBaseToken;
    }
    public User() {
        this.id = 0;
        this.email = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirebaseToken() {
        if(firebaseToken== null){
            return new String("");
        }
        return firebaseToken;
    }

    public void setFirebaseToken(String firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
