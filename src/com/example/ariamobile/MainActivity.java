package com.example.ariamobile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Typeface;
import android.widget.Toast;
import android.widget.ProgressBar;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import org.json.*;
import com.loopj.android.http.*;


public class MainActivity extends Activity implements OnClickListener {

	private Button scanBtn;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private ProgressBar progressBar;
    private String scan_content;
    public static final String PREFS_NAME = "reservation_file";




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        tv1 = (TextView) findViewById(R.id.texto1);
        tv2 = (TextView) findViewById(R.id.texto2);
        tv3 = (TextView) findViewById(R.id.texto3);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tv1.setTypeface(type);
        tv2.setTypeface(type);
        tv3.setTypeface(type);
        tv3.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
		scanBtn = (Button)findViewById(R.id.scan_button);
        scanBtn.setOnClickListener(this);
        scanBtn.setTypeface(type);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        int nights = settings.getInt("nights",0);

        if(nights > 0)
        {
            Intent myIntent = new Intent(scanBtn.getContext(), AplicationMenu.class);
            startActivityForResult(myIntent, 0);
            finish();
        }

	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClick(View v){
		//respond to clicks
		if(v.getId()==R.id.scan_button){

            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();

			}
	}

    public void getReservation(String id){
        RequestParams params = new RequestParams("id", id);
        AriaRestClient.get("reservation/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    progressBar.setVisibility(View.GONE);
                    JSONObject valid = new JSONObject(jsonObject.getString("valid"));
                    String g1= valid.getString("value");
                    String g2="true";

                    if(g1.equals(g2))
                    {
                        //escribo en db
                        tv3.setVisibility(View.GONE);
                        JSONObject data = new JSONObject(jsonObject.getString("data"));

                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("client_id",data.getString("client_id"));
                        editor.putString("in",data.getString("in"));
                        editor.putString("out",data.getString("out"));
                        editor.putInt("nights",Integer.parseInt(data.getString("nights")));
                        editor.putString("reservation_number",data.getString("reservation_number"));
                        editor.commit();
                    }
                    else
                    {
                        //codigo invalido
                        tv3.setText("Codigo Invalido");

                    }



                } catch (Exception e) {
                    tv3.setText("Intente nuevamente");
                }
            }
        });
    }

    public void getClient(String id){
        RequestParams params = new RequestParams("id", id);
        AriaRestClient.get("client/", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    progressBar.setVisibility(View.GONE);
                    JSONObject valid = new JSONObject(jsonObject.getString("valid"));
                    String g1= valid.getString("value");
                    String g2="true";

                    if(g1.equals(g2))
                    {
                        //escribo en db
                        tv3.setVisibility(View.GONE);
                        JSONObject data = new JSONObject(jsonObject.getString("data"));

                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("name",data.getString("name"));
                        editor.putString("last_name",data.getString("last_name"));
                        editor.putString("email",data.getString("email"));
                        editor.putString("phone",data.getString("phone"));
                        editor.commit();

                        Intent myIntent = new Intent(scanBtn.getContext(), AplicationMenu.class);
                        startActivityForResult(myIntent, 0);
                        finish();
                    }
                    else
                    {
                        //codigo invalido
                        tv3.setText("Codigo Invalido");

                    }



                } catch (Exception e) {
                    tv3.setText("Intente nuevamente");
                }
            }
        });
    }

   public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            //we have a result
            scan_content = scanningResult.getContents();
            tv3.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
            getReservation(scan_content);

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            String client_id = settings.getString("client_id","vacio");

            getClient(client_id);


        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Error al escanear, intente nuevamente", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
