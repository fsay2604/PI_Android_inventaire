/****************************************
 Fichier : VoirReservation.java
 Auteur : Francois Charles Hebert
 Fonctionnalité : a-03 - Gestion des réservation
    - Adapteur pour utiliser un recyclerView pour lister toute les réservations

 Date : 2021-04-27

 Vérification :
 Date           Nom             Approuvé
 =========================================================


 Historique de modifications :
 Date           Nom             Description
 =========================================================

 ****************************************/

package com.example.pi_android_inventaire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
    public Reservation_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.row_voir_reservations, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Reservation_adapter.MyViewHolder holder, int position) {
        // Produit p = new Produit(); // Creer un produit avec le id contenu dans le mAll_reservations.get(position).getProduit_id()

        holder.nomProduit.setText(Integer.toString(mAll_reservations.get(position).getProduit_id())); // Aller chercher le nom du produit avec le id dans la reservation
        holder.nomCategorie.setText(Integer.toString(mAll_reservations.get(position).getId()));       // Aller chercher le nom de la categorie avec le id contenu dans le produit
        holder.etat.setText(Integer.toString(mAll_reservations.get(position).getEtat_id()));          // Aller chercher le nom de l'etat de la reservation avec le id dans la reservation
        holder.qte.setText(Integer.toString(mAll_reservations.get(position).getQuantite()));          // La quantité est la bonne.
       // holder.image_prod.setImageResource();                                                       // Aller chercher l'image du produit.
    }

    @Override
    public int getItemCount() {
        return mAll_reservations.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nomProduit;
        private TextView nomCategorie;
        private TextView qte;
        private TextView etat;
        private ImageView image_prod;

        private Button btn_modifier;
        private Button btn_annuler;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomProduit = itemView.findViewById(R.id.TextView_voirReservation_nom);
            nomCategorie = itemView.findViewById(R.id.textView_voirReservation_categorie);
            qte = itemView.findViewById(R.id.textView_voirReservation_qte);
            etat = itemView.findViewById(R.id.textView_voirReservation_etat);
            image_prod = itemView.findViewById(R.id.imageView_voirReservation_img);

            btn_modifier = itemView.findViewById(R.id.btn_VoirReservation_modifier);
            btn_modifier.setText(R.string.btn_modifier);
            //btn_modifier.setOnClickListener();
        }
    }


}
