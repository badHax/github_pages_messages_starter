package io.github.badhax.githubpagesmessages.async;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.github.badhax.githubpagesmessages.models.AsyncResponse;
import io.github.badhax.githubpagesmessages.models.Message;

/**
 * @author sultanofcardio
 */

public class MessageTask extends AsyncTask<Void, Void, Void> {
    private Context context;
    private String API_URL;
    private JSONArray response;
    private int count = 0;
    private AsyncResponse asyncResponse;

    public MessageTask(Context context, AsyncResponse asyncResponse) {
        this.context = context;
        this.asyncResponse = asyncResponse;
        API_URL = ((AppCompatActivity)context).getPreferences(Context.MODE_PRIVATE).getString("server_url", "");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(API_URL).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            String json = builder.toString();

            System.out.println(json);

            response = new JSONArray(json);

            for (int i = 0; i < response.length(); i++) {
                Message message2 = Message.findById(Message.class, i);
                if (message2 == null) {
                    JSONObject message = response.getJSONObject(i);
                    Message message1 = new Message(
                            i,
                            message.getString("name"),
                            message.getString("email"),
                            message.getString("message")
                    );
                    message1.save();
                    count++;
                }
            }

            connection.disconnect();

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        asyncResponse.processFinish(count);
    }
}
