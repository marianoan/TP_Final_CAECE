package com.example.ariamobile;

/**
 * Created by Mariano on 26/10/13.
 */
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

/*
 * Here you can control what to do next when the user selects an item
 */
public class OnChildClickListenerListViewItem implements OnChildClickListener {

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                             int groupPosition, int childPosition, long id) {

        Context context = v.getContext();

        TextView textViewItem = ((TextView) v.findViewById(R.id.restoInfo));

        // get the clicked item name
        //String listItemText = textViewItem.getText().toString();

        // get the clicked item ID
        //String listItemId = textViewItem.getTag().toString();
        Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
        //String account_status="92";
        //String restorants="93";
        Intent myIntent = new Intent(context, AccountStatus.class);
        context.startActivity(myIntent);
        /*
        if(listItemId.equals(account_status)) {
            Intent myIntent = new Intent(context, AccountStatus.class);
            context.startActivity(myIntent);
        } else {
            if(listItemId.equals(restorants)) {
                Intent myIntent = new Intent(context, Restorants.class);
                context.startActivity(myIntent);
            }
        }*/

        return true;

    }



}
