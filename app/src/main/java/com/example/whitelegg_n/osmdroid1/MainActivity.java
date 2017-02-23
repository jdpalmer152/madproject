package com.example.whitelegg_n.osmdroid1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MainActivity extends Activity
{
    MapView mv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        setContentView(R.layout.activity_main);
        mv = (MapView)findViewById(R.id.map1);
        mv.getController().setZoom(14);
        mv.getController().setCenter(new GeoPoint(40.1, 22.5));
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_hello_map, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.choosemap)
        {
            Intent intent = new Intent(this,MapChooseActivity.class);
            startActivityForResult(intent, 0);
            //react to the menu item being selected...
            return true;
        }
        if(item.getItemId() == R.id.setLocation)
        {
            Intent intent = new Intent(this, SetLocationActivity.class);
            startActivityForResult(intent, 1);

            return true;
        }
        if(item.getItemId() == R.id.choosemapstyle)
        {
            Intent intent = new Intent(this, MapTypeListactivity.class);
            startActivityForResult(intent,0);

            return true;
        }
        return false;
    }
    protected void onActivityResult(int requestCode,int resultCode, Intent intent)
    {
        if(requestCode==0)
        {
            Bundle extras=intent.getExtras();
            boolean cyclemap = extras.getBoolean("com.ex ample.cyclemap");
            if(cyclemap==true)
            {
                mv.setTileSource(TileSourceFactory.CYCLEMAP);
            }
            else
            {
                mv.setTileSource(TileSourceFactory.MAPNIK);
            }
        }elseif (requestCode == 1)
        {
            if (resonseCode == RESULT_OK)
            {
                Bundle latlongbundle = intent.getExtras();
                double latitude = latlongBundle.getDouble("com.whitelegg_n.latitude");
                double longitude = latlongBundle.getDouble("com.whitelegg_n.longitude");

                mv.getController().setCenter(new GeoPoint(longitude, latitude));
            }
        }
    }
}

