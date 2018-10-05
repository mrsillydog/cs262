package ceb45.cs262.calvin.edu.lab05;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
                implements LoaderManager.LoaderCallbacks<String> {

    private EditText mBookInput;
    private TextView mAuthorText;
    private TextView mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = (EditText) findViewById(R.id.bookInput);
        mAuthorText = (TextView) findViewById(R.id.authorText);
        mTitleText = (TextView) findViewById(R.id.titleText);

        if(getSupportLoaderManager().getLoader(0)!=null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }


    public void searchBooks(View view) {
        String mQueryString = mBookInput.getText().toString();

        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);


        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected() && mQueryString.length()!=0) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("mQueryString", mQueryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            mTitleText.setText(R.string.loading);
            mAuthorText.setText("");
        } else {
            if(mQueryString.length() == 0) {
                mAuthorText.setText("");
                mTitleText.setText("Please enter a search term.");
            } else {
                mAuthorText.setText("");
                mTitleText.setText("Please check your network connection and try again.");
            }
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new BookLoader(this, bundle.getString("mQueryString"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            //Iterate through the results
            for(int i = 0; i<itemsArray.length(); i++){
                JSONObject book = itemsArray.getJSONObject(i); //Get the current item
                String title=null;
                String authors=null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e){
                    e.printStackTrace();
                }

                //If both a title and author exist, update the TextViews and return
                if (title != null && authors != null){
                    mTitleText.setText(title);
                    mAuthorText.setText(authors);
                    return;
                }
            }
            mTitleText.setText("No Results Found!");
            mAuthorText.setText("");

        } catch (Exception e) {
            mTitleText.setText("No Results Found");
            mAuthorText.setText("");
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
