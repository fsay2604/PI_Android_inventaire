package com.example.pi_android_inventaire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Connexion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        EditText courrielIn = findViewById(R.id.emailText);
        EditText mdpIn = findViewById(R.id.passwordText);
        Button connexionBtn =findViewById(R.id.connexionBtn);

        connexionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courriel = courrielIn.getText().toString();
                String motDePasse =mdpIn.getText().toString();

                Intent intentmain = new Intent(Connexion.this,MainActivity.class);
                startActivity(intentmain);
               // Toast.makeText(Connexion.this, "Courriel: "+courriel+" Mot de passe: "+motDePasse,Toast.LENGTH_LONG).show();
            }
        });
    }
}