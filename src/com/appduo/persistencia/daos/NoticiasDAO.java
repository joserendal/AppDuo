package com.appduo.persistencia.daos;

import java.util.List;

import com.appduo.modelo.Noticia;

public interface NoticiasDAO {
	
	/**
	 * M�todo que genera el listado de todas las noticias de un canal
	 * determinado. Este m�todo supone que el canal existe previamente.
	 * 
	 * @param idCanal
	 *            - Identificador �nico del canal.
	 * @return Listado de noticias del canal.
	 */
	public List<Noticia> generarListadoNoticiasCanal(int idCanal);


	/**
	 * Genera el listado completo de noticias almacenadas en la base de datos,
	 * de todos los canales disponibles.
	 * 
	 * @return Listado de canales completo
	 */
	public List<Noticia> generarListadoCompletoNoticias();

	/**
	 * Genera el listado completo de noticias guardadas, independientemente del
	 * canal.
	 * 
	 * @return Listado de noticias guardadas.
	 */
	public List<Noticia> generarListadoNoticiasGuardadas();

	/**
	 * Este m�todo recupera el m�ximo identificador �nico de las noticias
	 * almacenadas en el canal.
	 * 
	 * @param idCanal
	 *            Identificador �nico del canal
	 * @return M�ximo identificador de la noticia
	 */
	public Long getMaximoIdentificadorCanal(int idCanal);

	/**
	 * M�todo que recupera una noticia en base a su identificador �nico.
	 * 
	 * @param idNoticia
	 *            Identificador de la noticia
	 * @return Noticia encontrada/null si no se encuentra
	 */
	public Noticia getNoticiaPorIdentificador(Long idNoticia);

	/**
	 * M�todo que crea y almacena una nueva noticia en la base de datos
	 * 
	 * @param noticia
	 *            - Noticia a guardar.
	 */
	public void crearNoticia(Noticia noticia);
	
	/**
	 * M�todo que a�ade, de forma transaccional, un listado determinado de noticias.
	 * @param noticia - Listado de noticias a crear
	 */
	public void crearNoticia(List<Noticia> noticia);

	/**
	 * M�todo que elimina una noticia de la base de datos.
	 * 
	 * @param idNoticia
	 *            - Identificador de la noticia a borrar
	 */
	public void eliminarNoticia(Long idNoticia);
	
	/**
	 * M�todo que borra, de forma transaccional, un listado determinado de noticias.
	 * @param noticia - Listado de noticias a borrar
	 */
	public void eliminarNoticia(List<Noticia> noticia);

	/**
	 * M�todo que marca una noticia como guardada
	 * 
	 * @param idNoticia
	 *            Identificador de la noticia a guardar
	 */
	public void guardarNoticia(Long idNoticia);

	/**
	 * M�todo que anula una noticia guardada en la base de datos
	 * 
	 * @param idNoticia
	 *            Identificador de la noticia a guardar
	 */
	public void anularNoticiaGuardada(Long idNoticia);

}
