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

import android.util.Log;

import com.example.pi_android_inventaire.PIAndroidInventaire;
import com.example.pi_android_inventaire.models.User;
import com.example.pi_android_inventaire.utils.Result;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class Factory<T> {
    public List<T> deserialize(String json, Class<T> klass) {
        Gson gson = new Gson();
        return gson.fromJson(json, new ListOfSomething<T>(klass));
    }
}


/**
 * TODO: Verify if it is possible to avoid on type conversion by directly returning a JsonElement directly from the callApi() method using this function -> gson.toJsonTree()
 */

public class ApiCaller {

    public static final int DEFAULT_BUFFER_SIZE = 8192;
    private static ExecutorService executor;
    private static Gson gson = null;

    public ApiCaller(ExecutorService executor) {
        /* Storing the reference to our executorService
         * to be able to call our methods asynchronously
         */
        this.executor = PIAndroidInventaire.executorService;

        // Creating the Gson Parser to properly deserialize our responses into Java objects
        this.gson = new Gson();
    }


    public <T> ArrayList<T> getListSync(Class<T> requestedObject, String urlString){
        // Starting the API Request on the same thread.
        Result<List<T>> response;
        List<T> value;
        try {
            // The fetched response
            response = parseJsonList(requestedObject, urlString);
            if (response instanceof Result.Success) {
                value = ((Result.Success<List<T>>) response).data;
                return (ArrayList<T>) value;
            }
        } catch (Exception e) {
            // Retrieve the error message
            Result<T> errorResult = new Result.Error<>(e);
            // Printing it to the console
            System.out.println("La requête à l'API à échouée pour la raison suivante : "
                    + errorResult.toString());
        }
        return null;
    }

