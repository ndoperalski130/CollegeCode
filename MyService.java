package com.example.congestion;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;

public class MyService extends Service {
    LocationManager lm;
    MyLocationListener ml;
    public double lat, lon;
    public String currentTime;
    Pinger.myPinger pinger;
    public String ID = "channel_1";
    private int NOTIFICATION = 1;
    private NotificationManager nM;

    public class LocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    // default
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // start pinging when the service starts
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        startPing();
        return START_STICKY;        // if service starts, great
                                    // otherwise try again...but safer
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service starting...", Toast.LENGTH_LONG).show();
        pinger  = new Pinger.myPinger(this);    // creates a pinger notification
        nM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        showNotification();

        super.onCreate();
    }

    // stop pinging when the screen comes back
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service destroying...", Toast.LENGTH_LONG).show();
        stopPing();
        destroyNotificationChannel();
        super.onDestroy();
    }

    // create the notification that we are creating a service
    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "Service";
            String description = "The service to run in the background";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // calls when service is destroyed
    // remove the notification
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void destroyNotificationChannel()
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.deleteNotificationChannel(ID);
    }

    // almost a copy of the pinger activities ping function
    public class MyLocationListener implements LocationListener {

        int count = 0;

        @Override
        public void onLocationChanged(Location location)
        {
            lat = location.getLatitude();
            lon = location.getLongitude();
            currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            Log.i("Debug", "Value of lat: " + lat);
            Log.i("Debug", "Value of lon: " + lon);
            Log.i("Debug", "Current time: " + currentTime);

            count++;


            Insert(lat, lon, currentTime);
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

    // display the notification that our service is running in the background
    public void showNotification()
    {
        CharSequence text = "Service is running";

        PendingIntent intent = PendingIntent.getActivity(this, 0, new Intent(this, MyService.class), 0);

        Notification not = new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setTicker(text)
                .setContentTitle("Service")
                .setContentText(text)
                .setContentIntent(intent)
                .build();

        nM.notify(NOTIFICATION, not);
    }

    // basically a copy of the function from Pinger.java
    public void startPing() {
        // allows us to access the gps
        ml = new MyLocationListener();
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            // no permission request needed here
        }

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 1, ml);      // gets the location every 10 seconds regardless of movement

    }

    // again a copy from Pinger.java
    public void stopPing()
    {
        if(lm != null)
        {
            this.lm.removeUpdates(ml);
        }
        Log.i("Debug", "About to Insert()");
        Insert(lat, lon, currentTime);

    }

    // final copy from Pinger.java
    public void Insert(final double lat, final double lon, final String time)
    {
        //ProgressDialog dialog = new ProgressDialog(Pinger.this);
        Log.i("Debug", "Value of time: " + time);
        Toast.makeText(this, "Inserting data...", Toast.LENGTH_LONG).show();

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String>
        {

            @Override
            protected String doInBackground(String... strings) {
                String latHolder = Double.toString(lat);
                String lonHolder = Double.toString(lon);
                String timeHolder = time;

                List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

                nameValuePairList.add(new BasicNameValuePair("Latitude", latHolder));
                nameValuePairList.add(new BasicNameValuePair("Longitude", lonHolder));
                nameValuePairList.add(new BasicNameValuePair("timeOfPing", timeHolder));

                try
                {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://compsci04.snc.edu/cs460/2021/dopend/PHP%20&%20JS/insert.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, HTTP.UTF_8));
                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    StatusLine line = httpResponse.getStatusLine();

                    if(line.getStatusCode() == HttpStatus.SC_OK)
                    {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        httpResponse.getEntity().writeTo(out);
                        out.close();
                    }
                    else
                    {
                        //Toast.makeText(this, "Error: Unable to connect", Toast.LENGTH_LONG).show();
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

            }
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(Double.toString(lat), Double.toString(lon), time);
    }
}
