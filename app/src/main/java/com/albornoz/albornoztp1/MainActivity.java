package com.albornoz.albornoztp1;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private CustomReceiver mr;
    private IntentFilter filter;
    private Button reg_receiver, unreg_receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermissions();

        // Instanciando BroadcastReceiver
        mr = new CustomReceiver();
        // Instanciando IntentFilter con sus acciones así el sistema sabe qué broadcast mandar
        filter = new IntentFilter();
        filter.addAction("android.hardware.usb.action.USB_STATE");
        // Acciones para probar con el emulador
        //filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        //filter.addAction(Intent.ACTION_POWER_CONNECTED);

        // Inicializando botones
        reg_receiver = findViewById(R.id.startBroadcastReceiver);
        unreg_receiver = findViewById(R.id.stopBroadcastReceiver);

        // Listeners botones
        reg_receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.registerReceiver(mr, filter);
                reg_receiver.setEnabled(false);
                unreg_receiver.setEnabled(true);
            }
        });
        unreg_receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.unregisterReceiver(mr);
                reg_receiver.setEnabled(true);
                unreg_receiver.setEnabled(false);
            }
        });
    }

    private void getPermissions() {
        if(
                Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                        && checkSelfPermission(Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1000);
        }
    }

    @Override
    protected void onDestroy() {
        // Elimina registro del Broadcast Receiver antes de que se cierre la activity
        this.unregisterReceiver(mr);
        super.onDestroy();
    }

}