    public <T> ArrayList<T> getList(Class<T> requestedObject, String urlString) {
        // Starting the API Request on another thread.
        Future<Result<List<T>>> future = executor.submit(() -> {
            Result<List<T>> response;
            try {
                // The fetched response
                response = parseJsonList(requestedObject, urlString);
                return response;
            } catch (Exception e) {
                // Retrieve the error message
                Result<T> errorResult = new Result.Error<>(e);
                // Printing it to the console
                System.out.println("La requête à l'API à échouée pour la raison suivante : "
                        + errorResult.toString());
            }
            return null;
        });

        // Retrieving the value from the API request if it is successful.
        List<T> value = null;
        try {
            Result<List<T>> returned = future.get();
            if (returned instanceof Result.Success) {
                value = ((Result.Success<List<T>>) returned).data;
            } else {
                value = null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return (ArrayList<T>) value;
    }

    public <T> T getSingleOrDefault(Class<T> requestedObject, String urlString){
        // Starting the API Request on another thread.
        Future<Result<T>> future = executor.submit(() -> {
            Result<T> response;
            try {
                // The fetched response
                response = parseJsonObject(requestedObject, urlString);
                return response;
            } catch (Exception e) {
                // Retrieve the error message
                Result<T> errorResult = new Result.Error<>(e);
                // Printing it to the console
                System.out.println("La requête à l'API à échouée pour la raison suivante : "
                        + errorResult.toString());
            }
            return null;
        });

        // Retrieving the value from the API request if it is successful.
        T value = null;
        try {
            Result<T> returned = future.get();
            if (returned instanceof Result.Success) {
                value = ((Result.Success<T>) returned).data;
            } else {
                value = null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return (value);
    }

    public <T> String putSingleOrDefault(T toSend, String urlString){
        // Starting the API Request on another thread.
        Future<Result<String>> future = executor.submit(() -> {
            Result<String> response;
            try {
                // The fetched response
                response = updateJsonObject(toSend, urlString);
                return response;
            } catch (Exception e) {
                // Retrieve the error message
                Result<T> errorResult = new Result.Error<>(e);
                // Printing it to the console
                System.out.println("La requête à l'API à échouée pour la raison suivante : "
                        + errorResult.toString());
            }
            return null;
        });

        // Retrieving the value from the API request if it is successful.
        String value = null;
        try {
            Result<String> returned = future.get();
            if (returned instanceof Result.Success) {
                value = ((Result.Success<String>) returned).data;
            } else {
                value = null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return value;
    }

    public User loginUser(String email, String password, String urlString){
        // Starting the API Request on another thread.
        Future<Result<User>> future = executor.submit(() -> {
            Result<User> response;
            try {
                // The fetched response
                response = login(email, password, urlString);
                return response;
            } catch (Exception e) {
                // Retrieve the error message
                Result<Exception> errorResult = new Result.Error<>(e);
                // Printing it to the console
                System.out.println("La requête à l'API à échouée pour la raison suivante : "
                        + errorResult.toString());
            }
            return null;
        });

        // Retrieving the value from the API request if it is successful.
        User value = null;
        try {
            Result<User> returned = future.get();
            if (returned instanceof Result.Success) {
                value = ((Result.Success<User>) returned).data;
            } else {
                value = null;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return value;
    }

    private <T> Result<String> updateJsonObject(T toSend, String urlString) {
        try {
            // The fetched response
            String response = "";

            // Creating the url object to make our query
            URL url = new URL(urlString);

            // Opening the connection to the requested url
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Setting the connection request Method
            urlConnection.setRequestMethod("PATCH");

            // Setting the connection header to state that we are sending JSON Patch data
            urlConnection.setRequestProperty("Content-Type", "application/merge-patch+json; utf-8");

            // Ensure the connection will be used to send content
            urlConnection.setDoOutput(true);

            // Creating our request body from converting the toSend object to a JSON String
            String jsonObject = gson.toJson(toSend);

            // Writting our string to the connection
            sendRequestString(jsonObject, urlConnection);

            // Opening the stream in order to be able to read the response.
            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // Reading the response into a String
            response = readResponse(in);

            // Closing the connection
            urlConnection.disconnect();

            /* Returning a json object from the received response string
             * (Assuming this is a JSON string)
             */
            return new Result.Success<>(response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    private <T> Result<String> sendJsonObject(T toSend, String urlString) {
        try {
            // The fetched response
            String response = "";

            // Creating the url object to make our query
            URL url = new URL(urlString);

            // Opening the connection to the requested url
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Setting the connection request Method
            urlConnection.setRequestMethod("POST");

            // Setting the connection header to state that we are sending JSON data
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");

            // Ensure the connection will be used to send content
            urlConnection.setDoOutput(true);

            // Creating our request body from converting the toSend object to a JSON String
            String jsonObject = gson.toJson(toSend);

            // Writting our string to the connection
            sendRequestString(jsonObject, urlConnection);

            // Opening the stream in order to be able to read the response.
            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // Reading the response into a String
            response = readResponse(in);

            // Closing the connection
            urlConnection.disconnect();

            /* Returning a json object from the received response string
             * (Assuming this is a JSON string)
             */
            return new Result.Success<>(response);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Result<User> login(String email, String password, String urlString) {
        try {
            // The fetched response
            String response = "";

            // Creating the url object to make our query
            URL url = new URL(urlString);

            // Opening the connection to the requested url
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Setting the connection request Method
            urlConnection.setRequestMethod("POST");

            // Setting the connection header to state that we are sending JSON data
            urlConnection.setRequestProperty("Content-Type", "application/json; utf-8");

            // Ensure the connection will be used to send content
            urlConnection.setDoOutput(true);

            /* Creating our request body from converting the username and password strings to
             * object to a JsonObject
             */
            JsonObject credentials = new JsonObject();

            credentials.addProperty("username", email);
            credentials.addProperty("password", password);

            String jsonObject = gson.toJson(credentials);

            // Writting our string to the connection
            sendRequestString(jsonObject, urlConnection);

            // Opening the stream in order to be able to read the response.
            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // Reading the response into a String
            response = readResponse(in);

            // Converting the response String to a User object
            User retreivedUser = null;
            try {
                 retreivedUser = gson.fromJson(response, User.class);
            }
            catch (JsonSyntaxException e){
                Log.d("User Login :", response);
                return new Result.Error<>(e);
            }

            // Closing the connection
            urlConnection.disconnect();

            /* Returning a User object from the received response string
             */
            return new Result.Success<>(retreivedUser);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    private <T> JsonObject callApi(Class<T> requestedObject, String urlString) {

        try {
            // The fetched response
            String response = "";

            // Creating the url object to make our query
            URL url = new URL(urlString);

            // Opening the connection to the requested url
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Opening the stream in order to be able to read the response.
            BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // Reading the response into a String
            response = readResponse(in);

            // Closing the connection
            urlConnection.disconnect();

            /* Returning a json object from the received response string
             * (Assuming this is a JSON string)
             */
            return gson.fromJson(response, JsonObject.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    private <T> Result<List<T>> parseJsonList(Class<T> requestedObject, String urlString) {
        // Getting our Json Object from the API
        JsonObject responseJsonObject = callApi(requestedObject, urlString);

        // Getting the actual list of element out of the JsonObject
        JsonElement jsonListElement = responseJsonObject.get("hydra:member");

        // Deserialize and converts the retrieved list of element into our requestedObject Type
        List<T> list = new Factory<T>().deserialize(jsonListElement.toString(), requestedObject);

        // Returning the list of object of proper Type.
        return new Result.Success<>(list);
    }

    private <T> Result<T> parseJsonObject(Class<T> requestedObject, String urlString){
        // Getting our Json Object from the API
        JsonObject responseJsonObject = callApi(requestedObject, urlString);

        // Deserialize and converts the retrieved element into our requestedObject Type
        T object = gson.fromJson(responseJsonObject.toString(), requestedObject);

        // Returning the list of object of proper Type.
        return new Result.Success<>(object);
    }

    private <T> List<T> fromJsonList(String json, Class<T> klass) {
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
            while ((length = stream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Returning the result after we read the entire response.
        return result.toString();
    }

    private void sendRequestString(String requestBody, HttpURLConnection urlConnection){
        try(OutputStream os = urlConnection.getOutputStream()) {
            byte[] input = requestBody.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

}
