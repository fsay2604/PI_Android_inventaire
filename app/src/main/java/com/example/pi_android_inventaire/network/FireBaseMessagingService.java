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

import com.example.pi_android_inventaire.PIAndroidInventaire;
import com.example.pi_android_inventaire.R;
import com.example.pi_android_inventaire.activities.MainActivity;
import com.example.pi_android_inventaire.models.User;
import com.example.pi_android_inventaire.utils.DbSyncService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FireBaseMessagingService extends FirebaseMessagingService {

    private static final String apiUpdateTokenBaseUrl = PIAndroidInventaire.apiUrlDomain + "users/";

    @Override
    public void onNewToken(String token) {
        Log.d("FirebaseToken", "Refreshed token: " + token);

        // Sends the FCM registration token to the app server.
        if(MainActivity.currentUser != null){
            sendRegistrationToServer(token);
        }
    }


    // Called when message is received.
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0){
            Log.e("FireBase message", remoteMessage.getData().toString());

            Map<String, String> messageData = remoteMessage.getData();

            // If one or more tables needs to be updated
            if (messageData.containsKey("updatedTable"))
            {
                // Getting the table name
                String[] tables = messageData.get("updatedTable").split(":");

                // Creating our dbSyncerService to sync the database
                DbSyncService dbSyncer = new DbSyncService();

                // Syncing all the received tables
                for (String tableName :
                        tables) {
                    // Requesting a table update for the specified table to the DbSyncService
                    dbSyncer.syncTable(tableName);
                }




            }

        }
    }

    public static void sendRegistrationToServer(String token) {
        String response = PIAndroidInventaire.apiCaller.putSingleOrDefault(MainActivity.currentUser, apiUpdateTokenBaseUrl +  MainActivity.currentUser.getId());
        int allo = 123;
    }
}
