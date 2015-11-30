package com.distributedworker.nishant.www.distributedcomputingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    TextView testing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        testing = (TextView) findViewById(R.id.testing);


    }

    public void buttonConnectClicked(View view)
    {
        // use this to start and trigger a service
        Intent i= new Intent(this, SocketService.class);
        // potentially add data to the intent
        i.putExtra("KEY1", "Value to be used by the service");
        this.startService(i);
    }

    public void receiveFileClicked(View view)
    {
        // use this to start and trigger a service
        Intent i= new Intent(this, FileReceiveService.class);
        // potentially add data to the intent
        i.putExtra("KEY1", "Value to be used by the service");
        this.startService(i);
    }

    public void socketChannelClicked(View view)
    {
        // use this to start and trigger a service
        Intent i= new Intent(this, socketChannel.class);
        // potentially add data to the intent
        i.putExtra("KEY1", "Value to be used by the service");
        this.startService(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
