/****************************************
 Fichier : MainActivity.java
 Auteur : Francois Charles Hebert
 Fonctionnalité :
    - Page d'accueil avec un menu.

 Date : 2021-04-26

 Vérification :
 Date           Nom             Approuvé
 =========================================================


 Historique de modifications :
 Date           Nom             Description
 =========================================================

 ****************************************/

package com.example.pi_android_inventaire.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.pi_android_inventaire.PIAndroidInventaire;
import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.activities.Liste_produits;
import com.example.pi_android_inventaire.models.Product;
import com.example.pi_android_inventaire.models.Reservation;
import com.example.pi_android_inventaire.models.User;
import com.example.pi_android_inventaire.network.ApiCaller;
import com.example.pi_android_inventaire.network.ApiCallerCallback;
import com.example.pi_android_inventaire.network.FireBaseMessagingService;
import com.example.pi_android_inventaire.utils.Result;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Boutons du menu
    private  Button btn_produit;
    private  Button btn_reservation;
    private  Button btn_compte;
    private  Button btn_connexion;
    private  Button btn_inscription;




    // Utilisateur de l'appli??
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Accueil");

        // TEST DU API CALLER

        ArrayList<Product> products = PIAndroidInventaire.apiCaller.getList(Product.class,"https://7cb6dae8616b.ngrok.io/api/produits?page=1");

        Product product = PIAndroidInventaire.apiCaller.getSingleOrDefault(Product.class, "https://7cb6dae8616b.ngrok.io/api/produits/2");

        int alllo = 0;
        // FIN TEST DU API CALLER

        // TEST DE FirebaseMessaging
        Bundle b = getIntent().getExtras();
        if(b != null){
            // Extracting the user info from the bundled data
            String email = b.getString("email");
            String password = b.getString("password");

            /* Authenticating the User via the API and setting the current application's user
             * to the retreived user from the database
             */
            currentUser = PIAndroidInventaire.apiCaller.loginUser(email, password, "https://7cb6dae8616b.ngrok.io/api/login");

            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w("FirebaseToken", "Fetching FCM registration token failed", task.getException());
                                return;
                            }

                            // Get new FCM registration token
                            String token = task.getResult();

                            // Log and toast
                            String msg = getString(R.string.msg_token_fmt, token);
                            Log.d("FirebaseToken", msg);
                            /* Send the token to the server if it is different from the one
                             * currently stored in the remote database
                             */
                            if ( !MainActivity.currentUser.getFirebaseToken().equals(token) )
                            {
                                MainActivity.currentUser.setFirebaseToken(token);
                                FireBaseMessagingService.sendRegistrationToServer(token);
                            }
                            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
        }



        // FIN TEST De FirebaseMessaging

        // Menu
        setupMenu();

        // Test Query BD insert
//        Reservation TestReservation = new Reservation(1,1,1,1,"2021-04-26",5,"");
       // TestReservation.put_in_db();

        /*TestReservation.setDate_retour_reel("2025-06-27");
        TestReservation.update_db();

        Product p = new Product();
        p.setId(800);
        p.setNom("Un produit");
        p.setCategorie(1);
        p.setImage("allo.png");
        p.setQteDisponible(50);
        p.setDescription("Salut je suis le produit"+Integer.toString(p.getId()));

        TestReservation.setProduit_id(p.getId());
        TestReservation.update_db();*/
    }

    /**
     * Fonction qui initialise les bouttons du menu afin de rediriger vers les bonnes pages
     */
    private void setupMenu()
    {
        // Recuperation des btn
        btn_produit = (Button) findViewById(R.id.btn_produit);
       // btn_produit.setText(R.string.btn_produit);
        btn_produit.setOnClickListener(this);

        btn_reservation = (Button) findViewById(R.id.btn_reservation);
       // btn_reservation.setText(R.string.btn_reservation);
        btn_reservation.setOnClickListener(this);

        btn_compte = (Button) findViewById(R.id.btn_compte);
       // btn_compte.setText(R.string.btn_compte);
        btn_compte.setOnClickListener(this);

        btn_connexion=(Button) findViewById(R.id.login_btn);
        btn_connexion.setOnClickListener(this);

        btn_inscription=(Button) findViewById(R.id.Suscribe_btn);
        btn_inscription.setOnClickListener(this);
    }

    /**
     * Fonction qui gere le listener des boutons.
     * @param v     represente la vue
     */
    @Override
    public void onClick(View v) {

        // Switch case en fonction du bouton appuyer
        switch (v.getId()) {
            case R.id.btn_produit:
                Intent intentListeProduit = new Intent(this, Liste_produits.class);
                startActivity(intentListeProduit);
                break;
            case R.id.btn_reservation:
                // redirection vers la page pour faire une reservation
                Intent intentVoirReservation = new Intent(this, VoirReservations.class);
                startActivity(intentVoirReservation);
                break;
            case R.id.login_btn:
                // redirection vers la page de connexion
                Intent intentConnexion = new Intent(MainActivity.this,Connexion.class);
                startActivity(intentConnexion);
                break;
            case R.id.btn_inscription:
                // redirection vers la page d'enregistrement
                break;
            default:
                break;
        }
    }
} // Main activity Class ends here