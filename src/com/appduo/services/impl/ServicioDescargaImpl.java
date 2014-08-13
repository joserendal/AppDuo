/**
 * 
 */
package com.appduo.services.impl;

import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;

import com.appduo.modelo.Canal;
import com.appduo.modelo.Noticia;
import com.appduo.persistencia.factoria.PersistenceFactory;
import com.appduo.services.ServicioDescarga;
import com.appduo.services.impl.actions.descarga.DescargarNoticiasCanalAction;
import com.appduo.services.impl.actions.noticias.AlmacenarNoticiasAction;

public class ServicioDescargaImpl implements ServicioDescarga {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.services.ServicioDescarga#iniciarServicioDescargaNoticias(
	 * android.content.Context)
	 */
	@Override
	public void iniciarServicioDescargaNoticias(Context context, ProgressDialog pDialog) {
		// recuperar los canales
		List<Canal> canales = recuperarListadoCanales(context);

		// crear los hilos
		List<Thread> hilos = generarListadoHilos(canales, context);

		// arrancar hilos y trabajar
		for (Thread hilo : hilos)
			hilo.start();
		
		for (int i = 0; i < hilos.size(); i++)
			try {
				hilos.get(i).join();
				
				//actualizar la barra de progreso
				if(pDialog != null)
				{
					pDialog.setProgress((int)(((float)(i+1))/6*100));					
				}				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

	}

	/**
	 * Método que genera el listado de hilos, y los almacena en la lista
	 * asignandoles un trabajo a realizar.
	 * @param canales - Canales a descargar
	 * @param context - Contexto
	 * @return Listado de hilos a arrancar
	 */
	private List<Thread> generarListadoHilos(List<Canal> canales, final Context context) {
		List<Thread> hilos = new ArrayList<Thread>();

		//por cada canal
		for (Canal canal : canales) {
			final Canal cln = canal;
			//crear hilos
			Thread hilo = new Thread(new Runnable() {
				@Override
				public void run() {
					//recuperar el id de la ultima noticia del canal
					Long idUltimaNoticia = PersistenceFactory
							.getNoticiasPersistenceService(context)
							.getMaximoIdentificadorCanal(cln.getIdCanal());
					//descargar las nuevas noticias
					List<Noticia> noticias = new DescargarNoticiasCanalAction(cln, idUltimaNoticia).ejecutar();
					if (!noticias.isEmpty()) { //si hay nuevas noticias
						//almacenar las noticias guardadas
						new AlmacenarNoticiasAction(noticias, context).ejecutar();
					}
				}
			});
			hilos.add(hilo);
		}

		return hilos;
	}

	/**
	 * Método que recupera el listado de canales almacenados
	 * en el sistema
	 * @param context contexto a utilizar
	 * @return Listado de canales
	 */
	private List<Canal> recuperarListadoCanales(Context context) {
		return PersistenceFactory.getCanalesPersistenceService(context)
				.generarListadoCanales();
	}
}
