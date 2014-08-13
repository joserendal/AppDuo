package com.appduo.persistencia.daos;

import java.util.List;

import com.appduo.modelo.Canal;

public interface CanalesDAO 
{
	/**
	 * M�todo que genera el listado de canales de la base de datos.
	 * 
	 * @return Listado de canales
	 */
	public List<Canal> generarListadoCanales();

	/**
	 * M�todo que recupera un canal de la base de datos en base a su
	 * identificador �nico
	 * 
	 * @param idCanal
	 *            Identificador del canal
	 * @return Canal encontrado
	 */
	public Canal buscarCanalPorIdentificador(int idCanal);

	/**
	 * M�todo que recupera un canal de la base de datos en base a su nombre
	 * 
	 * @param nombreCanal
	 *            - Nombre del canal
	 * @return canal encontrado
	 */
	public Canal buscarCanalPorNombre(String nombreCanal);
}
