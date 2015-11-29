package com.distributedworker.nishant.www.distributedcomputingapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

                client = new Socket("10.0.0.34", 8001);
               // System.out.println("client ready");

                //DataOutputStream out = new DataOutputStream(client.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream(),"UTF-8"));

                String filename = "myfile.txt";
                String line = "Hello world!";
                String Nextline = "Hello world!";
                FileOutputStream outputStream;
                int count = 0;
              while ((line = br.readLine()) != null) {//!(line = br.readLine().toString()).equals(null)) { //br.readLine() != null) { //
                  //line = br.readLine().toString();
//                  count++;
//                    if(count == 5)
//                        break;
                  System.out.println(line);
                    try {
                        outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                        outputStream.write(line.getBytes());
                        outputStream.write("Hello from Nishant".getBytes());
                        outputStream.close();
                        System.out.println("File written");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //System.out.println(br.readLine().toString());
                }


                //Read the gile just saved
                System.out.println("File open");
                FileInputStream fin = openFileInput(filename);
                int c;
                String temp="";
                while( (c = fin.read()) != -1){
                    temp = temp + Character.toString((char)c);
                }
                //string temp contains all the data of the file.
                fin.close();
                System.out.println("Ready to send");
                PrintWriter printwriter;
                printwriter = new PrintWriter(client.getOutputStream(),true);
                printwriter.println(temp);       // sending to server
                printwriter.flush();                    // flush the data
                System.out.println("File sent");

                br.close();
                return "done";
            }
            catch (IOException e) {
                System.out.println("Excpetion " +e);
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
