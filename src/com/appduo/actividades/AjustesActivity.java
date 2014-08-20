package com.appduo.actividades;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.appduo.R;
import com.appduo.segundoplano.BorradoReceiver;
import com.appduo.segundoplano.DescargaReceiver;

/**
 * Esta clase gestionará el menú de ajustes de la aplicación.
 */
public class AjustesActivity extends PreferenceActivity {
	
	
	/**
	 * Carga el archivo XML donde se ha definido el menú de ajustes
	 * con elementos Preference.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);				
		addPreferencesFromResource(R.xml.ajustes);		
		//Define las preferencias de los botones de los ajustes
		definirPreferenciasAjustes();
	}

	/**
	 * Establece el comportamiento personalizado
	 * de algunos de los botones del menú de ajustes.
	 * Asocia cada botón a un comportamiento.
	 */
	@SuppressWarnings("deprecation")
	private void definirPreferenciasAjustes() {		
		//Botón 'Administrar las suscripciones'
		Preference pref = findPreference("pref_key_administrar_suscripciones");
		 pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {		 
		      @Override
		      public boolean onPreferenceClick(Preference preference) {
		    	administrarSuscripciones();
		         return false;
		      }	
		});		
		 //Botón 'Anular las suscripciones'
		 pref = findPreference("pref_key_anular_todas_suscripciones");
		 pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {		 
		      @Override
		      public boolean onPreferenceClick(Preference preference) {
		    	  anularTodasLasSuscripciones();
		         return false;
		      }		
		});
		 //Botón 'limpiar ahora'
		 pref = findPreference("pref_key_limpiar_ahora");
		 pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {		 
		      @Override
		      public boolean onPreferenceClick(Preference preference) {
		    	 limpiarAhora();
		         return false;
		      }		
		});
		//Botón 'actualización manual'
		 pref = findPreference("pref_key_actualizar_manualmente");
		 pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {		 
		      @Override
		      public boolean onPreferenceClick(Preference preference) {
		    	 actualizarAhora();
		         return false;
		      }		
		});
		 //Botón 'acerca de'
		 pref = findPreference("pref_key_acerca_de");
		 pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {		 
		      @Override
		      public boolean onPreferenceClick(Preference preference) {
		    	 lanzarMensajeAcercaDe();
		         return false;
		      }		
		});
		 //Botón 'ayuda'
		 pref = findPreference("pref_key_comandos_voz");
		 pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {		 
		      @Override
		      public boolean onPreferenceClick(Preference preference) {
		    	 lanzarAyuda();
		         return false;
		      }		
		});
	}
	
	/**
	 * Método que lanza la ayuda relacionada
	 * con los comandos de voz de la aplicación desde
	 * el menú de ajustes.
	 */
	private void lanzarAyuda()
	{
		showMessage(getString(R.string.tituloAyuda),R.string.mensajeAyuda, getString(R.string.aceptar));
	}
	
	/**
	 * Método que lanza la información acerca de
	 * la aplicación desde el menú de ajustes.
	 */
	private void lanzarMensajeAcercaDe()
	{
		showMessage("Acerca de",R.string.textoAcerdaDe, getString(R.string.aceptar));
	}
	
	/**
	 * Método que muestra un dialogo informativo en la pantalla
	 * al usuario con un único botón, 'Aceptar' que al ser pulsado
	 * cerrará el dialogo.
	 * 
	 * @param title - Título del dialogo.
	 * @param msg - Mensaje principal del dialogo.
	 * @param txButton - Texto del botón para cerrar.
	 */
	private void showMessage(String title, int msg, String txButton)
    {
    	new AlertDialog.Builder(this)
    	.setTitle(title)
    	.setIcon(R.drawable.acercade)
    	.setMessage(msg)
    	.setNeutralButton(txButton, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		}).show();
    }
	
	/**
	 * Método encargado de limpiar la base de datos de noticias antiguas
	 * bajo demanda del usuario. Aquí se lanzará el mensaje de advertencia
	 * y se lanzará el borrado.
	 */
	private void limpiarAhora()
	{
		new AlertDialog.Builder(this)
		.setTitle("Atención:")
		.setMessage( R.string.mensajeAvisoLimpiarAhora )
		.setNegativeButton("No", new DialogInterface.OnClickListener() {						
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{		
			}
		})
		.setNeutralButton("Si", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				lanzarServicioBorrado();					
				Toast.makeText(getApplicationContext(), R.string.toastAvisoLimpiarAhora , Toast.LENGTH_SHORT).show();
			}
			
		}).show();
	}

	/**
	 * Método encargado de relanzar el servicio temporizado de limpieza
	 * de la base de datos de noticias antiguas, bajo petición del usuario.
	 */
	private void lanzarServicioBorrado() {
		//Recoge la alarma
		 AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

		 Intent myIntentBorrado = new Intent(this, BorradoReceiver.class);
		 PendingIntent pendingIntentBorrado = PendingIntent.getBroadcast(this, 0, myIntentBorrado,0);

		 try {
			 //Cancela la alarma existente
		      alarmManager.cancel(pendingIntentBorrado);
		      //Programa una nueva (se ejecutará ahora mismo y quedará programada)
		      long millis =  (long) (24*3600*1000);	
		      alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10, millis, pendingIntentBorrado);
		 } catch (Exception e) {
		      Log.e("AppDuo.Ajustes", getString(R.string.noSePudoCancelarAlarma) + e.toString());
		 }
	}
	
	/**
	 * Método encargado de relanzar el servicio de descarga de nuevas noticias
	 * bajo petición del usuario, volviendo a temporizarlo.
	 */
	private void actualizarAhora()
	{
		//Recoge la alarma
		 AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

		 Intent myIntent = new Intent(this, DescargaReceiver.class);
		 PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent,0);

		 // Cancela la alrma
		 try {
			 //Cancela la alarma existente
		      alarmManager.cancel(pendingIntent);
		      //Programa una nueva (se ejecutará ahora mismo y quedará programada)
		      SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences( this );	
		      int tiempo = pref.getInt("pref_key_intervalo_actualizacion", 6);
		      long millis =  (long) (tiempo*3600*1000);	
		      alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10, millis, pendingIntent);
		      Toast.makeText(this, R.string.comprobaraNuevasNoticias , Toast.LENGTH_SHORT).show();
		 } catch (Exception e) {
		      Log.e("AppDuo.Ajustes", getString(R.string.noPudoCancelarAlarma) + e.toString());
		 }
	}
	
	/**
	 * Método que lanza el administrador de suscripciones
	 * del usuario.
	 */
	private void administrarSuscripciones() {
		Intent mIntent = new Intent(this, SuscripcionesActivity.class);
		startActivity(mIntent);		
	}	
	
	/**
	 * Método que anula todas las suscripciones del usuario, mostrándole un dialogo
	 * de confirmación primero. 
	 */
	private void anularTodasLasSuscripciones() {
		new AlertDialog.Builder(this)
		.setTitle("Atención:")
		.setMessage(R.string.seBorraranSuscripciones)
		.setNegativeButton("No", new DialogInterface.OnClickListener() {						
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				Toast.makeText(getApplicationContext(), R.string.noBorraranSuscripciones , Toast.LENGTH_SHORT).show();				
			}
		})
		.setNeutralButton("Si", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) 
			{
				borrarSuscripciones();						
				Toast.makeText(getApplicationContext() , R.string.todasSuscripcionesBorradas , Toast.LENGTH_SHORT).show();
			}			
		}).show();
	}


	/**
	 * Método que borrará todas las suscripciones almacenadas
	 * en las preferencias de la aplicación.
	 */
	private void borrarSuscripciones() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences( this );  		
  		//Anula todas las suscripciones
  		pref.edit().putBoolean("pref_suscribir_actos", false).commit();
  		pref.edit().putBoolean("pref_suscribir_anuncios", false).commit();
  		pref.edit().putBoolean("pref_suscribir_becas", false).commit();
  		pref.edit().putBoolean("pref_suscribir_boletines_oficiales", false).commit();
  		pref.edit().putBoolean("pref_suscribir_convocatorias", false).commit();
  		pref.edit().putBoolean("pref_suscribir_otros", false).commit();
	}

	

}
