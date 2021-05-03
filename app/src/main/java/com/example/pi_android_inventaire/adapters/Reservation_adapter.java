/****************************************
 Fichier : VoirReservation.java
 Auteur : Francois Charles Hebert
 Fonctionnalité : a-03 - Gestion des réservation
    - Adapteur pour utiliser un recyclerView pour lister toutes les réservations

 Date : 2021-04-27

 Vérification :
 Date           Nom             Approuvé
 =========================================================


 Historique de modifications :
 Date           Nom             Description
 =========================================================

 ****************************************/

package com.example.pi_android_inventaire.adapters;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.activities.MainActivity;
import com.example.pi_android_inventaire.activities.ModifierReservation;
import com.example.pi_android_inventaire.activities.VoirReservations;
import com.example.pi_android_inventaire.models.Reservation;

import java.util.ArrayList;
import java.util.Calendar;

public class Reservation_adapter extends RecyclerView.Adapter<Reservation_adapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Reservation> mAll_reservations;   // Contient les infos des reservations, utiliser le produit_id pour aller cherher les infos du produit reli/ a la reservation

    public Reservation_adapter(Context ct, ArrayList<Reservation> all_reservations)
    {
        mContext =ct;
        mAll_reservations = all_reservations;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.row_voir_reservations, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.nomProduit.setText(mAll_reservations.get(position).getProduit().getNom());

        /**
         * TODO: Changer le param pour Integer.toString(mAll_reservations.get(position).getProduit().getCategorieName(); un fois la classe produit termine
         */
        //holder.nomCategorie.setText(Integer.toString(mAll_reservations.get(position).getId()));
        holder.nomCategorie.setText(mAll_reservations.get(position).getProduit().getCategorieName(mAll_reservations.get(position).getProduit().getId()));

        // Vue que l'on affiche seulement les reservation en attente, pas besoin de faire de requete a la bd.
        holder.etat.setText("En attente");

        holder.qte.setText(Integer.toString(mAll_reservations.get(position).getQuantite()));
       // holder.image_prod.setImageResource();                                                       // Aller chercher l'image du produit.

        // Amene vers la page ModifierReservation, qui permet de supprimer/modifier une reservation
        holder.btn_modifier.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ModifierReservation.class);

                // Passe l'objet de la reservation dans l'intent pour preremplir les champs dans la page ModifierReservation
                i.putExtra("Reservation", mAll_reservations.get(position));
                holder.context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mAll_reservations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView nomProduit;
        private TextView nomCategorie;
        private TextView qte;
        private TextView etat;
        private ImageView image_prod;
        private Button btn_modifier;

        private Context context;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            nomProduit = itemView.findViewById(R.id.TextView_voirReservation_nom);
            nomCategorie = itemView.findViewById(R.id.textView_voirReservation_categorie);
            qte = itemView.findViewById(R.id.textView_voirReservation_qte);
            etat = itemView.findViewById(R.id.textView_voirReservation_etat);
            image_prod = itemView.findViewById(R.id.imageView_voirReservation_img);

            btn_modifier = itemView.findViewById(R.id.btn_VoirReservation_modifier);
            btn_modifier.setText(R.string.btn_modifier);
        }

    }
}
