package com.appduo.persistencia.helper;

import android.database.sqlite.SQLiteDatabase;

/**
 * Esta clase debe gestionar y mantener una única conexión a la base de datos,
 * para que la base de datos no se bloquee.
 */
public class DatabaseManager {

	private int openCounter;
	private static SQLiteDatabase bbdd;
	private static DatabaseHelper databaseHelper;
	private static DatabaseManager instance;

	public static synchronized void initialize(DatabaseHelper helper) {
		if (instance == null) {
			instance = new DatabaseManager();
			databaseHelper = helper;
		}
	}

	public static synchronized DatabaseManager getInstance() {
		if (instance == null) {
			throw new IllegalStateException(
					DatabaseManager.class.getSimpleName()
							+ " is not initialized, call initialize(..) method first.");
		}

		return instance;
	}

	public synchronized SQLiteDatabase openDatabase() {
        openCounter++;
        if(openCounter == 1) {
            // Opening new database
            bbdd = databaseHelper.getWritableDatabase();
        }
        return bbdd;
    }

    public synchronized void closeDatabase() {
        openCounter--;
        if(openCounter == 0) 
            // Closing database
            bbdd.close();

      
    }
}
