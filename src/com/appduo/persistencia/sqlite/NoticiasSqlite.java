package com.appduo.persistencia.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appduo.modelo.Noticia;

public class NoticiasSqlite {

	private SQLiteDatabase db;

	public NoticiasSqlite(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * Método que genera el listado de todas las noticias de un canal
	 * determinado. Este método supone que el canal existe previamente.
	 * 
	 * @param idCanal
	 *            - Identificador único del canal.
	 * @return Listado de noticias del canal.
	 */
	public List<Noticia> generarListadoNoticiasCanal(int idCanal) {
		List<Noticia> noticias = new ArrayList<Noticia>();

		String[] args = new String[] { idCanal+"" };
		Cursor cursor = db.rawQuery("SELECT * FROM noticias WHERE id_canal=? ",
				args);

		// Si no hay resultados, devolver null
		if (cursor.getCount() == 0) {
			cursor.close();
			return null;
		}

		// Si los hay, recuperarlos y almacenarlos
		while (cursor.moveToNext()) {
			Noticia noticia = formarNoticia(cursor);
			noticias.add(noticia);
		}
		cursor.close();

		return noticias;
	}

	/**
	 * Método que forma la noticia a partir de el cursor de una consulta de la
	 * base de datos.
	 * 
	 * @param cursor
	 *            - Cursor resultado de la consulta.
	 * @return Noticia formada
	 */
	private Noticia formarNoticia(Cursor cursor) {
		Noticia noticia = new Noticia();
		noticia.setTitulo(cursor.getString(0));
		noticia.setTextoNoticia(cursor.getString(1));
		noticia.setEnlace(cursor.getString(2));
		noticia.setFecha(cursor.getLong(3));
		// Comprueba si la noticia está guardada o no
		String guardado = cursor.getString(4);
		switch (guardado) {
		case ("si"):
			noticia.setGuardada(true);
			break;
		default:
			noticia.setGuardada(false);
			break;
		}
		noticia.setOrigen(cursor.getString(5));
		noticia.setIdNoticia(cursor.getLong(6));
		noticia.setIdCanal(cursor.getInt(7));
		return noticia;
	}

	/**
	 * Genera el listado completo de noticias almacenadas en la base de datos,
	 * de todos los canales disponibles.
	 * 
	 * @return Listado de canales completo
	 */
	public List<Noticia> generarListadoCompletoNoticias() {
		List<Noticia> noticias = new ArrayList<Noticia>();

		Cursor cursor = db.rawQuery("SELECT * FROM noticias", null);

		// Si no hay resultados, devolver null
		if (cursor.getCount() == 0) {
			cursor.close();
			return null;
		}

		// Si los hay, recuperarlos y almacenarlos
		while (cursor.moveToNext()) {
			Noticia noticia = formarNoticia(cursor);
			noticias.add(noticia);
		}
		cursor.close();

		return noticias;
	}

	/**
	 * Genera el listado completo de noticias guardadas, independientemente del
	 * canal.
	 * 
	 * @return Listado de noticias guardadas.
	 */
	public List<Noticia> generarListadoNoticiasGuardadas() {
		List<Noticia> noticias = new ArrayList<Noticia>();

		String[] args = new String[] { "si" };
		Cursor cursor = db.rawQuery("SELECT * FROM noticias WHERE guardado=? ",
				args);

		// Si no hay resultados, devolver null
		if (cursor.getCount() == 0) {
			cursor.close();
			return null;
		}

		// Si los hay, recuperarlos y almacenarlos
		while (cursor.moveToNext()) {
			Noticia noticia = formarNoticia(cursor);
			noticias.add(noticia);
		}
		cursor.close();

		return noticias;
	}

	/**
	 * Este método recupera el máximo identificador único de las noticias
	 * almacenadas en el canal.
	 * 
	 * @param idCanal
	 *            Identificador único del canal
	 * @return Máximo identificador de la noticia
	 */
	public Long getMaximoIdentificadorCanal(int idCanal) {
		Long maximoIdentificador = null;

		String[] args = new String[] { idCanal+"" };
		Cursor cursor = db.rawQuery(
				"SELECT max(id_noticia) FROM noticias WHERE id_canal=?", args);

		// Si no hay resultados, devolver null
		if (cursor.getCount() == 0) {
			cursor.close();
			return null;
		}

		// Si los hay, recuperarlos y almacenarlos
		while (cursor.moveToNext()) {
			maximoIdentificador = cursor.getLong(0);
		}
		cursor.close();
		return maximoIdentificador;
	}

	/**
	 * Método que recupera una noticia en base a su identificador único.
	 * 
	 * @param idNoticia
	 *            Identificador de la noticia
	 * @return Noticia encontrada/null si no se encuentra
	 */
	public Noticia getNoticiaPorIdentificador(Long idNoticia) {
		Noticia noticia = null;

		String[] args = new String[] { idNoticia.toString() };
		Cursor cursor = db.rawQuery("SELECT * FROM noticias WHERE id_noticia=?", args);

		// Si no hay resultados, devolver null
		if (cursor.getCount() == 0) {
			cursor.close();
			return null;
		}

		// Si los hay, recuperarlos y almacenarlos
		while (cursor.moveToNext()) {
			noticia = formarNoticia(cursor);
		}
		cursor.close();

		return noticia;
	}

	/**
	 * Método que crea y almacena una nueva noticia en la base de datos
	 * 
	 * @param noticia
	 *            - Noticia a guardar.
	 */
	public void crearNoticia(Noticia noticia) {
		ContentValues content = new ContentValues();
		content.put("titulo", noticia.getTitulo());
		content.put("descripcion", noticia.getTextoNoticia());
		content.put("link", noticia.getEnlace());
		content.put("fecha", noticia.getFecha());
		content.put("origen", noticia.getOrigen());
		content.put("guardado", noticia.isGuardada() ? "si" : "no");
		content.put("id_noticia", noticia.getIdNoticia());
		content.put("id_canal", noticia.getIdCanal());
		db.insert("noticias", null, content);
	}

	/**
	 * Método que elimina una noticia de la base de datos.
	 * 
	 * @param idNoticia
	 *            - Identificador de la noticia a borrar
	 */
	public void eliminarNoticia(Long idNoticia) {
		db.delete("noticias", "id_noticia = " + idNoticia, null);
	}

	/**
	 * Método que marca una noticia como guardada
	 * 
	 * @param idNoticia
	 *            Identificador de la noticia a guardar
	 */
	public void guardarNoticia(Long idNoticia) {
		ContentValues content = new ContentValues();
		content.put("guardado", "si");
		db.update("noticias", content, "id_noticia=" + idNoticia, null);
	}

	/**
	 * Método que anula una noticia guardada en la base de datos
	 * 
	 * @param idNoticia
	 *            Identificador de la noticia a guardar
	 */
	public void anularNoticiaGuardada(Long idNoticia) {
		ContentValues content = new ContentValues();
		content.put("guardado", "no");
		db.update("noticias", content, "id_noticia=" + idNoticia, null);
	}

}
