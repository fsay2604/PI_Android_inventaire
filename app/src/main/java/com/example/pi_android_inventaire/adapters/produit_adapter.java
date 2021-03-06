/****************************************
 Fichier : produit_adapter.java
 Auteur : Julien Fortier
 Fonctionnalité : L'objet adapter hérite de la classe RecyclerView ce qui lui permet d'ajouter
 un prodduit dans le recyclerView.
 Date : 2021-04-27
 Vérification :
 Date                       Nom                   Approuvé
 =========================================================

 Historique de modifications :
 Date                       Nom                 Description
 =========================================================
 2021-05-02         Philippe Boulanger              Oui
 2021-05-02         David Marcoux                   Oui

 ****************************************/
package com.example.pi_android_inventaire.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.activities.Liste_produits;
import com.example.pi_android_inventaire.activities.infos_produit;
import com.example.pi_android_inventaire.models.Product;

import java.util.ArrayList;

public class produit_adapter extends RecyclerView.Adapter<produit_adapter.MyViewHolder>{
    private ArrayList<Product> all_products;
    private Context context;
    private AdapterView.OnItemClickListener mListener;

    public produit_adapter(Context c, ArrayList<Product> products)
    {
        context =c;
        all_products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_my_row_produit,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nom.setText(all_products.get(position).getNom());
        holder.categorie.setText(all_products.get(position).getCategorieName(all_products.get(position).getCategorie()));
        holder.qte.setText(Integer.toString(all_products.get(position).getQteDisponible()));
        holder.id.setText(Integer.toString(all_products.get(position).getId()));
        //holder.image.setImageResource(position);

    }

    @Override
    public int getItemCount() {
        return all_products.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nom;
        TextView id;
        TextView categorie;
        TextView qte;
        ImageView image;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            context = itemView.getContext();
            nom = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);
            categorie = itemView.findViewById(R.id.categorie);
            qte = itemView.findViewById(R.id.qte);
            //image = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Product produit = new Product();
            produit = produit.get_produit_by_id(Integer.valueOf(id.getText().toString()));

            Intent intent = new Intent(context, infos_produit.class);
            intent.putExtra("produit",produit);
            context.startActivity(intent);
        }
    }
}
