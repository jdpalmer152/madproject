package com.example.whitelegg_n.osmdroid1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

/**
 * Created by whitelegg_n on 09/02/2017.
 */
public class MapChooseActivity extends Activity implements View.OnClickListener {
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mca);

        Button regular = (Button)findViewById(R.id.btnRegular);
        regular.setOnClickListener(this);
        Button cyclemap = (Button)findViewById(R.id.btnCyclemap);
        cyclemap.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        Intent intent = new Intent();
        Bundle bundle=new Bundle();
        boolean cyclemap=false;
        if (view.getId()==R.id.btnCyclemap)
        {
            cyclemap=true;
        }
        bundle.putBoolean("com.example.cyclemap", cyclemap);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
