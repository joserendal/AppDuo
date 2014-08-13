package com.appduo.persistencia.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appduo.modelo.Canal;

public class CanalesSqlite {

	private SQLiteDatabase db;

	public CanalesSqlite(SQLiteDatabase db) 
	{
		this.db = db;
	}

	/**
	 * Clase que consulta y recupera el listado de canales
	 * de la aplicación almacenadas en la base de datos.
	 * @return Listado de canales del duo.
	 */
	public List<Canal> generarListadoCanales() {
		List<Canal> canales = new ArrayList<Canal>();
		
		Cursor cursor = db.rawQuery("SELECT * FROM canales", null);
		
		//Si no hay resultados, devolver null
		if(cursor.getCount() == 0)
		{
			cursor.close();
			return null;
		}
		
		//Si los hay, recuperarlos y almacenarlos
		while(cursor.moveToNext())
		{
			Canal canal = new Canal();
			canal.setIdCanal(cursor.getInt(0)  );
			canal.setNombreCanal(cursor.getString(1));
			canal.setEnlaceCanal(cursor.getString(2));
			canales.add(canal);			
		}
		cursor.close();
		
		return canales;
	}
	
	/**
	 * Método que consulta y busca un canal en función de 
	 * su identificador único.
	 * @param idCanal Identificador del canal a buscar.
	 * @return Canal encontrado / null si no se encuentra.
	 */
	public Canal leerCanalPorId(int idCanal)
	{
		String[] args = new String[]{idCanal+""};
		Cursor cursor = db.rawQuery("SELECT * FROM canales WHERE id_canal=?", args);
		
		//Si no hay resultados, devolver null
		if(cursor.getCount() == 0)
		{
			cursor.close();
			return null;
		}
		
		//Si los hay, recuperarlos y almacenarlos
		Canal canal = new Canal();
		cursor.moveToNext();
		canal.setIdCanal(cursor.getInt(0)  );
		canal.setNombreCanal(cursor.getString(1));
		canal.setEnlaceCanal(cursor.getString(2));
		cursor.close();
		
		return canal;
	}
	
	/**
	 * Método que busca un canal en función de su nombre.
	 * @param nombreCanal - Nombre del canal a buscar.
	 * @return Canal encontrado.
	 */
	public Canal leerCanalPorNombre(String nombreCanal)
	{
		String[] args = new String[]{nombreCanal};
		Cursor cursor = db.rawQuery("SELECT * FROM canales WHERE titulo=?", args);
		
		//Si no hay resultados, devolver null
		if(cursor.getCount() == 0)
		{
			cursor.close();
			return null;
		}
		
		//Si los hay, recuperarlos y almacenarlos
		Canal canal = new Canal();
		cursor.moveToNext();
		canal.setIdCanal(cursor.getInt(0)  );
		canal.setNombreCanal(cursor.getString(1));
		canal.setEnlaceCanal(cursor.getString(2));
		cursor.close();
		
		return canal;
	}
	
	

}
