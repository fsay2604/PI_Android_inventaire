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

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe representant une reservation.
 */
public class Reservation implements Serializable { // l<implementation de Serializable permet de passer l'objet en extra a l'intent et de le recuperer.

    @SerializedName("id")
    private int id;

    @SerializedName("produit_id")
    private int produit_id;

    @SerializedName("etat_id")
    private int etat_id;

    @SerializedName("numero_utilisateur")
    private int numero_utilisateur;

    @SerializedName("date_retour_prevue")
    private String date_retour_prevue;

    @SerializedName("quantite")
    private int quantite;

    @SerializedName("date_retour_reel")
    private String date_retour_reel;

    private Product p;

    /**
     * Constructeur d'une réservation avec params.
     * @param id    correspond a l;id de la reservation
     * @param produit_id    correspond au id du produit lié a cette reservation
     * @param numero_utilisateur    correspond a l'id de l'utilisateur auquel la reservation est associe
     * @param date_retour_prevue    correspond a la date de retour prevue
     * @param quantite              corespond a la quantite du produit que l'utilisateur a reserve
     * @param date_retour_reel      correspond a la date de retour reel des produits chez le fournisseur.
     */
    public Reservation(int id, int etat_id, int produit_id, int numero_utilisateur, String date_retour_prevue, int quantite, String date_retour_reel) {
        this.id = id;
        this.etat_id = etat_id;
        this.produit_id = produit_id;
        this.numero_utilisateur = numero_utilisateur;
        this.date_retour_prevue = date_retour_prevue;
        this.quantite = quantite;
        this.date_retour_reel = date_retour_reel;

        // Construire le produit p correspondant a la reservation
        setProduit(); //(contiendra la requete a la bd en fonction de this.produit_id)
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
     * Retourne un objet produit associé a cette reservation (pour recuperer le nom facilement)
     * @return
     */
    public Product getProduit() {
        return p;
    }

    /**
     * Permet d'associé le bon objet de produit en fonction de l'id
     *
     */
    private void setProduit() {
        // QUERY = 'SELECT * FROM Produits WHERE id = this.produit_id'
        // this.p = QueryResponse
        this.p = p;
    }

    /**
     * Retourne une reservations associé au user_id.
     * @param user_id   le user_id de l'utilisateur.
     * @return ArrayList<Reservation>    Contient tout les reservations associées au user_id.
     */
    public Reservation get_reservation(int user_id)
    {
        // Query to DB locale (mise a jour au depart de l'appli avec la classe api, si ya connection)
        // String query = 'SELECT * FROM Reservation WHERE numero_utilisateur = user_id'; + join table produit pour le nom
        // queryResponse = db.execSQL(query);
        // r = queryResponse;

        return this;
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

    /**
     * Envoit l'objet dans la bd locale si il n'existe pas (modifierReservation et FaireReservation)
     */
    public void put_in_db()
    {
        // Query pour add une row dans les bonnes tables
    }

    /**
     * Fonction qui va permettre de détruire cette reservation de la BD si elle est encore en attente
     */
    public void delete_from_db()
    {
        if(this.etat_id == 1) // 1= En attente
        {
            //query = 'Delete from Reservation WHERE id = this.id'
        }
        else
        {
            // Impossible puisque la reservation n'est pas en attente, et donc les capteurs ont deja ete recuperer.
        }
    }


}
