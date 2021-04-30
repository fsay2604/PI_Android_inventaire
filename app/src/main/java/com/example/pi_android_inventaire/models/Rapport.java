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

public class Rapport implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("produit_id")
    private int produit_id;

    @SerializedName("user_id")
    private int user_id;

    @SerializedName("type_rapport_id")
    private int type_rapport_id;

    @SerializedName("description")
    private String description;

    private Product p;

    /**
     * @param id
     * @param produit_id
     * @param user_id
     * @param type_rapport_id
     * @param description
     */
    public Rapport(int id, int produit_id, int user_id, int type_rapport_id, String description){
        this.id = id;
        this.produit_id = produit_id;
        this.user_id = user_id;
        this.type_rapport_id = type_rapport_id;
        this.description = description;
    }

    public Rapport(){
        this.id = 0;
        this.produit_id = 0;
        this.user_id = 0;
        this.type_rapport_id = 0;
        this.description = "";
    }

    public Rapport get_rapport(int numero_utilisateur){
        // Query to DB locale
        // String query = 'SELECT * FROM Rapport WHERE user_id = numero_utilisateur'; + join table produit pour le nom
        // queryResponse = db.execSQL(query);
        // r = queryResponse;

        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getType_rapport_id() {
        return type_rapport_id;
    }

    public void setType_rapport_id(int type_rapport_id) {
        this.type_rapport_id = type_rapport_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getP() {
        return p;
    }

    public void setP(Product p) {
        this.p = p;
    }
}
