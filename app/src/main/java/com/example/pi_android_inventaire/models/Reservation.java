/****************************************
 Fichier : Reservation.java
 Auteur : Francois Charles Hebert
 Fonctionnalité : a-03 - Gestion des réservation
    - Classe représentant une réservation.

 Date : 2021-04-27

 Vérification :
 Date           Nom             Approuvé
 =========================================================


 Historique de modifications :
 Date           Nom             Description
 =========================================================

 ****************************************/

package com.example.pi_android_inventaire.models;

import java.util.ArrayList;

public class Reservation {
    private int id;
    private int produit_id;
    private int etat_id;
    private int numero_utilisateur;
    private String date_retour_prevue;
    private int quantite;
    private String date_retour_reel;

    /**
     * Constructeur d'une réservation avec params.
     * @param id
     * @param produit_id
     * @param numero_utilisateur
     * @param date_retour_prevue
     * @param quantite
     * @param date_retour_reel
     */
    public Reservation(int id, int etat_id, int produit_id, int numero_utilisateur, String date_retour_prevue, int quantite, String date_retour_reel) {
        this.id = id;
        this.etat_id = etat_id;
        this.produit_id = produit_id;
        this.numero_utilisateur = numero_utilisateur;
        this.date_retour_prevue = date_retour_prevue;
        this.quantite = quantite;
        this.date_retour_reel = date_retour_reel;
    }

    /**
     * Constructeur par defaut
     */
    public Reservation() {
        this.id = 0;
        this.produit_id = 0;
        this.numero_utilisateur = 0;
        this.date_retour_prevue = "";
        this.quantite = 0;
        this.date_retour_reel = "";
    }

    /**
     * Get reservation id.
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * Set reservation id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get reservation produit_id
     * @return int id
     */
    public int getProduit_id() {
        return produit_id;
    }

    /**
     * Set reservation produit_id
     * @param produit_id
     */
    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }

    /**
     * get reservation Numero_utilisateur
     * @return int numero_utilisateur
     */
    public int getNumero_utilisateur() {
        return numero_utilisateur;
    }

    /**
     * Set reservation numero utilisateur
     * @param numero_utilisateur
     */
    public void setNumero_utilisateur(int numero_utilisateur) {
        this.numero_utilisateur = numero_utilisateur;
    }

    /**
     * get reservation date_retour_prevu
     * @return date_retour_prevu
     */
    public String getDate_retour_prevue() {
        return date_retour_prevue;
    }

    /**
     * set reservation date_retour_prevue
     * @param date_retour_prevue
     */
    public void setDate_retour_prevue(String date_retour_prevue) {
        this.date_retour_prevue = date_retour_prevue;
    }

    /**
     * get reservation quantite of product reserved
     * @return quantite
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Set reservation quantity of product to reserve
     * @param quantite
     */
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    /**
     * get reservation date_retour_reel
     * @return date_retour_reel
     */
    public String getDate_retour_reel() {
        return date_retour_reel;
    }

    /**
     * Set date_retour_reel of reservation
     * @param date_retour_reel
     */
    public void setDate_retour_reel(String date_retour_reel) {
        this.date_retour_reel = date_retour_reel;
    }

    /**
     * Retourne un tableau des reservations associé au user_id.
     * @param user_id   le user_id de l'utilisateur.
     * @return ArrayList<Reservation>    Contient tout les reservations associées au user_id.
     */
    public ArrayList<Reservation> get_all_reservations(int user_id)
    {
        ArrayList<Reservation> r = new ArrayList<Reservation>(); // conteneur des reservations

        // Query to DB locale (mise a jour au depart de l'appli avec la classe api, si ya connection)
        // String query = 'SELECT * FROM Reservation WHERE numero_utilisateur = user_id';
        // queryResponse = db.execSQL(query);
        // r = queryResponse;

        return r;
    }

    /**
     * get etat_id
     * @return
     */
    public int getEtat_id() {
        return etat_id;
    }

    /**
     * Set etat_id
     * @param etat_id
     */
    public void setEtat_id(int etat_id) {
        this.etat_id = etat_id;
    }
}
