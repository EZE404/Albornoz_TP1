package com.albornoz.albornoztp1;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private CustomReceiver mr;
    private IntentFilter intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermissions();

        // Instanciando BroadcastReceiver
        mr = new CustomReceiver();
        // Instanciando IntentFilter con sus acciones así el sistema sabe qué broadcast mandar
        intent = new IntentFilter();
        intent.addAction("android.hardware.usb.action.USB_STATE");
        // Acciones para probar con el emulador
        intent.addAction(Intent.ACTION_POWER_DISCONNECTED);
        intent.addAction(Intent.ACTION_POWER_CONNECTED);
    }

    public void registerBroadcastReceiver(View view) {
        // Registrar el Broadcast Receiver desde este contexto para que empiece a recibir Intents.
        this.registerReceiver(mr, intent);
    }

    public void unregisterBroacastReceiver(View view) {
        // Desregistrar Broadcast Receiver para que deje de recibir Intents
        this.unregisterReceiver(mr);
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