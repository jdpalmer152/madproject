package com.example.whitelegg_n.osmdroid1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
/**
 * Created by 2palmj38 on 23/02/2017.
 */
public class SetLocationActivity extends Activity implements View.OnClickListener
    {
        protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_set_location);

            Button submitButton = (Button) findViewById(R.id.submitButton);
            submitButton.setOnClickListener(this);


        }

        public void onClick(View view)
        {
            EditText latitudeEditText = (EditText) findViewById(R.id.latitudeEditText);
            double latitude = Double.parseDouble(latitudeEditText.getText().toString());

            EditText longitudeEditText = (EditText) findViewById(R.id.longitudeEditText);
            double longitude = Double.parseDouble(longitudeEditText.getText().toString());

            Bundle latlongBundle = new Bundle();
            latlongBundle.putDouble("com.whitelegg_n.latitude", latitude);
            latlongBundle.putDouble("com.whitelegg_n.longitude", longitude);

            Intent intent = new Intent();
            intent.putExtras(latlongBundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
