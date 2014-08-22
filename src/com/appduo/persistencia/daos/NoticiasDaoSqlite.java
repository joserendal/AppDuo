package com.appduo.persistencia.daos;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.appduo.modelo.Canal;
import com.appduo.modelo.Noticia;
import com.appduo.persistencia.helper.DatabaseHelper;
import com.appduo.persistencia.helper.DatabaseManager;
import com.appduo.persistencia.sqlite.CanalesSqlite;
import com.appduo.persistencia.sqlite.NoticiasSqlite;

public class NoticiasDaoSqlite implements NoticiasDAO {

	private DatabaseManager database;
	private Context context;

	public NoticiasDaoSqlite(Context context) {
		this.context = context;
	}

	private void crearConexion() {
		try {
			// Intenta abrir una conexión
			this.database = DatabaseManager.getInstance();
		} catch (IllegalStateException e) {
			// No está abierta, hay que crearla.
			DatabaseHelper dbHelper = new DatabaseHelper(context, "AppDuo.db",
					null);
			DatabaseManager.initialize(dbHelper);
			this.database = DatabaseManager.getInstance();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.persistencia.daos.NoticiasDAO#generarListadoNoticiasCanal(
	 * java.lang.Long)
	 */
	@Override
	public List<Noticia> generarListadoNoticiasCanal(int idCanal) {
		// Crea una conexión
		crearConexion();
		SQLiteDatabase db = this.database.openDatabase();
		// Comprobar que existe el canal
		CanalesSqlite cnsqlite = new CanalesSqlite(db);
		Canal canal = cnsqlite.leerCanalPorId(idCanal);
		if (canal == null)
			throw new IllegalArgumentException("No existe el canal " + idCanal);

		// Si existe, generar el listado de noticias
		NoticiasSqlite noticiasSqlite = new NoticiasSqlite(db);
		List<Noticia> noticias = noticiasSqlite
				.generarListadoNoticiasCanal(idCanal);

		this.database.closeDatabase();
		return noticias;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.persistencia.daos.NoticiasDAO#generarListadoCompletoNoticias()
	 */
	@Override
	public List<Noticia> generarListadoCompletoNoticias() {
		// Crea una conexión
		crearConexion();
		NoticiasSqlite noticiasSqlite = new NoticiasSqlite(
				this.database.openDatabase());
		List<Noticia> noticias = noticiasSqlite
				.generarListadoCompletoNoticias();
		this.database.closeDatabase();
		return noticias;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.persistencia.daos.NoticiasDAO#generarListadoNoticiasGuardadas
	 * ()
	 */
	@Override
	public List<Noticia> generarListadoNoticiasGuardadas() {
		// Crea una conexión
		crearConexion();
		NoticiasSqlite noticiasSqlite = new NoticiasSqlite(
				this.database.openDatabase());
		List<Noticia> noticias = noticiasSqlite
				.generarListadoNoticiasGuardadas();
		this.database.closeDatabase();
		return noticias;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.persistencia.daos.NoticiasDAO#getMaximoIdentificadorCanal(
	 * java.lang.Long)
	 */
	@Override
	public Long getMaximoIdentificadorCanal(int idCanal) {
		// Crea una conexión
		crearConexion();
		SQLiteDatabase db = this.database.openDatabase();
		// Comprobar que existe el canal
		CanalesSqlite cnsqlite = new CanalesSqlite(db);
		Canal canal = cnsqlite.leerCanalPorId(idCanal);
		if (canal == null)
			throw new IllegalArgumentException("No existe el canal " + idCanal);

		// Si existe, generar el listado de noticias
		NoticiasSqlite noticiasSqlite = new NoticiasSqlite(db);
		Long idNoticia = noticiasSqlite.getMaximoIdentificadorCanal(idCanal);
		this.database.closeDatabase();
		return idNoticia;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.persistencia.daos.NoticiasDAO#getNoticiaPorIdentificador(java
	 * .lang.Long)
	 */
	@Override
	public Noticia getNoticiaPorIdentificador(Long idNoticia) {
		// Crea una conexión
		crearConexion();
		NoticiasSqlite noticiasSqlite = new NoticiasSqlite(
				this.database.openDatabase());
		Noticia noticia = noticiasSqlite.getNoticiaPorIdentificador(idNoticia);
		this.database.closeDatabase();
		return noticia;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.persistencia.daos.NoticiasDAO#crearNoticia(com.appduo.modelo
	 * .Noticia)
	 */
	@Override
	public void crearNoticia(Noticia noticia) {
		// Crea una conexión
		crearConexion();
		NoticiasSqlite noticiasSqlite = new NoticiasSqlite(
				this.database.openDatabase());
		noticiasSqlite.crearNoticia(noticia);
		this.database.closeDatabase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.persistencia.daos.NoticiasDAO#eliminarNoticia(java.lang.Long)
	 */
	@Override
	public void eliminarNoticia(Long idNoticia) {
		// Crea una conexión
		crearConexion();
		NoticiasSqlite noticiasSqlite = new NoticiasSqlite(
				this.database.openDatabase());
		noticiasSqlite.eliminarNoticia(idNoticia);
		this.database.closeDatabase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.persistencia.daos.NoticiasDAO#guardarNoticia(java.lang.Long)
	 */
	@Override
	public void guardarNoticia(Long idNoticia) {
		// Crea una conexión
		crearConexion();
		NoticiasSqlite noticiasSqlite = new NoticiasSqlite(
				this.database.openDatabase());
		noticiasSqlite.guardarNoticia(idNoticia);
		this.database.closeDatabase();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.persistencia.daos.NoticiasDAO#anularNoticiaGuardada(java.lang
	 * .Long)
	 */
	@Override
	public void anularNoticiaGuardada(Long idNoticia) {
		// Crea una conexión
		crearConexion();
		NoticiasSqlite noticiasSqlite = new NoticiasSqlite(
				this.database.openDatabase());
		noticiasSqlite.anularNoticiaGuardada(idNoticia);
		this.database.closeDatabase();
	}

	@Override
	public void crearNoticia(List<Noticia> noticia) {
		// Crea una conexión a la base de datos
		crearConexion();
		SQLiteDatabase db = this.database.openDatabase();
		// Inicia la transaccion
		db.beginTransaction();
		// crea todas las noticias
		try {
			for (Noticia not : noticia) {
				NoticiasSqlite notsqlite = new NoticiasSqlite(db);
				Noticia notLeida = null;
				//mirar si existe la noticia
				if(not.getIdNoticia() != null)
					notLeida = notsqlite.getNoticiaPorIdentificador(not.getIdNoticia());
				//si no existe, crearla
				if(notLeida == null)				
					notsqlite.crearNoticia(not);
			}
			// marca la transacción como correcta
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		this.database.closeDatabase();
	}

	@Override
	public void eliminarNoticia(List<Noticia> noticia) {
		// Crea una conexión a la base de datos
		crearConexion();
		SQLiteDatabase db = this.database.openDatabase();
		// Inicia la transaccion
		db.beginTransaction();
		//borra todas las noticias
		try {
			for (Noticia not : noticia) {
				NoticiasSqlite notsqlite = new NoticiasSqlite(db);
				notsqlite.eliminarNoticia(not.getIdNoticia());
			}
			// marca la transacción como correcta
			db.setTransactionSuccessful();
		} finally {
			db.endTransaction();
		}
		this.database.closeDatabase();
	}

}
