/**
 * 
 */
package com.appduo.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;

import com.appduo.modelo.Canal;
import com.appduo.persistencia.factoria.PersistenceFactory;
import com.appduo.services.ServicioBorrado;
import com.appduo.services.impl.actions.borrado.BorrarNoticiasCanalAction;

public class ServicioBorradoImpl implements ServicioBorrado {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.appduo.services.ServicioBorrado#eliminarNoticiasAnterioresA(int,
	 * android.content.Context)
	 */
	@Override
	public void eliminarNoticiasAnterioresA(int numeroDias,
			final Context context) {
		// Calcular la fecha en base a la actual del sistema
		final Date fechaFinal = calcularFecha(numeroDias);

		// Paralelizar el borrado
		// Crear una lista de hilos
		List<Thread> hilos = new ArrayList<Thread>();
		// Recuperar la lista de canales del sistema
		List<Canal> canales = PersistenceFactory.getCanalesPersistenceService(
				context).generarListadoCanales();
		// Crear listado de hilos. cada uno realiza un borrado
		crearYArrancarHilos(context, fechaFinal, hilos, canales);
	}

	/**
	 * Método que crea y arranca los hilos que se encargarán del borrado
	 * @param context
	 * @param fechaFinal
	 * @param hilos
	 * @param canales
	 */
	private void crearYArrancarHilos(final Context context,
			final Date fechaFinal, List<Thread> hilos, List<Canal> canales) {
		for (Canal canal : canales) {
			final Canal cnl = canal;
			Thread hilo = new Thread(new Runnable() {
				@Override
				public void run() {
					BorrarNoticiasCanalAction borrado = new BorrarNoticiasCanalAction(
							cnl, fechaFinal.getTime(), context);
					borrado.ejecutar();
				}
			});
			hilos.add(hilo);
		}
		// Lanzar los hilos
		for (Thread hilo : hilos)
		{
			hilo.start();
		}
		//esperar los hilos
		for (Thread hilo : hilos)
		{
			try {
				hilo.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Método que calcula la fecha a partir de la
	 * cual deben borrarse las noticias antiguas.
	 * @param numeroDias
	 * @return
	 */
	private Date calcularFecha(int numeroDias) {
		// Calcular la fecha en base a la actual del sistema
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, -numeroDias);
		return calendar.getTime();
	}
}
