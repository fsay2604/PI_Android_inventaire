/****************************************
 Fichier : Connexion.java
 Auteur : Philippe Boulanger
 Fonctionnalité : a-01 - Gestion de connexion
Une page de connexion permet de se connnecter à l'aide d'un courriel et d'un mot de passe
La récupération de mot de psse est possible en demandant un lien de récupération
qui sera envoyé à une adresse entrée par l'utilisateur.

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pi_android_inventaire.PIAndroidInventaire;
import com.example.pi_android_inventaire.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
    Button mConnexionBtn, mProduit, mReservation, mCompte;
    TextView forgotTextLink, singUpBtn;
    ProgressBar progessBar;




    /**
     * Initialistaion de éléments de la page
     * <p>
     * Attribution des écouteurs d'évènements sur le champs courriel et mot de passe
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
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        setTitle("Connexion");

        mCourriel = findViewById(R.id.emailText);
        mMotDePasse = findViewById(R.id.passwordText);
        mConnexionBtn = findViewById(R.id.connexionBtn);
        forgotTextLink = findViewById(R.id.forgotPassword);
        singUpBtn = findViewById(R.id.register_btn);

        String courriel = mCourriel.getText().toString();
        String motDePasse = mMotDePasse.getText().toString();
        Log.i("courriel",courriel);

        // Recuperation des btn
        mProduit = (Button) findViewById(R.id.btn_produit);

        mProduit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentListeProduit = new Intent(Connexion.this, Liste_produits.class);
                startActivity(intentListeProduit);
            }
        });
        mReservation = (Button) findViewById(R.id.btn_reservation);
        // btn_reservation.setText(R.string.btn_reservation);
        mReservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentListeProduit = new Intent(Connexion.this, VoirReservations.class);
                startActivity(intentListeProduit);
            }
        });

        mCompte = (Button) findViewById(R.id.btn_compte);
        // btn_compte.setText(R.string.btn_compte);
        mCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Connexion.this, "You click compte button", Toast.LENGTH_LONG).show();
            }
        });

        singUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirection vers la page de connexion
                Intent intentConnexion = new Intent(Connexion.this, RegisterUser.class);
                startActivity(intentConnexion);
            }
        });
        mConnexionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFormField();
                MainActivity.currentUser = PIAndroidInventaire.apiCaller.loginUser( mCourriel.getText().toString(),  mMotDePasse.getText().toString(), PIAndroidInventaire.apiUrlDomain + "login");
                Intent intentConnexion = new Intent(Connexion.this, MainActivity.class);
                startActivity(intentConnexion);
               // POSTStringAndJSONRequest();
            }

            private void checkFormField() {
                if (TextUtils.isEmpty(courriel)) {
                    mCourriel.setError("Entrer votre courriel");
                    return;
                }
                if (TextUtils.isEmpty(motDePasse)) {
                    mMotDePasse.setError("Mot de passe requis");
                    return;
                }
                if (motDePasse.length() < 6) {
                    mMotDePasse.setError("Doit contenir au moins 6 charactères");
                    return;
                }
            }

            private void POSTStringAndJSONRequest() {

                RequestQueue queue = Volley.newRequestQueue(Connexion.this);

                VolleyLog.DEBUG = true;
                String uri = "https://0d6ecac9266f.ngrok.io/Jsonlogin";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, uri, new Response.Listener() {
                    /**
                     * Called when a response is received.
                     *
                     * @param response
                     */
                    @Override
                    public void onResponse(Object response) {

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    public Priority getPriority() {
                        return Priority.LOW;
                    }

                    @Override
                    public Map getParams() {
                        Map params = new HashMap();

                        params.put("email", courriel);
                        params.put("password",motDePasse);


                        return params;
                    }

                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                        return headers;
                    }

                };


                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("email", courriel);
                    jsonObject.put("password",motDePasse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(uri, jsonObject, new Response.Listener() {
                    /**
                     * Called when a response is received.
                     *
                     * @param response
                     */
                    @Override
                    public void onResponse(Object response) {

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

                    @Override
                    public int getMethod() {
                        return Method.POST;
                    }

                    @Override
                    public Priority getPriority() {
                        return Priority.NORMAL;
                    }
                };


                StringRequest stringRequestPOSTJSON = new StringRequest(Request.Method.POST, uri, new Response.Listener() {
                    /**
                     * Called when a response is received.
                     *
                     * @param response
                     */
                    @Override
                    public void onResponse(Object response) {

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    public Priority getPriority() {
                        return Priority.HIGH;
                    }

                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        return headers;
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("password",courriel);
                            jsonObject.put("password",motDePasse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String requestBody = jsonObject.toString();


                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }


                };

               //queue.add(stringRequest);
                //queue.add(jsonObjectRequest);
                queue.add(stringRequestPOSTJSON);
            }

/*                Intent intentmain = new Intent(Connexion.this, MainActivity.class);
                startActivity(intentmain);*/

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

