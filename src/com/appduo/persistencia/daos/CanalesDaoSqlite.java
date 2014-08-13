package com.appduo.persistencia.daos;

import java.util.List;

import android.content.Context;

import com.appduo.modelo.Canal;
import com.appduo.persistencia.helper.DatabaseHelper;
import com.appduo.persistencia.helper.DatabaseManager;
import com.appduo.persistencia.sqlite.CanalesSqlite;

public class CanalesDaoSqlite implements CanalesDAO {

	private DatabaseManager database;
	private Context context;

	public CanalesDaoSqlite(Context context) {
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

	@Override
	public List<Canal> generarListadoCanales() {
		// Crea una conexión
		crearConexion();
		// Pasala a la capa inferior
		CanalesSqlite cnsqlite = new CanalesSqlite(this.database.openDatabase());
		List<Canal> canales = cnsqlite.generarListadoCanales();
		// cierra la base de datos
		DatabaseManager.getInstance().closeDatabase();
		return canales;
	}

	@Override
	public Canal buscarCanalPorIdentificador(int idCanal) {
		// Crea una conexión
		crearConexion();
		// Pasala a la capa inferior
		CanalesSqlite cnsqlite = new CanalesSqlite(this.database.openDatabase());
		Canal canal = cnsqlite.leerCanalPorId(idCanal);
		// cierra la base de datos
		DatabaseManager.getInstance().closeDatabase();
		return canal;
	}

	@Override
	public Canal buscarCanalPorNombre(String nombreCanal) {
		// Crea una conexión
		crearConexion();
		// Pasala a la capa inferior
		CanalesSqlite cnsqlite = new CanalesSqlite(this.database.openDatabase());
		Canal canal = cnsqlite.leerCanalPorNombre(nombreCanal);
		// cierra la base de datos
		DatabaseManager.getInstance().closeDatabase();
		return canal;
	}

}
