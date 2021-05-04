/****************************************
 Fichier : RegisterUser.java
 Auteur : Philippe Boulanger
 Fonctionnalité : a-01 - Gestion d'enregistrenment
 Les information entrées par l'utilisateur sont envoyée à l'api du site web par requête post
 à l'aide de la méthode newrequestQueue() de la libtairie Volley.

 Date : 2021-04-29

 Vérification :
 Date           Nom             Approuvé
 =========================================================


 Historique de modifications :
 Date           Nom             Description
 =========================================================
 2021-05-02      Philippe Boulanger
 ****************************************/
package com.example.pi_android_inventaire.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pi_android_inventaire.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {
    Button btn_register;
    EditText edEmail, edPassword,edNom,edPrenom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register =findViewById(R.id.registerBtn);
        edEmail=findViewById(R.id.r_email);
        edPassword=findViewById(R.id.r_Password);
        edNom = findViewById(R.id.r_nom);
        edPrenom = findViewById(R.id.r_prenom);
        //to do ajouter un bouton password copié

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRequest();
            }
        });
    }

    private void postRequest(){
        RequestQueue queue = Volley.newRequestQueue(RegisterUser.this);
        String url ="  https://d9f5e93b7d80.ngrok.io/api/users";

        JSONObject obj = new JSONObject();
        try {
            obj.put("content-type", "application/json");
            obj.put("email",edEmail.getText().toString());
            obj.put("password",edPassword.getText().toString());
            obj.put("prenom",edPrenom.getText().toString());
            obj.put("nom",edNom.getText().toString());
            obj.put("firebaseToken",null);
            Log.d("object","my object");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Request a string response from the provided URL.
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,obj, new Response.Listener<JSONObject>() {

            /**
             * Called when a response is received.
             *
             * @param response
             */
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(RegisterUser.this, response.toString(), Toast.LENGTH_LONG).show();
                Intent intentConnexion = new Intent(RegisterUser.this,Connexion.class);
                startActivity(intentConnexion);
            }
        }, new Response.ErrorListener() {
            /**
             * Callback method that an error has been occurred with the provided error code and optional
             * user-readable message.
             *
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterUser.this, "Somethsing wrong with "+edEmail.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(request);
    }

}