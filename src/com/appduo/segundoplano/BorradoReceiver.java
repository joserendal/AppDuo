package com.appduo.segundoplano;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Clase que recibe la alerta de la alarma y se encarga de lanzar los servicios
 */
public class BorradoReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		//arrancar el servicio
		Intent servicio = new Intent(context, ServicioBorrado.class);
		context.startService(servicio);
	}

}
