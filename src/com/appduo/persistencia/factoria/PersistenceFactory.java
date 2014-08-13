package com.appduo.persistencia.factoria;

import android.content.Context;

import com.appduo.persistencia.daos.CanalesDAO;
import com.appduo.persistencia.daos.CanalesDaoSqlite;
import com.appduo.persistencia.daos.NoticiasDAO;
import com.appduo.persistencia.daos.NoticiasDaoSqlite;

public class PersistenceFactory {

	public static CanalesDAO getCanalesPersistenceService(Context context) {
		return new CanalesDaoSqlite(context);
	}

	public static NoticiasDAO getNoticiasPersistenceService(Context context) {
		return new NoticiasDaoSqlite(context);
	}

}
