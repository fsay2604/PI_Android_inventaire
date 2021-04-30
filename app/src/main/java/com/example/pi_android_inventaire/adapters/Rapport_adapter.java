package com.example.pi_android_inventaire.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.activities.Liste_Rappots;
import com.example.pi_android_inventaire.activities.infos_Rapport;
import com.example.pi_android_inventaire.models.Product;
import com.example.pi_android_inventaire.models.Rapport;

import java.util.ArrayList;

public class Rapport_adapter extends RecyclerView.Adapter<Rapport_adapter.MyViewHolder>{

    //String data1[], data2[], data3[];
    Context context;
    private ArrayList<Rapport> rapports;
    private AdapterView.OnItemClickListener mListener;


    /*public Rapport_adapter(Context ct, String s1[], String s2[],String s3[]){
        context = ct;
        data1 = s1;
        data2 = s2;
        data3 = s3;
    }*/

    public Rapport_adapter(Context c, ArrayList<Rapport> all_rapports)
    {
        context =c;
        rapports = all_rapports;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_row_rapport,parent,false);
        return new MyViewHolder(view);
    }

    /*@Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);
        holder.myText3.setText(data3[position]);
    }*/

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.produit_id.setText(Integer.toString(rapports.get(position).getProduit_id()));
//        holder.user_id.setText(Integer.toString(rapports.get(position).getUser_id()));
        holder.type_rapport_id.setText(Integer.toString(rapports.get(position).getType_rapport_id()));
          holder.description.setText(rapports.get(position).getDescription());
      //  holder.id.setText(Integer.toString(rapports.get(position).getProduit_id()));

    }

    @Override
    public int getItemCount() {
        return rapports.size();
    }

    /*@Override
    public int getItemCount() {
        return images.length;
    }*/

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView id, produit_id,user_id,type_rapport_id,description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            id = itemView.findViewById(R.id.id);
            produit_id = itemView.findViewById(R.id.textViewNomProduitRapport);
                //user_id ;
                //type_rapport_id;
            type_rapport_id = itemView.findViewById(R.id.textViewEtatRapport);
            description = itemView.findViewById(R.id.textViewCommentaireRapport);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            Intent intentRapport = new Intent(context, infos_Rapport.class);
            intentRapport.putExtra("nom",produit_id.getText().toString());
            intentRapport.putExtra("description",rapports.get(Integer.valueOf(id.getText().toString())).getDescription());
            intentRapport.putExtra("user_id",user_id.getText().toString());
            intentRapport.putExtra("type_rapport_id",type_rapport_id.getText().toString());
            intentRapport.putExtra("id",id.getText().toString());
            context.startActivity(intentRapport);
        }
    }





    /*public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView myText1, myText2,myText3;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            myText1 = itemView.findViewById(R.id.textViewNomProduitRapport);
            myText2 = itemView.findViewById(R.id.textViewEtatRapport);
            myText3 = itemView.findViewById(R.id.textViewCommentaireRapport);

        }

        @Override
        public void onClick(View view) {

            for(int i = 0; i<getItemCount(); i++){
                passData(i);
            }
        }

        private void passData(int id){
            Intent intent = new Intent(context, Liste_Rappots.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            String t1 = myText1.getText().toString();
            String t2 = myText2.getText().toString();
            String t3 = myText3.getText().toString();


            Bundle b = new Bundle();
            intent.putExtra("rapport",b);
            intent.putExtra("nom",t1);
            intent.putExtra("etat",t2);
            intent.putExtra("commentaire",t3);

            context.startActivity(intent);
        }
    }*/
}
