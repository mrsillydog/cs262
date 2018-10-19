package isa3.cs262.calvin.edu.homework2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String>{

    private EditText mSearchInput;
    private Button mFetchButton;

    private ListView mPlayerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSearchInput = (EditText) findViewById(R.id.search_field);
        mSearchInput.setTransformationMethod(null);
        mPlayerList = (ListView) findViewById(R.id.player_list);
        mFetchButton = (Button) findViewById(R.id.fetch_button);

        if(getSupportLoaderManager().getLoader(0)!=null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    public void findPlayer(View view) {
        String mQueryString = mSearchInput.getText().toString();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(mFetchButton.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        if(networkInfo != null && networkInfo.isConnected()) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("mSearchInput", mQueryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        } else {
            displayToast("Please check your network connection and try again.");
        }

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new PlayerLoader(this, bundle.getString("mSearchInput"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String id;
            String name;
            String email;
            List<String> player_array_list = new ArrayList<String>();
            if(jsonObject.has("items")) {
                JSONArray itemsArray = jsonObject.getJSONArray("items");
                //Iterate through the results
                for(int i = 0; i<itemsArray.length(); i++) {
                    JSONObject player = itemsArray.getJSONObject(i); //Get the current item

                    try {
                        id = player.getString("id");
                    } catch (Exception e) {
                        id = "no ID";
                    }

                    try {
                        name = player.getString("name");
                    } catch (Exception e) {
                        name = "no name";
                    }

                    try {
                        email = player.getString("emailAddress");
                    } catch (Exception e) {
                        email = "no email";
                    }
                    player_array_list.add(id+", "+name+", "+email);
                }
            } else {
                try {
                    id = jsonObject.getString("id");
                } catch (Exception e) {
                    id = "no ID";
                }

                try {
                    name = jsonObject.getString("name");
                } catch (Exception e) {
                    name = "no name";
                }

                try {
                    email = jsonObject.getString("emailAddress");
                } catch (Exception e) {
                    email = "no email";
                }

                player_array_list.add(id+", "+name+", "+email);
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                    this,
                    android.R.layout.simple_list_item_1,
                    player_array_list);

            mPlayerList.setAdapter(arrayAdapter);
        } catch (Exception e) {
            displayToast("Nonexistent ID");
            e.printStackTrace();
        }
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
