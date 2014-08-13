package com.appduo.services;

import android.content.Context;

public interface ServicioBorrado {

	/**
	 * Este método elimina, lanzando varios hilos, todas las noticias anteriores
	 * a un numero de dias predeterminado.
	 * 
	 * @param numeroDias
	 * @param context
	 */
	public void eliminarNoticiasAnterioresA(int numeroDias, Context context);

}
