package com.appduo.segundoplano;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.appduo.services.factoria.ServicesFactory;

public class ServicioBorrado extends Service {

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
	 * arranca el servicio de borrado de datos en paralelo
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onStart(final Intent intent, int startID) {
		super.onStart(intent, startID);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// leer el numero de dias a borrar
				int diasABorrar = leerDiasABorrar();
				// iniciar servicio borrado
				ServicesFactory.crearServicioBorrado().eliminarNoticiasAnterioresA(diasABorrar,	getBaseContext());
			}
		}).start();
		this.stopSelf();
	}

	private int leerDiasABorrar() {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		return pref.getInt("pref_key_intervalo_borrado", 40);
	}

}
