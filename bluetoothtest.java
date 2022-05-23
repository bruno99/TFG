package com.bruno.syncro;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Set;
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
    //---------------------------------------------------------

    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //numero universal bluetooth boards

    //---------------------------------------------------------
    ImageButton mLogoutBtn, btnLess, btnMore, setting, disconectBT, conectBT;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLogoutBtn= findViewById(R.id.logoutBtn);
        btnLess = findViewById(R.id.btnLess);
        btnMore = findViewById(R.id.btnMore);
        setting = findViewById(R.id.setting);
        fAuth = FirebaseAuth.getInstance();
        disconectBT = findViewById(R.id.disconectBT);
        conectBT = findViewById(R.id.conectBT);


        //---------------------------------------------------------
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        System.out.println(btAdapter.getBondedDevices());

        BluetoothDevice hc05 = btAdapter.getRemoteDevice("00:21:09:00:3D:AB"); //dirección de nuestro módulo HC-05
        System.out.println(hc05.getName()); //comprobamos que efectivamente es el HC-05
        BluetoothSocket btSocket = null;



        int count = 0;
        do {//para probar la conexión del socket hasta que se conecte
            try {
                btSocket = hc05.createRfcommSocketToServiceRecord(mUUID);
                System.out.println(btSocket);
                btSocket.connect();
                System.out.println(btSocket.isConnected());
            } catch (IOException e) {
                e.printStackTrace();
            }
         count ++;
        }while (!btSocket.isConnected()&& count < 3 );


        try {
            OutputStream outputStream = btSocket.getOutputStream();
            outputStream.write(43);
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
        InputStream inputStream = null;
        try {
            inputStream = btSocket.getInputStream();
            inputStream.skip(inputStream.available()); //por si quedase información en el buffer

            for (int i = 0; i < 26; i++) {

                byte b = (byte) inputStream.read();
                System.out.println((char) b);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        */

            try {
                btSocket.close();
                System.out.println(btSocket.isConnected());
            } catch (IOException e) {
                e.printStackTrace();
            }



        //---------------------------------------------------------

        /**
         * Descrease speed
         * @param btnLess buttom to decrease speed
         */
        btnLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //menos velocidad

            }
        });
        /**
         * Increase speed
         * @param btnMore buttom to increase speed
         */
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //más velocidad

            }
        });

        /**
         * Logout from Firebase
         * @param mLogoutBtn buttom to Logout
         * @return Login page
         */
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();//logout
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
        /**
         * Go to settings menu
         * @param setting buttom
         * @return Settings page
         */
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Ajustes.class));
            }
            /**
             * Connects to a bluetooth device
             * @param conectBT
             * @return successfull/unsuccessfull conection
             */
        });
        conectBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //bluetooth dentro

            }
            /**
             * Disconect Bluetooth
             * @param disconectBtn
             * @return Disconection to previous device
             */
        });
        disconectBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //bluetooth fuera

            }

        });
    }

}
