package io.github.sultanofcardio.githubpagesmessages.activities;

//    TODO: change package name above, as well as on all your Java files

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import io.github.sultanofcardio.githubpagesmessages.R;
import io.github.sultanofcardio.githubpagesmessages.models.AsyncResponse;

public class MainActivity extends AppCompatActivity
implements SwipeRefreshLayout.OnRefreshListener, AsyncResponse{
    //Place your views and other variables here
    //All the xml views have been done up for you

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Call various methods here. We use method instead of writing code directly here
        //for better readability
    }

    private void createDialog(){
        //Show an alert dialog with an EditText to collect the server URL
        //then store the URL for later. Hint: use SharedPreferences
    }

    private boolean isFirstTime() {
        //Check of this app has been run before. If not, show the dialog

        return false;
    }

    private void initViews(){
        //Initialize your views, using findViewById
    }

    private void setUpViews(){
        //Operations on the views you initialized
    }

    private void setUpRecyclerView(){
        //Operations on the recyclerview
    }

    @Override
    public void onRefresh() {
        //Refresh the data if the user swipes down
        //Hint, use the AsyncTask
    }

    @Override
    public void processFinish(int newItems) {
        //Refresh the data if there are new items
    }
}
