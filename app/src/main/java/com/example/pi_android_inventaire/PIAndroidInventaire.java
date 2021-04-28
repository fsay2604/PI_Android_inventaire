package com.example.pi_android_inventaire;

import android.app.Application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PIAndroidInventaire extends Application {
    public static ExecutorService executorService = Executors.newFixedThreadPool(4);
}
