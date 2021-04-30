/****************************************
 Fichier : Connexion.java
 Auteur : Philippe Boulanger
 Fonctionnalité : a-01 - Gestion de connexion
Une page de connexion permet de se connnecter à l'aide d'un courriel et d'un mot de passe
La récupération de mot de psse est possible en demandant un lien de récupération
qui sera envoyé à une adressse entrée par l'utilisateur.

 Date : 2021-04-28

 Vérification :
 Date           Nom             Approuvé
 =========================================================


 Historique de modifications :
 Date           Nom             Description
 =========================================================
2021-04-29      Philippe Boulanger
 ****************************************/
package com.example.pi_android_inventaire.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * <b>La classe connexion permet à l'uilisateur de se connecter</b>
 * <p>La connexionn est établie à l'aide de... quand l'utilisateur entre :
 * <ul>
 * <li>Une adresse courriel valide</li>
 * <li>Un mot de passe composé d'au moins 6 characctères</li>
 * </ul>
 * </p>
 * <p>De plus, un option de récupération de mot de passse est disponnible "Mot de passe oublié"</p>
 * <p>lorsque l'utilisateur en fait la demande une boîte de dialogue lui demande une adresse courriel pour l'envoi d'un lien de récupération</p>
 *
 * @author Philippe Boulanger
 * @version 1.0
 */
public class Connexion extends AppCompatActivity {

     EditText mCourriel;
     EditText mMotDePasse;
     Button mConnexionBtn;
     TextView forgotTextLink;
     ProgressBar progessBar;
/**
 * Initialistaion de éléments de la page
 *
 *Attribution des écouteurs d'évènements sur le champs courriel et mot de passe
 * <p>Mise en place d'un message d'erreur de courriel requis</p>
 * <p>Mise en place d'un message d'erreur de mot de passe requis</p>
 * <p>Mise en place d'un format de mot de passe requis</p>
 * <p>Écouteur d'évènement sur le lien de récupération de mot de passe</p>
 * <p>Une boite de dialogue est créée avec :
 * <ul>
 * <li>Un titre</li>
 * <li>Un message</li>
 * <li>Un champ d'entrée</li>
 * <li>Des boutons 'oui' ou 'non' </li>
 * </ul></p>
 * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        setTitle("Connexion");

        mCourriel = findViewById(R.id.emailText);
        mMotDePasse = findViewById(R.id.passwordText);
        mConnexionBtn = findViewById(R.id.connexionBtn);
        forgotTextLink = findViewById(R.id.forgotPassword);

        mConnexionBtn.setOnClickListener(new View.OnClickListener() {
            /*
             * Attribution des écouteurs d'évènements sur le champs courriel et mot de passe
             * Mise en place d'un message de courriel requis
             * Mise en place d'un message de mot de passe requis
             * Mise en place d'un format de mot de passe requis
             */
            @Override
            public void onClick(View v) {
                String courriel = mCourriel.getText().toString();
                String motDePasse = mMotDePasse.getText().toString();
                if (TextUtils.isEmpty(courriel)) {
                    mCourriel.setError("Entrer votre courriel");
                    return;
                }
                if (TextUtils.isEmpty(motDePasse)) {
                    mMotDePasse.setError("Mot de passe requis");
                    return;
                }
                if (courriel.length()< 6) {
                    mMotDePasse.setError("Doit contenir au moins 6 charactères");
                    return;
                }

                //progessBar.setVisibility(View.VISIBLE);

                Intent intentmain = new Intent(Connexion.this,MainActivity.class);
                intentmain.putExtra("email", courriel);
                intentmain.putExtra("password", motDePasse);
                startActivity(intentmain);
                // Toast.makeText(Connexion.this, "Courriel: "+courriel+" Mot de passe: "+motDePasse,Toast.LENGTH_LONG).show();
            }

        });

        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            /*
             * Écouteur d'évènement sur le lien de récupération de mot de passe
             * <p>Une boite de dialogue est créée avec : Un titre, Un message, Un champ d'entrée, Des boutons 'oui' ou 'non'
             */
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Mot d passe");
                passwordResetDialog.setMessage("Entrer votre courriel pour recevoir un lien de changement de mot  de passe");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    //TO DO...

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Extrait le courriel et envoie le lien

                        String mail = resetMail.getText().toString();
                        Toast.makeText(Connexion.this, "Courriel: "+resetMail,Toast.LENGTH_LONG).show();
                        //fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>(){...}
                    }
                });
                passwordResetDialog.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //ferme le dialog
                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }
}