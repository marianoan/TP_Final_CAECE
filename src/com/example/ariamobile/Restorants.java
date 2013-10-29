package com.example.ariamobile;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mariano on 26/10/13.
 */


/**
 * Created by Mariano on 24/09/13.
 */
public class Restorants extends Activity {

    private TextView account_status_title;
    private TextView loading_text;
    private ProgressBar progressBar;
    private TableLayout table_list;
    private TableLayout table_loading;
    RestoExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    public static final String PREFS_NAME = "reservation_file";
    //Context restoContext = getApplicationContext();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restorants);
        // getting elements
        table_list = (TableLayout) findViewById(R.id.table_list);
        table_loading = (TableLayout) findViewById(R.id.table_loading);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        account_status_title = (TextView) findViewById(R.id.account_status_title);
        loading_text = (TextView) findViewById(R.id.loading_text);
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        // preparing list data
        //prepareListData();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listAdapter = new RestoExpandableListAdapter(this, listDataHeader, listDataChild);

        System.err.println(listAdapter.isChildSelectable(0,0));
        // setting list adapter
        expListView.setAdapter(listAdapter);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface type_regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

        account_status_title.setTypeface(type);
        loading_text.setTypeface(type);

        table_list.setVisibility(View.INVISIBLE);
        table_loading.setVisibility(View.VISIBLE);

        getRestorants();


    }

    public void getRestorants(){
        AriaRestClient.get("restorants/", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject valid = new JSONObject(jsonObject.getString("valid"));
                    String g1= valid.getString("value");
                    String g2="true";

                    if(g1.equals(g2))
                    {

                        JSONObject data = new JSONObject(jsonObject.getString("data"));


                        try {

                            JSONArray data_array = new JSONArray(data.getString("restorants"));
                            List<String> child = new ArrayList<String>();
                            for (int i=0; i < data_array.length(); i++)
                            {
                                JSONObject json_object = data_array.getJSONObject(i);
                                listDataHeader.add(json_object.getString("name"));
                                // get all value here
                                String reservable = json_object.getString("reservable");
                                String valid_reservable = "true";
                                if (reservable.equals(valid_reservable)){
                                    child.add(json_object.getString("desc") + " Conozca mas y reserve pulsando aqui.");
                                } else {
                                    child.add(json_object.getString("desc") + " Conozca mas pulsando aqui.");
                                }
                                listDataChild.put(listDataHeader.get(i), child);
                                child = new ArrayList<String>();
                            }
                            table_loading.setVisibility(View.GONE);
                            loading_text.setVisibility(View.GONE);
                            table_list.setVisibility(View.VISIBLE);


                        } catch (JSONException e) {
                            loading_text.setText(e.getMessage());
                        }


                    }
                    else
                    {
                        //codigo invalido
                        loading_text.setText("Codigo Invalido");

                    }



                } catch (Exception e) {
                    loading_text.setText("Intente nuevamente");
                }
            }
        });
    }



}