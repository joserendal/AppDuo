package com.appduo.services;

import java.util.List;

import android.content.Context;

import com.appduo.modelo.Canal;
import com.appduo.modelo.Noticia;

public interface ServiciosNoticias 
{
	/**
	 * M�todo que genera el listado de todas las noticias de un canal
	 * determinado. Este m�todo supone que el canal existe previamente.
	 * 
	 * @param idCanal
	 *            - Identificador �nico del canal.
	 * @return Listado de noticias del canal.
	 */
	public List<Noticia> generarListadoNoticiasCanal(Context context, Canal canal);


	/**
	 * Genera el listado completo de noticias almacenadas en la base de datos,
	 * de todos los canales disponibles.
	 * 
	 * @return Listado de canales completo
	 */
	public List<Noticia> generarListadoCompletoNoticias(Context context);

	/**
	 * Genera el listado completo de noticias guardadas, independientemente del
	 * canal.
	 * 
	 * @return Listado de noticias guardadas.
	 */
	public List<Noticia> generarListadoNoticiasGuardadas(Context context);

	/**
	 * M�todo que recupera una noticia en base a su identificador �nico.
	 * 
	 * @param idNoticia
	 *            Identificador de la noticia
	 * @return Noticia encontrada/null si no se encuentra
	 */
	public Noticia getNoticiaPorIdentificador(Context context, Long idNoticia);

	/**
	 * M�todo que marca una noticia como guardada
	 * 
	 * @param idNoticia
	 *            Identificador de la noticia a guardar
	 */
	public void guardarNoticia(Context context,Noticia noticia);

	/**
	 * M�todo que anula una noticia guardada en la base de datos
	 * 
	 * @param idNoticia
	 *            Identificador de la noticia a guardar
	 */
	public void anularNoticiaGuardada(Context context,Noticia noticia);


}
