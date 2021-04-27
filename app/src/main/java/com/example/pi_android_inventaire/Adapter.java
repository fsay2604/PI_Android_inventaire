/****************************************
 Fichier : Adapter.java
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

 ****************************************/
package com.example.pi_android_inventaire;

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

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
    private String nom[];
    private String categorie[];
    private String qte[];
    private int images[];
    private Context context;
    private AdapterView.OnItemClickListener mListener;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_my_row_produit,parent,false);
        return new MyViewHolder(view);
    }
    public Adapter(Context context, String[] nom, String[] categorie, String[] qte, int[] images){
        this.context = context;
        this.nom = nom;
        this.categorie = categorie;
        this.qte = qte;
        this.images = images;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nom.setText(nom[position]);
        holder.categorie.setText(categorie[position]);
        holder.qte.setText(qte[position]);
        holder.image.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return images.length;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nom;
        TextView categorie;
        TextView qte;
        ImageView image;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            nom = (TextView)itemView.findViewById(R.id.name);
            categorie = (TextView)itemView.findViewById(R.id.categorie);
            qte = (TextView)itemView.findViewById(R.id.quantite);
            image = (ImageView) itemView.findViewById(R.id.imageButton);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,infos_produit.class);
            intent.putExtra("nom",nom.getText().toString());
            intent.putExtra("categorie",categorie.getText().toString());
            intent.putExtra("qte",qte.getText().toString());
            intent.putExtra("image",images[getLayoutPosition()]);
            context.startActivity(intent);
        }
    }
}
