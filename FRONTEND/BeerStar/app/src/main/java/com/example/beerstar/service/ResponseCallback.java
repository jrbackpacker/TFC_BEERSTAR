package com.example.beerstar.service;

/**
 * Callback interface for handling responses from the Voiceflow API client.
 */
public interface ResponseCallback {

    /**
     * Called when the API call is successful.
     *
     * @param responseBodyString The raw JSON response body as a String.
     */
    void onSuccess(String responseBodyString);

    /**
     * Called when the API call fails (network error, server error, etc.).
     *
     * @param errorMessage A description of the error.
     */
    void onError(String errorMessage);
}