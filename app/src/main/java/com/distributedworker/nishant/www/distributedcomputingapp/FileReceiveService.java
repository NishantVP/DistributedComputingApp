package com.distributedworker.nishant.www.distributedcomputingapp;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class FileReceiveService extends Service {

    Socket client;

    public FileReceiveService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful

        Toast.makeText(this, "This is from File Rx Service", Toast.LENGTH_LONG).show();

        //ip = RunSocketClient();
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute();



        return Service.START_NOT_STICKY;
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {

                client = new Socket("10.0.0.34", 1099);
                System.out.println("client ready");

                //DataOutputStream out = new DataOutputStream(client.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

                while (br.readLine() != null) {
                    System.out.println(br.readLine().toString());
                }
                br.close();

                return "done";
            }
            catch (IOException e) {
                return "Not done";
            }
            finally {
                try {
                    client.close();   //closing the connection
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
