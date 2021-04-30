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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pi_android_inventaire.PIAndroidInventaire;
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
     * Et set le this.produit associé a cette produit_id
     * @param produit_id
     */
    public void setProduit_id(int produit_id) {

        this.produit_id = produit_id;
        /**
         * TODO: Decommenter la fonction setProduit quand il y aura de l'info dans la BD.
         */
        //this.setProduit(); // Met a jour l'objet produit
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
     * Permet d'associé le bon objet de produit en fonction de l'id_produit
     * Il faut que this.produit_id ai ete set avant.
     * TODO: Requete a tester quand des produits auront ete ajouter a la bds
     */
   private void setProduit() {

        if(this.produit_id != 0) {
            // Aller chercher la DB
            SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();

            // Query
            Cursor c = DB.rawQuery("SELECT * FROM produit WHERE id = ?", new String[]{Integer.toString(this.produit_id)});

            // Parcours l'ensemble de la reponse du Select contenu dans le cursor c
            if (c.moveToFirst()) {
                Product p = new Product();

                // Construction de du produit
                p.setId(c.getInt(1));
                p.setCategorie(c.getInt(2));
                p.setNom(c.getString(3));
                p.setDescription(c.getString(4));
                p.setCommentaire(c.getString(5));
                p.setQteDisponible(c.getInt(6));
                p.setImage(c.getString(9));

                // Set le produit construit a la variable this.produit
                this.p = p;
            }
        }
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
     *
     */
    public void put_in_db()
    {
        // Aller chercher la DB
        SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();

        // Ajout dans la BD
        String query = "INSERT INTO reservation (etat_reservation_id, produit_id, numero_utilisateur_id, date_retour_prevue, quantite, date_retour_reel) VALUES (?, ?, ?, ?, ?, ?)";
        DB.execSQL(query, new String[]{ Integer.toString(this.etat_id), Integer.toString(this.produit_id), Integer.toString(this.numero_utilisateur), this.date_retour_prevue, Integer.toString(this.quantite), this.date_retour_reel});
    }

    /**
     * Update de l'enregistrement
     */
    public void update_db()
    {
        // Aller chercher la DB
        SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();

        // Construction du conteneur des informations a update
        //ValuesToUpdate.put("db_col_name", values_to_put);
        ContentValues ValuesToUpdate = new ContentValues();
        ValuesToUpdate.put("etat_reservation_id",Integer.toString(this.etat_id));
        ValuesToUpdate.put("produit_id",Integer.toString(this.produit_id));
        ValuesToUpdate.put("numero_utilisateur_id",Integer.toString(this.numero_utilisateur));
        ValuesToUpdate.put("date_retour_prevue",this.date_retour_prevue);
        ValuesToUpdate.put("quantite",Integer.toString(this.quantite));
        ValuesToUpdate.put("date_retour_reel",this.date_retour_reel);

        // Update
        DB.update("reservation", ValuesToUpdate,"id = ?", new String[] {Integer.toString(this.id)});
    }

    /**
     * Fonction qui va permettre de détruire cette reservation de la BD si elle est encore en attente
     *
     */
   public void delete_from_db()
    {
        if(this.etat_id == 1) // 1= En attente
        {
            // Aller chercher la DB
            SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();

            // Suppression de l'enregistrement
            String query = "Delete from reservation WHERE id = ?";
            DB.execSQL(query, new String[] {Integer.toString(this.id)} );
        }
    }

    /**
     * Fonction qui retourne l'ensemble des réservations avec l'etat 'en attente' relié a un utilisateur
     * @param User_id   Le numero de l'utilisateur pour lequel on veut avoir ses reservations
     * @return ArrayList<Reservation> contenant l'ensemble des reservation 'en attente' de l'utilisateur spécifier en parametre,
     */
    public static ArrayList<Reservation> get_all_reservations(int User_id)
    {
        // Conteneur des reservations a retourné
        ArrayList<Reservation> all_reservations = new ArrayList<Reservation>();

        // Aller chercher la DB
        SQLiteDatabase DB = PIAndroidInventaire.getDatabaseInstance();

        // Query
        Cursor c = DB.rawQuery("SELECT * FROM reservation WHERE numero_utilisateur_id = ? AND etat_reservation_id = 1", new String[] {Integer.toString(User_id)} ); //etat_reservation_id = 1 -> reservation en attente

        // Parcours l'ensemble de la reponse du Select contenu dans le cursor c
        if(c.moveToFirst())
        {
            do {
                Reservation r = new Reservation();

                // Construction de la reservation
                r.setId(c.getInt(0));
                r.setEtat_id(c.getInt(1));
                r.setProduit_id(c.getInt(2));
                r.setNumero_utilisateur(c.getInt(3));
                r.setDate_retour_prevue(c.getString(4));
                r.setQuantite(c.getInt(5));
                r.setDate_retour_reel(c.getString(6));

                // Ajout de la reservation a l'array que l'on retourne
                all_reservations.add(r);
            }while(c.moveToNext());  // Avance d'une row
        }

        return all_reservations;
    }
}


