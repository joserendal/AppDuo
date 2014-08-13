package com.appduo.persistencia.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	//Version de la base de datos
	private static int VERSION = 1;
	
	//Tabla canales
	public static final String NOMBRE_TABLA_CANALES = "canales";
	public static final String ID_CANAL = "id_canal";
	public static final String TITULO = "titulo";
	public static final String LINK = "link";
	
	private static final String CREA_TABLA_CANALES = 
			"CREATE TABLE " + NOMBRE_TABLA_CANALES + " (" +
			ID_CANAL + " integer primary key autoincrement, " +
			TITULO + " text not null, " + 
			LINK + " text not null);";
	
	
	//Tabla noticias
	public static final String NOMBRE_TABLA_NOTICIAS = "noticias";
	public static final String ID_NOTICIA = "id_noticia";
	public static final String DESCRIPCION = "descripcion";
	public static final String FECHA = "fecha";
	public static final String GUARDADO = "guardado";
	
	private static final String CREA_TABLA_NOTICIAS = 
			"CREATE TABLE " + NOMBRE_TABLA_NOTICIAS + " (" + 
			TITULO + " text not null, " +
			DESCRIPCION + " text not null, " +
			LINK + " text not null, " +
			FECHA + " bigint not null," +
			GUARDADO + " text not null, " + 
			"origen text not null, " + 
			ID_NOTICIA + " bigint, " + 
			ID_CANAL + " integer, " +
			"FOREIGN KEY (" + ID_CANAL + ") REFERENCES " + NOMBRE_TABLA_CANALES  + " (id_canal), " +
			"PRIMARY KEY (" + ID_NOTICIA + "," + ID_CANAL + "));";	
	

	public DatabaseHelper(Context context, String name, CursorFactory factory) {
		super(context, name, factory, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREA_TABLA_CANALES);
		db.execSQL(CREA_TABLA_NOTICIAS);
		db.execSQL("PRAGMA foreign_keys = ON;");
		crearCanales(db);
	}

	private void crearCanales(SQLiteDatabase db) {

		// Canal actos
		String titulo = "Actos";
		String link = "http://www.uniovi.es/comunicacion/duo/actos/-/asset_publisher/L2iEKaAZuBNz/rss";
		String crearCanal = "INSERT INTO canales (titulo, link) VALUES ";

		
		db.execSQL(crearCanal + "('" + titulo + "','" + link + "');"  );

		// Canal anuncios
		titulo = "Anuncios";
		link = "http://www.uniovi.es/comunicacion/duo/anuncios/-/asset_publisher/k22otAqlur25/rss";

		db.execSQL(crearCanal + "('" + titulo + "','" + link + "');"  );

		// Canal becas
		titulo = "Becas";
		link = "http://www.uniovi.es/comunicacion/duo/becas/-/asset_publisher/ZICH7zdCzufQ/rss";

		db.execSQL(crearCanal + "('" + titulo + "','" + link + "');"  );

		// Canal boletin
		titulo = "Boletines Oficiales";
		link = "http://www.uniovi.es/comunicacion/duo/boletinesoficiales/-/asset_publisher/7iuBUjFjsoD2/rss";

		db.execSQL(crearCanal + "('" + titulo + "','" + link + "');"  );

		// Canal convocatorias
		link = "http://www.uniovi.es/comunicacion/duo/convocatorias/-/asset_publisher/cxX13ntusT2E/rss";
		titulo = "Convocatorias";

		db.execSQL(crearCanal + "('" + titulo + "','" + link + "');"  );

		// Canal otros
		link = "http://www.uniovi.es/comunicacion/duo/otros/-/asset_publisher/tJrNest4WQMj/rss";
		titulo = "Otros";

		db.execSQL(crearCanal + "('" + titulo + "','" + link + "');"  );

	}

	

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		VERSION++;
		//Al actualizar, se eliminan todos los datos. No se permite esto por el momento.
		/*
		db.execSQL("DROP TABLE " + NOMBRE_TABLA_NOTICIAS);
		db.execSQL("DROP TABLE " + NOMBRE_TABLA_CANALES);
		onCreate(db);
		*/
	}

}
