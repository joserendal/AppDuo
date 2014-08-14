package com.appduo.actividades;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.appduo.MainActivity;
import com.appduo.R;
import com.appduo.modelo.Noticia;
import com.appduo.services.factoria.ServicesFactory;

public class ActivityBienvenidaInicial extends Activity {

	// Botón 'Iniciar descarga de datos'
	Button btnIniciarDescarga;
	// Barra que muestra el progreso de la descarga
	private ProgressDialog pDialog;
	// Tipo de barra de progreso (0 = barra horizontal)
	public static final int progress_bar_type = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bienvenida_inicial);

		// Registrar el listener al botón
		// Al pulsar, se inicia la descarga de datos.
		btnIniciarDescarga = (Button) findViewById(R.id.botonIniciarDescarga);
		btnIniciarDescarga.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new MyDownloadTask().execute("");
			}
		});
		
		
		

		// Si la base de datos no está vacia, hay que cambiar a
		// la otra actividad.
		List<Noticia> noticias = ServicesFactory.crearServicioNoticias()
				.generarListadoCompletoNoticias(getApplicationContext());
		if (noticias != null) {
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
			this.finish();			
		}
		
		//si hay que descargar noticias, comprobar que esté conectado a internet
		boolean hayInternet = isOnline();
		if(!hayInternet)
		{
			new DialogoNoHayInternet().show(getFragmentManager(), CONNECTIVITY_SERVICE);
		}
	}
	

	
	/**
	 * Método que comprueba si hay conexión a internet en el dispositivo
	 * @return
	 */
	private boolean isOnline() {
	    ConnectivityManager cm =  (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnected()) {
	        return true;
	    }
	    return false;
	}


	/**
	 * Método que crea la barra de progreso. Se muestra un mensaje y se irá
	 * actualizando.
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case progress_bar_type:
			pDialog = new ProgressDialog(this);
			pDialog.setMessage("Descargando datos. Mantengase a la espera...");
			pDialog.setIndeterminate(false);
			pDialog.setMax(100);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pDialog.setCancelable(false);
			pDialog.show();
			return pDialog;
		default:
			return null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bienvenida_inicial, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/******************************************************************************************************/

	/**
	 * Clase que realiza la descarga inicial de noticias. Va conectada con la
	 * barra de progreso horizontal, para mostrar el estado de la descarga.
	 */
	class MyDownloadTask extends AsyncTask<String, Integer, String> {

		@SuppressWarnings("deprecation")
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// enseña la barra de progreso
			showDialog(0);
		}

		/**
		 * Tarea a ejecutar en segundo plano. Se realiza la descarga y el
		 * parseamiento inicial de datos.
		 */
		@Override
		protected String doInBackground(String... strings) {
			//Se van a descargar los datos
			ServicesFactory.crearServicioDescarga().iniciarServicioDescargaNoticias(getApplicationContext(), pDialog);
			return null;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(String file_url) {
			// Cuando se acaba la descarga, desaparece la barra
			dismissDialog(progress_bar_type);
			// Se cambia la actividad principal
			Intent i = new Intent(ActivityBienvenidaInicial.this,MainActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			ActivityBienvenidaInicial.this.finish();
		}

	}
}
