package com.appduo.services;

import android.app.ProgressDialog;
import android.content.Context;

public interface ServicioDescarga 
{
	/**
	 * M�todo que arranca la descarga de nuevas noticias 
	 * y las guarda
	 * @param context
	 * @param pDialog 
	 */
	public void iniciarServicioDescargaNoticias(Context context, ProgressDialog pDialog, boolean primeraDescarga);

}
