package com.albornoz.albornoztp1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Guardando la identificación del action del intent que viene desde el sistema
        String intentAction = intent.getAction();
        int flag = 0;

        if (intentAction != null) {
            String toastMessage = "Acción de intent desconocida";
            switch (intentAction) {
                // Caso del USB conectado
                case "android.hardware.usb.action.USB_STATE":
                    toastMessage = "USB CONECTADO";
                    flag = 1;
                    break;
                // Caso del cargador conectado. Para usar emulador
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = "Cargador conectado";
                    flag = 1;
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage = "Cargador desconectado";
                    flag = 0;
                    break;
            }

            // Mostrando el toast
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();

            // Llamando al 911 si USB o Cargador está conectado
            if (flag == 1) {
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "911"));
                context.startActivity(i);
            }
        }
    }
}