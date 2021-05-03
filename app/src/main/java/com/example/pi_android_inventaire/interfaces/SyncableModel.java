/****************************************
 Fichier : SyncableModel.java
 Auteur : Marc Antoine Griffiths Lorange
 Fonctionnalité : Interface permettant à un model de données d'être syncronisé par le DbSyncService.
 Date : 2021-04-28
 Vérification :
 Date               Nom                   Approuvé
 =========================================================

 Historique de modifications :
 Date               Nom                   Description
 =========================================================
 ****************************************/
package com.example.pi_android_inventaire.interfaces;

import android.database.Cursor;

public interface SyncableModel {

    SyncableModel initializeFromCursor(Cursor cursor);
    void insertIntoDb();
    void deleteFromDb();
}
