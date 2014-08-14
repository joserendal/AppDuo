package com.appduo.segundoplano;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Clase que recibe la alerta de la alarma y se encarga de lanzar los servicios
 */
public class DescargaReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		//arrancar el servicio
		Intent servicio = new Intent(context, ServicioDescarga.class);
		context.startService(servicio);
		//cuando acabe la ejecución parar
		context.stopService(servicio);
	}

}
