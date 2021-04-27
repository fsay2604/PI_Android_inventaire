package com.example.pi_android_inventaire.network;

import com.example.pi_android_inventaire.utils.Result;

public interface ApiCallerCallback<T>{
    void onComplete(Result<T> result);
}
