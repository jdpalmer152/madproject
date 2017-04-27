package com.example.whitelegg_n.osmdroid1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity
{
    MapView mv;
    ItemizedIconOverlay<OverlayItem> items;
    ItemizedIconOverlay.OnItemGestureListener<OverlayItem> markerGestureListener;

    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));

        setContentView(R.layout.activity_main);
        mv = (MapView)findViewById(R.id.map1);
        mv.getController().setZoom(14);
        mv.getController().setCenter(new GeoPoint(40.1, 22.5));

        markerGestureListener = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>()
        {
            public boolean onItemLongPress(int i, OverlayItem item)
            {
                Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }
            public boolean onItemSingleTapUp(int i, OverlayItem item)
            {
                Toast.makeText(MainActivity.this, item.getSnippet(), Toast.LENGTH_SHORT).show();
                return true;
            }
        };

        items = new ItemizedIconOverlay<OverlayItem>(this, new ArrayList<OverlayItem>(), markerGestureListener);
        OverlayItem Verwood = new OverlayItem("Verwood", "Town in East Dorset", new GeoPoint(50.88, -1.87));

        OverlayItem StStephensCastle = new OverlayItem("St. Stephens Castle", "The 'Castle' is an Iron Age barrow at the top of an old sand and gravel quarry", new GeoPoint(50.88, -1.86));
        items.addItem(Verwood);
        items.addItem(StStephensCastle);
        mv.getOverlays().add(items);

        ArrayList<OverlayItem> overlayItems = new ArrayList<OverlayItem>();
        try {

            BufferedReader reader = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath() + "/poi.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] components = line.split(",");
                if (components.length == 5) {
                    OverlayItem currentOverlayItem = new OverlayItem(components[0], components[2], new GeoPoint(Double.parseDouble(components[4]), Double.parseDouble(components[3])));
                    overlayItems.add(currentOverlayItem);
                }
            }
        }

        catch(IOException e)
        {
            System.out.println("ERROR: " + e);
        }

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
        if(item.getItemId() == R.id.setlocation)
        {
            Intent intent = new Intent(this, SetLocationActivity.class);
            startActivityForResult(intent, 1);

            return true;
        }
        if(item.getItemId() == R.id.choosemapstyle)
        {
            Intent intent = new Intent(this, MapTypeListActivity.class);
            startActivityForResult(intent,0);

            return true;
        }
        return false;
    }
    protected void onActivityResult(int requestCode,int responseCode, Intent intent)
    {
        if(responseCode == RESULT_OK)
        {
            if(requestCode == 0)
            {
                Bundle extras=intent.getExtras();
                boolean cyclemap = extras.getBoolean("com.example.cyclemap");
                if(cyclemap==true)
                {
                    mv.setTileSource(TileSourceFactory.CYCLEMAP);
                }
                else
                {
                    mv.setTileSource(TileSourceFactory.MAPNIK);
                }
             }
            else if (requestCode == 1)
             {

                Bundle latlongBundle = intent.getExtras();
                double latitude = latlongBundle.getDouble("com.whitelegg_n.latitude");
                double longitude = latlongBundle.getDouble("com.whitelegg_n.longitude");

                mv.getController().setCenter(new GeoPoint(longitude, latitude));
            }
        }
    }

    public void onStart()
    {
        super.onStart();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        double lat = Double.parseDouble (prefs.getString("lat", "50.9"));
        double lon = Double.parseDouble (prefs.getString("lon", "-1.4"));
        int zoom = Integer.parseInt(prefs.getString("zoom", "14"));

        mv.getController().setCenter(new GeoPoint(lat, lon));
        mv.getController().setZoom(zoom);
    }
}

