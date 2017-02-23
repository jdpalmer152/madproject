package com.example.whitelegg_n.osmdroid1;

import android.app.ListActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by 2palmj38 on 23/02/2017.
 */
public class MapTypeListActivity extends ListActivity
    {
        String[] data;

        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            data = new String[] { "Regular Map", "Cycle Map"};

            MyAdapter browseradapter = new MyAdapter();
            setListAdapter(browseradapter);
        }

        public void onListItemClick(ListView lv, View view, int index, long id)
        {
            boolean cyclemap = false;

            if (index == 1)
            {
                cyclemap = true;
            }

            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putBoolean("com.example.cyclemap", cyclemap);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }

        class MyAdapter extends ArrayAdapter<String>
        {

            public MyAdapter()
            {
                super(MapTypeListActivity.this, android.R.layout.simple_list_item_1, data);


            }

            public View getView(int index, View convertView, ViewGroup)
            {
                LayoutInflater inflater;
                inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View view = inflater.inflate(R.layout.poi_entry, parent, false);
                TextView nameTextView = (TextView) view.findViewById(R.id.poi_name);
                nameTextView.setText("Test");

                return view;
            }

        }
    }
