package com.appduo.services.impl.actions.canales;

import android.content.Context;

import com.appduo.modelo.Canal;
import com.appduo.persistencia.factoria.PersistenceFactory;

public class BuscarCanalPorIdAction {

	private Context context;
	private int idCanal;

	public BuscarCanalPorIdAction(Context context, int idCanal) {
		this.context = context;
		this.idCanal = idCanal;
	}

	public Canal ejecutar() {
		return PersistenceFactory.getCanalesPersistenceService(context)
				.buscarCanalPorIdentificador(idCanal);
	}
}
