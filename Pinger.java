package com.example.congestion;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;

import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.security.NetworkSecurityPolicy;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.json.JSONObject;
import org.json.JSONException;

public class Pinger extends AppCompatActivity {

    LocationManager lm;                 // location manager object
    MyLocationListener ml;              // custom location listener object
    public double lat, lon;             // variables to hold lat and lon
    public String currentTime;          // variable to hold time

    // reference object to MyService.java
    public static class myPinger
    {
        private final Context mContext;
        public myPinger(Context ctx)
        {
            mContext = ctx.getApplicationContext();
        }
    }

    // custom location listener begins
    public class MyLocationListener implements LocationListener {
        // find the TextViews
        TextView tv1 = findViewById(R.id.actualLat);
        TextView tv2 = findViewById(R.id.actualLong);
        TextView tv3 = findViewById(R.id.tTime);

        // initialize a counter
        int count = 0;

        @Override
        public void onLocationChanged(Location location)
        {
            // get the lat and lon
            lat = location.getLatitude();
            lon = location.getLongitude();

            // simple date to display on the screen
            currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            Log.i("Debug", "Value of lat: " + lat);
            Log.i("Debug", "Value of lon: " + lon);
            Log.i("Debug", "Current time: " + currentTime);

            // set the TextViews to the correct info
            tv1.setText(String.valueOf(lat));
            tv2.setText(String.valueOf(lon));
            count++;                                // increment count for a ping
            tv3.setText(String.valueOf(count));
            Insert(lat, lon, currentTime);          // insert function called
        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pinger);
    }

    @Override
    protected void onStart() {

        super.onStart();

    }

    // when the user reopens the app
    // destroy the service
    @Override
    protected void onRestart()
    {
        super.onRestart();
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    // when the user minimizes the app or turns the screen off
    // start the service
    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    // when the activity is destroyed
    // stop the service
    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);

        super.onDestroy();
    }

    // request permissions result
    // did we get the permissions?
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch(requestCode)
        {
            case 1:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(Pinger.this, "Permissions granted", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    // startPing function
    public void startPing(View v) {
        // allows us to access the gps
        ml = new MyLocationListener();
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);  // location manager to get gps
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//          // MUST CALL
            // try to get permissions from user
            requestPermission();
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1, ml);      // gets the location every 10 seconds regardless of movement

    }

    // stopPing function
    public void stopPing(View v)
    {
        // set the TextView for currentTime
        TextView tv;
        tv = findViewById(R.id.actualTime);
        tv.setText(currentTime);

        // if the locationManager is running
        if(lm != null)
        {
            this.lm.removeUpdates(ml);
        }
        Log.i("Debug", "About to Insert()");

        // final insert
        Insert(lat, lon, currentTime);

    }

    // custom requestPermission function
    private void requestPermission()
    {
        ActivityCompat.requestPermissions(Pinger.this, new String[]{ACCESS_FINE_LOCATION}, 0);      // request location permissions
        ActivityCompat.requestPermissions(Pinger.this, new String[]{INTERNET}, 1);                  // request internet permissions
    }

    // Insert function begin
    public void Insert(final double lat, final double lon, final String time)
    {
        //ProgressDialog dialog = new ProgressDialog(Pinger.this);
        Log.i("Debug", "Value of time: " + time);

        // inner functional class
        class SendPostReqAsyncTask extends AsyncTask<String, Void, String>
        {

            // doInBackground call
            // WARNING: Only runs when screen is up
            @Override
            protected String doInBackground(String... strings) {
                // reference variables
                String latHolder = Double.toString(lat);
                String lonHolder = Double.toString(lon);
                String timeHolder = time;

                // Object of key:value pairs
                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

                // add the Latitude, Longitude, and time to list
                // EDIT: Time is no longer necessary
                nameValuePairList.add(new BasicNameValuePair("Latitude", latHolder));
                nameValuePairList.add(new BasicNameValuePair("Longitude", lonHolder));
                nameValuePairList.add(new BasicNameValuePair("timeOfPing", timeHolder));

                // connections
                try
                {
                    // establish connection
                    HttpClient httpClient = new DefaultHttpClient();

                    // attempt to look for script
                    HttpPost httpPost = new HttpPost("http://compsci04.snc.edu/cs460/2021/dopend/PHP%20&%20JS/insert.php");

                    // encode our key:value pair in UTF_8 to send
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, HTTP.UTF_8));

                    // attempt to send the key:value pair
                    // gets a response based on if data can be sent successfully or not
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    StatusLine line = httpResponse.getStatusLine();

                    // connection is ok to send
                    if(line.getStatusCode() == HttpStatus.SC_OK)
                    {
                        // write to the stream for the script to execute
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        httpResponse.getEntity().writeTo(out);
                        out.close();
                    }
                    else
                    {
                        //Toast.makeText(Pinger.this, "Error: Unable to connect", Toast.LENGTH_LONG).show();
                    }

                } catch (ClientProtocolException e)
                {
                    //Toast.makeText(Pinger.this, "ClientProtocol Exception", Toast.LENGTH_LONG).show();
                    return "";
                }
                catch (IOException e)
                {
                    //Toast.makeText(Pinger.this, "IOException", Toast.LENGTH_LONG).show();
                    return "";
                }
                return "Data Inserted Successfully";
            }




            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //dialog.setMessage("Inserting details, please wait...");
                //dialog.setTitle("Connecting");
                //dialog.show();
                //dialog.setCancelable(false);
            }
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //dialog.cancel();

//                AlertDialog.Builder ac = new AlertDialog.Builder(Pinger.this);
//                ac.setTitle("Result");
//                ac.setMessage("Details Successfully Inserted");
//                ac.setCancelable(true);
//
//                ac.setPositiveButton(
//                        "OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialog.cancel();
//                            }
//                        }
//                );
//                AlertDialog alert = ac.create();
//                alert.show();
                Toast.makeText(Pinger.this, "Data Submit Successful", Toast.LENGTH_LONG).show();
            }
        }
        // call the object to start making pings
        // object executes the next Insert with the values obtained
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(Double.toString(lat), Double.toString(lon), time);
    }

}


