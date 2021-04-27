/****************************************
 Fichier : ApiCaller.java
 Auteur : Marc Antoine Griffiths Lorange
 Fonctionnalité : Classe permettant d'effectuer tous les appels à l'api fait par l'application.
 Date : 2021-04-26
 Vérification :
 Date               Nom                   Approuvé
 =========================================================
 Historique de modifications :
 Date               Nom                   Description
 =========================================================
 ****************************************/

package com.example.pi_android_inventaire.network;

import androidx.annotation.WorkerThread;

import com.example.pi_android_inventaire.PIAndroidInventaire;
import com.example.pi_android_inventaire.utils.Result;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class Factory<T> {
    public List<T> deserialize(String json, Class<T> klass) {
        Gson gson = new Gson();
        return gson.fromJson(json, new ListOfSomething<T>(klass));
    }
}
public class ApiCaller<T> implements Callable<T> {

    public static final int DEFAULT_BUFFER_SIZE = 8192;
    private static ExecutorService executor;

    public ApiCaller(ExecutorService executor) {
        this.executor = PIAndroidInventaire.executorService;
    }

    @Override
    public T call() throws Exception {
        return null;
    }

    @WorkerThread
    public ArrayList<T> getList(Class<T> requestedObject,
                                String urlString) {
        Future<Result<T>> future = executor.submit(new Callable<Result<T>>() {
            public Result<T> call() {
                Result<T> response = null;
                try {
                    // The fetched response
                    response = callApi(requestedObject, urlString);
                    return response;
                } catch (Exception e) {
                    Result<T> errorResult = new Result.Error<>(e);
                }
                return response;
            }
        });
        Result<T> returned = null;
        T value = null;
        try {
             returned = future.get();
            if (returned instanceof Result.Success) {
                value = ((Result.Success<T>) returned).data;
            }
            else
            {
                value = null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return (ArrayList<T>) value;
    }

    private Result<T> callApi(Class<T> requestedObject, String urlString) {
        // The fetched response
        String response = "";

        try {
            // Creating the url object to make our query
            URL url = new URL(urlString);

            // Opening the connection to the requested url
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Opening the stream in order to be able to read the response.
            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
             response = readResponse(in);

            // Creating an object from the received response string (Assuming this is a JSON string)
            JSONObject jsonObject = new JSONObject(response);

            Gson gson = new Gson();
            JsonObject object = gson.fromJson(response, JsonObject.class);
            // Extracting the collection of object received from the api
            //Constructor[] constructor = requestedObject.getConstructors();
//            ArrayList<T> retrieved = new ArrayList<>();
//            for (T o:
//                 objects) {
//                //retrieved.add(gson.fromJson( o, requestedObject));
//            }
            JsonElement allo = object.get("hydra:member");
            List<T> list = new Factory<T>().deserialize(allo.toString(), requestedObject);

            return new Result.Success<T>((T) list);
        }
        catch (MalformedURLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
//        catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        catch (InstantiationException e) {
//            e.printStackTrace();
//        }
//        catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    private List<T> fromJsonList(String json, Class<T> klass) {
        Gson gson = new Gson();
        return gson.fromJson(json, new ListOfSomething<T>(klass));
    }

    private String readResponse(BufferedInputStream stream) {
        // Creating our ByteArrayOutputStream to contain the bytes we will read
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        try {
            // Number of bytes read
            int length;
            // Bytes buffer for the bytes we read
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];

            // Fetching the entire response from the stream into the ByteArrayOutputStream
            while ((length = stream.read(buffer)) != -1){
                result.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Returning the result after we read the entire response.
        return result.toString();
    }


}
