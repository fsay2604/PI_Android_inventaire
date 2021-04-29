/****************************************
 Fichier : ApiCaller.java
 Auteur : Marc Antoine Griffiths Lorange
 Fonctionnalité : Classe permettant d'effectuer la gestion des messages de notifications et de
 synchronisation de base de données.
 Date : 2021-04-28
 Vérification :
 Date               Nom                   Approuvé
 =========================================================

 Historique de modifications :
 Date               Nom                   Description
 =========================================================
 ****************************************/

package com.example.pi_android_inventaire.network;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.activities.MainActivity;
import com.example.pi_android_inventaire.models.User;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FireBaseMessagingService extends FirebaseMessagingService {

    private static final String apiUpdateTokenBaseUrl = "https://7cb6dae8616b.ngrok.io/api/users/";

    @Override
    public void onNewToken(String token) {
        Log.d("FirebaseToken", "Refreshed token: " + token);

        // Sends the FCM registration token to your app server.
        if(MainActivity.currentUser != null){
            sendRegistrationToServer(token);
        }
    }


    // Called when message is received.
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0){
            Log.e("FireBase message", remoteMessage.getData().toString());
        }
    }

    public static void sendRegistrationToServer(String token) {
        String response = MainActivity.apiCaller.putSingleOrDefault(MainActivity.currentUser, apiUpdateTokenBaseUrl +  MainActivity.currentUser.getId());
        int allo = 123;
    }
}
