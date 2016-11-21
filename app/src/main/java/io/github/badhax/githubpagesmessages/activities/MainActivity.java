package io.github.badhax.githubpagesmessages.activities;

//    TODO: change package name above, as well as on all your Java files

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import io.github.badhax.githubpagesmessages.R;
import io.github.badhax.githubpagesmessages.adapters.MessageAdapter;
import io.github.badhax.githubpagesmessages.async.MessageTask;
import io.github.badhax.githubpagesmessages.models.AsyncResponse;
import io.github.badhax.githubpagesmessages.models.Message;

public class MainActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener, AsyncResponse {
    //Place your views and other variables here
    //All the xml views have been done up for you
    SharedPreferences prefs = null;
    RecyclerView recyclerView;
    MessageAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Toolbar toolbar;
    SwipeRefreshLayout swipe;
    AlertDialog.Builder alert;
    EditText urlValue;
    MessageTask asyncTask;
    RelativeLayout relativeLayout;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Call various methods here. We use method instead of writing code directly here
        //for better readability
        initViews();
        setUpRecyclerView();
        isFirstTime();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void createDialog() {
        //Show an alert dialog with an EditText to collect the server URL
        //then store the URL for later. Hint: use SharedPreferences
        alert = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inf = this.getLayoutInflater();
        View alertView = inf.inflate(R.layout.dialog_add_url,null);
        urlValue = (EditText) alertView.findViewById(R.id.url_value);
        alert.setView(alertView);
        alert.setTitle(R.string.form_url_dialog_label);
        alert.setMessage("Please enter a URL from which to retrieve messages.");
        alert.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("server_url", urlValue.getText().toString());
                editor.apply();

                setUpViews();
                new MessageTask(MainActivity.this, MainActivity.this).execute();
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //System.out.println("This isnt the first run.");
            }
        });
        alert.setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();
    }

    private boolean isFirstTime() {
        //Check of this app has been run before. If not, show the dialog
        // Do first run stuff here then set 'firstrun' as false
        prefs = getSharedPreferences("io.github.badhax.githubpagesmessages", MODE_PRIVATE);
        if (prefs.getBoolean("firstrun", true)) {
            createDialog();
            prefs.edit().putBoolean("firstrun", false).apply();
            return true;
        }

        return false;
    }

    private void initViews() {
        //Initialize your views, using findViewById
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        relativeLayout = (RelativeLayout) findViewById(R.id.no_messages);
        adapter = new MessageAdapter();
    }

    private void setUpViews() {
        //Operations on the views you initialized
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.activity_main_label));
        swipe.setOnRefreshListener(this);
    }

    private void setUpRecyclerView() {
        //Operations on the recyclerview
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        manager.setStackFromEnd(true); // what does this do?
        recyclerView.setHasFixedSize(true); // what does this do?
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                if (recyclerView.getChildCount() > 0) {
                    relativeLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                if (recyclerView.getChildCount() == 0) {
                    relativeLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
    }

    @Override
    public void onRefresh() {
        //Refresh the data if the user swipes down
        //Hint, use the AsyncTask
        swipe.setRefreshing(true);
        asyncTask = new MessageTask(this, this);
        asyncTask.execute();
    }

    @Override
    public void processFinish(int newItems) {
        //Refresh the data if there are new items
        swipe.setRefreshing(false);
        if(newItems>0){
            adapter.update();
        }

    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
