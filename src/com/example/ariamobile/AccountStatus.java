package com.example.ariamobile;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.widget.ExpandableListView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mariano on 24/09/13.
 */
public class AccountStatus extends Activity {

    private TextView account_status_title;
    private TextView account_status_total;
    private TextView account_status_total_value;
    private TextView loading_text;
    private ProgressBar progressBar;
    private TableLayout table_list;
    private TableLayout table_loading;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    public static final String PREFS_NAME = "reservation_file";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_status);
        // getting elements
        table_list = (TableLayout) findViewById(R.id.table_list);
        table_loading = (TableLayout) findViewById(R.id.table_loading);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        account_status_title = (TextView) findViewById(R.id.account_status_title);
        account_status_total = (TextView) findViewById(R.id.account_status_total);
        account_status_total_value = (TextView) findViewById(R.id.account_status_total_value);
        loading_text = (TextView) findViewById(R.id.loading_text);
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        // preparing list data
        //prepareListData();
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface type_regular = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");

        account_status_title.setTypeface(type);
        account_status_total.setTypeface(type);
        loading_text.setTypeface(type);
        account_status_total_value.setTypeface(type_regular);

        table_list.setVisibility(View.INVISIBLE);
        account_status_total.setVisibility(View.INVISIBLE);
        account_status_total_value.setVisibility(View.INVISIBLE);
        table_loading.setVisibility(View.VISIBLE);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String reservation = settings.getString("reservation_number","vacio");

        getAccount(reservation);
    }

    public void getAccount(String reservation_id){
        RequestParams params = new RequestParams("id", reservation_id);
        AriaRestClient.get("account/", params, new AsyncHttpResponseHandler() {
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
                            JSONArray data_array = new JSONArray(data.getString("rooms"));
                            listDataHeader.add("Habitacion");
                            List<String> child = new ArrayList<String>();

                            for (int i=0; i < data_array.length(); i++)
                            {
                                JSONObject json_object = data_array.getJSONObject(i);
                                // get all value here
                                child.add(json_object.getString("fecha") + " " + json_object.getString("item"));
                            }
                            listDataChild.put(listDataHeader.get(0), child);

                            data_array = new JSONArray(data.getString("aabb"));
                            listDataHeader.add("Alimentos y bebidas");
                            child = new ArrayList<String>();

                            for (int i=0; i < data_array.length(); i++)
                            {
                                JSONObject json_object = data_array.getJSONObject(i);
                                // get all value here
                                child.add(json_object.getString("fecha") + " " + json_object.getString("item"));
                            }
                            listDataChild.put(listDataHeader.get(1), child);

                            data_array = new JSONArray(data.getString("spa"));
                            listDataHeader.add("Spa");
                            child = new ArrayList<String>();

                            for (int i=0; i < data_array.length(); i++)
                            {
                                JSONObject json_object = data_array.getJSONObject(i);
                                // get all value here
                                child.add(json_object.getString("fecha") + " " + json_object.getString("item"));
                            }
                            listDataChild.put(listDataHeader.get(2), child);

                            data_array = new JSONArray(data.getString("misc"));
                            listDataHeader.add("Miscelaneos");
                            child = new ArrayList<String>();

                            for (int i=0; i < data_array.length(); i++)
                            {
                                JSONObject json_object = data_array.getJSONObject(i);
                                // get all value here
                                child.add(json_object.getString("fecha") + " " + json_object.getString("item"));
                            }
                            listDataChild.put(listDataHeader.get(3), child);

                            JSONObject total = new JSONObject(jsonObject.getString("total"));
                            account_status_total_value.setText("$" + total.getString("value"));

                            table_loading.setVisibility(View.GONE);
                            loading_text.setVisibility(View.GONE);
                            table_list.setVisibility(View.VISIBLE);
                            account_status_total.setVisibility(View.VISIBLE);
                            account_status_total_value.setVisibility(View.VISIBLE);
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