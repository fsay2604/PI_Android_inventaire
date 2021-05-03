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

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.pi_android_inventaire.PIAndroidInventaire;
import com.example.pi_android_inventaire.activities.Connexion;
import com.example.pi_android_inventaire.activities.MainActivity;
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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static android.os.Looper.getMainLooper;

class Factory<T> {
    public List<T> deserialize(String json, Class<T> klass) {
        Gson gson = new Gson();
        return gson.fromJson(json, new ListOfSomething<T>(klass));
    }
}


/**
 * Permet de faire des appels à l'API de facon asynchrone.
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


    /**
     * Fait un appel à l'api de facon synchrone et retourne une liste d'objet du type demandée.
     * @param requestedObject Le type d'objet demandé.
     * @param urlString L'url à appelé qui retourne un liste d'objet en JSON
     * @param <T> Le type d'objet retourné
     * @return Une ArrayList du type de l'objet demandé en paramètre et en retour.
     */
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

    /**
     * Fait un appel à l'api de facon asynchrone et retourne un objet du type demandée.
     * @param requestedObject Le type d'objet demandé.
     * @param urlString L'url à appelé qui retourne un objet en JSON
     * @param <T> Le type d'objet retourné
     * @return Un objet du type demandé en paramètre et en retour.
     */
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

    /**
     * Fait un appel à l'api de facon asynchrone et retourne un objet du type demandée.
     * @param requestedObject Le type d'objet demandé.
     * @param urlString L'url à appelé qui retourne un objet en JSON
     * @param <T> Le type d'objet retourné
     * @return Une ArrayList du type de l'objet demandé en paramètre et en retour.
     */
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

    /**
     * Fait un appel à l'api de facon asynchrone et insert un objet du type demandée dans
     * la base de données distante.
     * @param toSend l'objet à envoyer à l'api.
     * @param urlString L'url à appelé qui retourne un objet en JSON
     * @param <T> Le type d'objet retourné
     * @return Une ArrayList du type de l'objet demandé en paramètre et en retour.
     */
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

    /**
     * Fait un appel à l'api de facon asynchrone pour login un utilisateur.
     * @param email courriel de l'utilisateur
     * @param password mot de passe de l'utilisateur
     * @param urlString url du endpoint pour le login
     * @param currentContext context pour faire un toast si quelquechose tourne mal
     * @return L'utilisateur si les identifiants sont valide sinon retourne null
     */
    public User loginUser(String email, String password, String urlString,Context currentContext){
        // Starting the API Request on another thread.
        Future<Result<User>> future = executor.submit(() -> {
            Result<User> response;
            try {
                // The fetched response
                response = login(email, password, urlString);

                if (response instanceof Result.Error){
                    Exception exception = ((Result.Error<User>) response).exception;
                    // Showing the error to the user
                    Handler mainHandler = new Handler(getMainLooper());

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(currentContext, exception.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                    return null;
                }
                else {
                    return response;
                }
            } catch (Exception e) {
                // Retrieve the error message
                Result<Exception> errorResult = new Result.Error<>(e);
                // Printing it to the console
                System.out.println("La requête à l'API à échouée pour la raison suivante : "
                        + errorResult.toString());
                // Showing the error to the user
                Toast.makeText(currentContext, errorResult.toString(),Toast.LENGTH_LONG).show();
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

    /**
     * Envoie un requête Json pour updaté les valeur d'un objet
     * @param toSend L'objet à jour
     * @param urlString L'url du endpoint
     * @param <T> le Type d'objet.
     * @return le résultat de la requête
     */
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

    /**
     * Envoie un requête Json pour insérer les valeur d'un objet
     * @param toSend L'objet à envoyer
     * @param urlString L'url du endpoint
     * @param <T> le Type d'objet.
     * @return le résultat de la requête
     */
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

    /**
     * Login un user via l'api
     * @param email le courriel de l'utilisateur.
     * @param password le mot de passe de l'utilisateur.
     * @param urlString le url de l'api pour login le user
     * @return Le résultat de l'opération
     */
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
            BufferedInputStream in;
            try {
                in = new BufferedInputStream(urlConnection.getInputStream());
            }
            catch (IOException e) {
                e.printStackTrace();
                in = new BufferedInputStream(urlConnection.getErrorStream());

                // Reading the response into a String
                response = readResponse(in);

                JsonObject errorResponseObj = gson.fromJson(response, JsonObject.class);
                String errorResponse = errorResponseObj.get("error").getAsString();
                Log.d("User Login : ", errorResponse);
                System.out.println(e.getMessage());
                return new Result.Error<>(new Exception(errorResponse));
            }

            // Reading the response into a String
            response = readResponse(in);

            // Converting the response String to a User object
            User retreivedUser = null;
            try {
                 retreivedUser = gson.fromJson(response, User.class);
            }
            catch (JsonSyntaxException e){
                Log.d("User Login : ", e.getMessage());
                System.out.println(e.getMessage());
            }

            // Closing the connection
            urlConnection.disconnect();

            /* Returning a User object from the received response string
             */

            return new Result.Success<>(retreivedUser);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Effectue un appel à l'api et retourne la réponse sous forme de JSON
     * @param requestedObject le type d'objet voulu
     * @param urlString le url à appeller
     * @param <T> le type d'objet de retour
     * @return l'objet recu en réponse lors de la requête
     */
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

    /**
     * Prends une liste d'objet JSON et retourne cette liste sous forme d'objet Java de n'importe quel type
     * @param requestedObject le type d'objet de retour
     * @param urlString l'url à appeler
     * @param <T> le type de retour
     * @return une liste d'objet Java du type demandé
     */
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

    /**
     * Prends un objet JSON et le retourne sous forme d'objet Java de n'importe quel type
     * @param requestedObject le type d'objet de retour
     * @param urlString l'url à appeler
     * @param <T> le type de retour
     * @return un objet Java du type demandé
     */
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

    /**
     * Lit une réponse du serveur à partir d'un BufferedInputStream
     * @param stream le stream à partir duquel on lit
     * @return la réponse du serveur sous forme de String
     */
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

    /**
     * Envoie Une request à l'url demandé
     * @param requestBody le body de la request à envoyé sous forme de String
     * @param urlConnection l'url auquel envoyé la request
     */
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
