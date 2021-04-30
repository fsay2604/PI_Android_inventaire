/****************************************
 Fichier : ApiCaller.java
 Auteur : Marc Antoine Griffiths Lorange
 Fonctionnalité : Classe permettant d'effectuer la synchronisation de la base de données locale
 de l'applicaiont avec la base de données principale distante.
 Date : 2021-04-29
 Vérification :
 Date               Nom                   Approuvé
 =========================================================

 Historique de modifications :
 Date               Nom                   Description
 =========================================================
 ****************************************/
package com.example.pi_android_inventaire.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pi_android_inventaire.PIAndroidInventaire;
import com.example.pi_android_inventaire.interfaces.SyncableModel;
import com.example.pi_android_inventaire.models.Product;
import com.example.pi_android_inventaire.models.Reservation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class DbSyncService {

    private static ExecutorService executor;

    public DbSyncService()
    {
        /* Storing the reference to our executorService
         * to be able to call our methods asynchronously
         */
        this.executor = PIAndroidInventaire.executorService;
    }

    /**
     * Starts a synchronisation operation with the remote database on the requested table
     * @param tableName the name of the table that needs to be updated
     */
    public void syncTable(String tableName)
    {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // Determining which type of data we are syncing
                switch (tableName){
                    case "produit":
                        ArrayList<Product> remoteProducts =  fetchFromRemoteDb(Product.class,
                                PIAndroidInventaire.apiUrlDomain + "produits?page=1");
                        synchronizeLocalDatabase(Product.class,remoteProducts, tableName);
                        break;
                    case "reservation":
                        ArrayList<Reservation> remoteReservations =  fetchFromRemoteDb(Reservation.class,
                                PIAndroidInventaire.apiUrlDomain + "reservations?page=1");
                        synchronizeLocalDatabase(Reservation.class,remoteReservations, tableName);
                        break;
                    case "rapport":
                        break;
                    case "utilisateur":
                        break;
                    case "categorie":
                        break;
                    default:
                        break;

                }
            }
        });
    }

    private <T> ArrayList<T> fetchFromRemoteDb(Class<T> requestedType, String url)
    {
        return PIAndroidInventaire.apiCaller.getListSync(requestedType, url);
    }

    private <T extends SyncableModel> ArrayList<T> fetchFromLocalDb(Class <T> impl, String tableName)
    {
        SQLiteDatabase localDb = PIAndroidInventaire.getDatabaseInstance();

        SyncableModel object;

        ArrayList<T> retreivedObjects = null;

        Cursor cursor = localDb.query(tableName, null, null,
               null, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.getCount() > 0)
        {
            retreivedObjects = new ArrayList<T>();
            try {
                Constructor<T> ctor = impl.getConstructor();
                // Looping throught every row and initializing an object from it
                do {
                    object = ctor.newInstance();
                    T initialized = impl.cast(object.initializeFromCursor(cursor));
                    retreivedObjects.add(initialized);
                }
                while (cursor.moveToNext());

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            } catch (InstantiationException e) {
                e.printStackTrace();
                return null;
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        }

        // return the ArrayList of objects
        return retreivedObjects;
    }

    private <T extends SyncableModel> void synchronizeLocalDatabase(Class<T> requestedType,ArrayList<T> remoteList, String tableName)
    {
        // Iterates throught the new list of objects and inserts them into the database
        for (SyncableModel syncable :
                remoteList) {
            syncable.insertIntoDb();
        }

        // Retreiving every objects from the local database
        ArrayList<T> localProducts = fetchFromLocalDb(requestedType, tableName);

        // Iterates throught the list of objects in the database and removes them if they are not in the remoteList
        if (localProducts != null)
        {
            for (SyncableModel syncable :
                    localProducts) {
                if (!remoteList.contains(syncable)) {
                    syncable.deleteFromDb();
                }
            }
        }
    }
}
