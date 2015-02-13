package com.example.macrocalculator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class UserPrefsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_prefs);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_prefs, menu);
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
            return true;
        }
        if (id == R.id.user_preferences) {
            startActivity(new Intent(this, UserPrefsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveProfile(View view) {
        TextView heightFeetTextView = (TextView) findViewById(R.id.HeightFeetField);
        int heightFeet = Integer.parseInt(heightFeetTextView.getText().toString());
        TextView heightInchesView = (TextView) findViewById(R.id.HeightInchesField);
        int heightInches = Integer.parseInt(heightInchesView.getText().toString());

        TextView weightView = (TextView) findViewById(R.id.Weight);
        int weight = Integer.parseInt(weightView.getText().toString());

        RadioGroup genderButton = (RadioGroup) findViewById(R.id.genderRadioButtons);
        boolean isMale = ((RadioButton) findViewById(genderButton.getCheckedRadioButtonId())).getText().toString().equals("Male");

        TextView ageView = (TextView) findViewById(R.id.Age);
        int age = Integer.parseInt(ageView.getText().toString());

        RadioGroup gainingButton = (RadioGroup) findViewById(R.id.weightLossRadioButtons);
        boolean isGaining = ((RadioButton) findViewById(gainingButton.getCheckedRadioButtonId())).getText().toString().equals("Gain Weight");


        User u = new User(new Height(heightFeet, heightInches), new Weight(weight, true), isMale, isGaining, age, User.ActivityMultiplier.MODERATELYACTIVE);
        ((MyApplication) this.getApplication()).setUser(u);
        startService(new Intent(this, StatusBarNotification.class));
        startActivity(new Intent(this, MainActivity.class));
    }
}
