package com.distributedworker.nishant.www.distributedcomputingapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketService extends Service {

    String ip = "nothingYet";
    private Socket client;
    private PrintWriter printwriter;

    //private DataInputStream in;
    //private DataOutputStream out;

    public SocketService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful

        Toast.makeText(this, "This is from service", Toast.LENGTH_LONG).show();

        //ip = RunSocketClient();
        DownloadWebPageTask task = new DownloadWebPageTask();
        task.execute();



        return Service.START_NOT_STICKY;
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {

                client = new Socket("10.0.0.7", 10000);  //connect to server
                Log.d("ClientApp", "Started");

                printwriter = new PrintWriter(client.getOutputStream(),true);

                // receiving from server ( receiveRead  object)
                InputStream istream = client.getInputStream();
                BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

                String receiveMessage;
//                String sendMessage = "This is New from Android";
//
//                printwriter.println(sendMessage);       // sending to server
//                printwriter.flush();                    // flush the data


                String filename = "myfile.txt";
                FileOutputStream outputStream;
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                while(true)
                {
                    if((receiveMessage = receiveRead.readLine()) != null) {

                        if(receiveMessage.equals("---fileSendingFinishedByServer---")){
                            System.out.println("End");
                            break;
                        }
                        outputStream.write(receiveMessage.getBytes());
                        //outputStream.write("\n Hello from Nishant \n".getBytes());

                        //System.out.println("From PC - " + receiveMessage); // displaying at DOS prompt

                    }
                }
                outputStream.close();
                System.out.println("Out of while");

                //Read the gile just saved
                System.out.println("File open");

                // OPENING THE REQUIRED TEXT FILE
//                BufferedReader reader = new BufferedReader(new InputStreamReader(
//                        getAssets().open("myfile.txt")));
//
//                String myLine = reader.readLine();


                double count = 0.0;
                long count2 = 0;
                FileInputStream fin = openFileInput(filename);
                int c;
                String temp="";
                while( (c = fin.read()) != -1){
                    temp = temp + Character.toString((char)c);
                    count = count + 0.001;
                    count2++;

                }
                //string temp contains all the data of the file.
                fin.close();
                System.out.println("Ready to send");

                PrintWriter printwriter;
                printwriter = new PrintWriter(client.getOutputStream(),true);
                printwriter.println(count2);       // sending to server
                printwriter.flush();                    // flush the data
                System.out.println("File sent: " +count);



            return "done";
            } catch (IOException e) {
                return "Not done";
            }
            finally {
//                try {
//                    //client.close();   //closing the connection
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

        }
    }

    public String RunSocketClient() {
        try {
            Socket clnt = new Socket("localhost", 4444);
            BufferedReader in = new BufferedReader(new InputStreamReader(clnt.getInputStream()));

            String fromServer;
            fromServer = in.readLine();

            return fromServer;
        } catch (IOException e) {
            return "nothing";
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
