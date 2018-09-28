package isa3.cs262.calvin.edu.homework1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    private EditText mValue1EditText;
    private EditText mValue2EditText;
    private TextView mResultTextView;
    private Spinner mOperatorSpinner;
    private Float result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mValue1EditText = (EditText) findViewById(R.id.editText_firstValue);
        mValue2EditText = (EditText) findViewById(R.id.editText_secondValue);
        mResultTextView = (TextView) findViewById(R.id.textview_result);
        mOperatorSpinner = (Spinner) findViewById(R.id.spinner);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("Value1", mValue1EditText.getText().toString());
        savedInstanceState.putString("Value2", mValue2EditText.getText().toString());
        savedInstanceState.putString("Result", mResultTextView.getText().toString());
        savedInstanceState.putString("Operator", mOperatorSpinner.getSelectedItem().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mValue1EditText.setText(savedInstanceState.getString("Value1"));
        mValue2EditText.setText(savedInstanceState.getString("Value2"));
        mResultTextView.setText(savedInstanceState.getString("Result"));
        // thanks to http://www.javased.com/?post=2390102 for the help on assigning the correct spinner value.
        String myString = savedInstanceState.getString("Operator"); //the value you want the position for

        ArrayAdapter myAdap = (ArrayAdapter) mOperatorSpinner.getAdapter(); //cast to an ArrayAdapter

        int spinnerPosition = myAdap.getPosition(myString);

        mOperatorSpinner.setSelection(spinnerPosition);
    }




    public void calculateResult(View view) {
        Log.d(LOG_TAG, "Button clicked, calculating!");
        String value1 = mValue1EditText.getText().toString();
        if(value1.equals("")) {
            mValue1EditText.setHint("Please enter a number.");
            mValue2EditText.setHint("Value 2");
            mResultTextView.setText("");
            return;
        }
        Integer finalValue1 = Integer.parseInt(value1);
        String value2 = mValue2EditText.getText().toString();
        if(value2.equals("")) {
            mValue2EditText.setHint("Please enter a number.");
            mValue1EditText.setHint("Value 1");
            mResultTextView.setText("");
            return;
        }
        mValue1EditText.setHint("Value 1");
        mValue2EditText.setHint("Value 2");
        Integer finalValue2 = Integer.parseInt(value2);
        String operator = mOperatorSpinner.getSelectedItem().toString();
        if(operator.equals("+")) {
            result = finalValue1.floatValue() + finalValue2.floatValue();
        } else if (operator.equals("-")) {
            result = finalValue1.floatValue() - finalValue2.floatValue();
        } else if (operator.equals("*")) {
            result = finalValue1.floatValue() * finalValue2.floatValue();
        } else {
            result = finalValue1.floatValue() / finalValue2.floatValue();
        }

        mResultTextView.setText(result.toString());
    }
}
