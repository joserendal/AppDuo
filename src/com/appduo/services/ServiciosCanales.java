package com.appduo.services;

import java.util.List;

import android.content.Context;

import com.appduo.modelo.Canal;

public interface ServiciosCanales {
	/**
	 * M�todo que genera el listado de canales de la base de datos.
	 * 
	 * @return Listado de canales
	 */
	public List<Canal> generarListadoCanales(Context context);

	/**
	 * M�todo que recupera un canal de la base de datos en base a su
	 * identificador �nico
	 * 
	 * @param idCanal
	 *            Identificador del canal
	 * @return Canal encontrado
	 */
	public Canal buscarCanalPorIdentificador(Context context,int idCanal);

	/**
	 * M�todo que recupera un canal de la base de datos en base a su nombre
	 * 
	 * @param nombreCanal
	 *            - Nombre del canal
	 * @return canal encontrado
	 */
	public Canal buscarCanalPorNombre(Context context,String nombreCanal);

}
