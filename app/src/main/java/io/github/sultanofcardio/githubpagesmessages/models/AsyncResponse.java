package io.github.sultanofcardio.githubpagesmessages.models;

/**
 * @author sultanofcardio
 *
 * Implementing this class allows an Activity class to collect the result of an AsyncTask
 * without needing to pass context or handling view modification restrictions
 *
 * This class has been written for you
 */
public interface AsyncResponse {
    void processFinish(int newItems);
}
