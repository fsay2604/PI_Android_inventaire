package com.example.pi_android_inventaire.interfaces;

import android.database.Cursor;

public interface SyncableModel {

    SyncableModel initializeFromCursor(Cursor cursor);
    void insertIntoDb();
    void deleteFromDb();
}
