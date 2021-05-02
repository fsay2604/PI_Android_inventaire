package com.example.pi_android_inventaire.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.adapters.Rapport_adapter;
import com.example.pi_android_inventaire.models.Rapport;
import com.example.pi_android_inventaire.models.Reservation;

import java.util.ArrayList;

public class Liste_Rappots extends AppCompatActivity {

    RecyclerView recyclerView_Rapport;
    private ArrayList<Rapport> rapports;

    //String s1[], s2[],s3[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste__rappots);

        rapports = new ArrayList<Rapport>();

        /*s1 = getResources().getStringArray(R.array.nom);
        s2 = getResources().getStringArray(R.array.etat);
        s3 = getResources().getStringArray(R.array.commentaire);*/

        // Remplit le array avec des donnees fictives, A changer pour avoir les donnes la BD.
       /* for(int i =0; i<3; i++) {
            Rapport r = new Rapport();
            r.setId(i);
            r.setProduit_id(1);
            r.setUser_id(1);
            r.setType_rapport_id(1);
            r.setDescription("Rapport "+i);
            rapports.add(r);
        }*/
        rapports = Rapport.get_all_rapport(13);

        recyclerView_Rapport = (RecyclerView)findViewById(R.id.recyclerViewRapport);
        Rapport_adapter myAdapter = new Rapport_adapter(this, rapports);
        recyclerView_Rapport.setAdapter(myAdapter);
        recyclerView_Rapport.setLayoutManager(new LinearLayoutManager(this));

       /* Rapport_adapter myAdapter= new Rapport_adapter(this,s1,s2,s3);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));*/
    }
}