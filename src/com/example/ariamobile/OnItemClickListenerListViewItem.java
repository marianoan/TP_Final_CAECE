package com.example.ariamobile;

/**
 * Created by Mariano on 26/10/13.
 */
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.view.View;

/*
 * Here you can control what to do next when the user selects an item
 */
public class OnItemClickListenerListViewItem implements OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Context context = view.getContext();

        TextView textViewItem = ((TextView) view.findViewById(R.id.textViewItem));

        // get the clicked item name
        String listItemText = textViewItem.getText().toString();

        // get the clicked item ID
        String listItemId = textViewItem.getTag().toString();
        //Toast.makeText(context, "Item: " + listItemText + ", Item ID: " + listItemId, Toast.LENGTH_SHORT).show();
        String account_status="92";
        String restorants="93";

        if(listItemId.equals(account_status)) {
            Intent myIntent = new Intent(context, AccountStatus.class);
            context.startActivity(myIntent);
        } else {
            if(listItemId.equals(restorants)) {
                Intent myIntent = new Intent(context, Restorants.class);
                context.startActivity(myIntent);
            }
        }

    }



}
