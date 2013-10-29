package com.example.ariamobile;

/**
 * Created by Mariano on 03/09/13.
 */

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class AplicationMenu extends Activity implements View.OnClickListener {

    public static final String PREFS_NAME = "reservation_file";
    private ListView listview;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aplication_menu);
        listview = (ListView) findViewById(R.id.listview);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        TextView welcome_menu = (TextView) findViewById(R.id.welcome_menu);
        welcome_menu.setTypeface(type);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String full_name = settings.getString("name","vacio") + "!";

        welcome_menu.setText("Bienvenido " + full_name);

        showListView();

    }

    public void showListView(){

        // add your items, this can be done programatically
        // your items can be from a database
        ObjectItem[] ObjectItemData = new ObjectItem[6];

        ObjectItemData[0] = new ObjectItem(91, "Mis Datos");
        ObjectItemData[1] = new ObjectItem(92, "Estado de Cuenta");
        ObjectItemData[2] = new ObjectItem(93, "Nuestros Restaurantes");
        ObjectItemData[3] = new ObjectItem(94, "Programe su Despierte");
        ObjectItemData[4] = new ObjectItem(95, "Pida su Auto");
        ObjectItemData[5] = new ObjectItem(96, "Comparti tu experiencia");

        // our adapter instance
        ArrayAdapterItem adapter = new ArrayAdapterItem(this, R.layout.list_view_row_item, ObjectItemData);

        // create a new ListView, set the adapter and item click listener
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListenerListViewItem());

    }



    public void onClick(View v){
        //respond to clicks
        /*if(v.getId()==R.id.account_status){

            Intent myIntent = new Intent(account_status.getContext(), AccountStatus.class);
            startActivityForResult(myIntent, 0);


        }*/
    }






}
