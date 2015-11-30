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
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class socketChannel extends Service {
    String ip = "nothingYet";
    private Socket client;
    private PrintWriter printwriter;

    //private DataInputStream in;
    //private DataOutputStream out;

    public socketChannel() {
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

                SocketChannel socketChannel = SocketChannel.open();
                socketChannel.connect(new InetSocketAddress("10.0.0.7", 9999));

                String newData = "New String to write to file..." + System.currentTimeMillis();

                ByteBuffer buf = ByteBuffer.allocate(48);
                buf.clear();
                buf.put(newData.getBytes());

                buf.flip();

                while(buf.hasRemaining()) {
                    socketChannel.write(buf);
                }

                return "done";
            }
            catch (IOException e) {
                return "Not done";
            }
            finally {

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
