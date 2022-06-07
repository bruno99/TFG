package com.bruno.syncro;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;


import java.io.IOException;
import java.util.UUID;


/**
 * @author Bruno Urbán Alfaro
 * @version 1.0
 * {@link "https://github.com/bruno99" Follow me on Github}
 *
 */

public class MainActivity extends AppCompatActivity {
    /**
     * Buttoms to logout, increase & decrease speed, go to settings, connect & disconect bluetooth
     * FirebaseAuth for the autentication
     */

    ImageButton mLogoutBtn, btnLess, btnMore, setting, disconnect;
    FirebaseAuth fAuth;
    //-------------------
    String address = null;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    //-------------------
    /**
     * @param savedInstanceState
     */
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        address = intent.getStringExtra(Ajustes.EXTRA_ADDRESS);

        mLogoutBtn= findViewById(R.id.logoutBtn);
        btnLess = findViewById(R.id.btnLess);
        btnMore = findViewById(R.id.btnMore);
        setting = findViewById(R.id.setting);
        fAuth = FirebaseAuth.getInstance();
        disconnect = findViewById(R.id.disconnect);

        //new MainActivity.ConnectBT().execute();




        btnLess.setOnClickListener(new View.OnClickListener() {
            /**
             * Decreases motor speed
             * @param view
             */
            @Override
            public void onClick(View view) {
                sendSignal("a");
                Toast.makeText(getApplicationContext(),"Menos velocidad", Toast.LENGTH_SHORT).show();

            }
        });

        btnMore.setOnClickListener(new View.OnClickListener() {
            /**
             * Increases motor speed
             * @param view
             */
            @Override
            public void onClick(View view) {
                sendSignal("b");
                Toast.makeText(getApplicationContext(),"Más velocidad", Toast.LENGTH_SHORT).show();
            }
        });


        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Logout from Firebase
             * @param view
             */
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            /**
             * Go to settings menu
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Ajustes.class));
            }

        });
        disconnect.setOnClickListener(new View.OnClickListener() {
            /**
             * Calls disonnect function
             * @param view
             */
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Desconectado", Toast.LENGTH_SHORT).show();
                Disconnect();

            }
        });


    }

    /**
     * Sends signal to hc-05
     * @param number
     */
    private void sendSignal ( String number ) {
        if ( btSocket != null ) {
            try {
                btSocket.getOutputStream().write(number.toString().getBytes());
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Disconnects bluetooth
     */
    private void Disconnect () {
        if ( btSocket!=null ) {
            try {
                btSocket.close();
            } catch(IOException e) {
                Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }

/*
    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;


        /**
         * @param devices
         * @return
         */
    /*
        @Override
        protected Void doInBackground (Void... devices) {
            try {
                if ( btSocket==null || !isBtConnected ) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }

            return null;
        }

        /**
         * @param result
         */
    /*
        @Override
        protected void onPostExecute (Void result) {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed.");
                finish();
            } else {
                msg("Connected");
                isBtConnected = true;
            }
        }
    }
    */
}
