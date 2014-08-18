package com.appduo.segundoplano;

import com.appduo.services.factoria.ServicesFactory;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServicioDescarga extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	/**
	 * Método que lanza el trabajo que ejecutará el servicio. Simplemente
	 * arranca el servicio de descarga de datos en paralelo
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onStart(final Intent intent, int startID) {
		super.onStart(intent, startID);

		new Thread(new Runnable() {
			@Override
			public void run() {
				// descargar noticias
				ServicesFactory.crearServicioDescarga().iniciarServicioDescargaNoticias(getBaseContext(),null, false);
			}
		}).start();
		this.stopSelf();
	}

}
