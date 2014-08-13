package com.appduo.services.impl.actions.canales;

import android.content.Context;

import com.appduo.modelo.Canal;
import com.appduo.persistencia.factoria.PersistenceFactory;

public class BuscarCanalPorNombreAction {

	private Context context;
	private String nombre;

	public BuscarCanalPorNombreAction(Context context, String nombre) {
		this.context = context;
		this.nombre = nombre;
	}

	public Canal ejecutar() {
		return PersistenceFactory.getCanalesPersistenceService(context)
				.buscarCanalPorNombre(nombre);
	}

}
