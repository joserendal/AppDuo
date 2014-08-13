package com.appduo.services.impl.actions.canales;

import java.util.List;

import android.content.Context;

import com.appduo.modelo.Canal;
import com.appduo.persistencia.factoria.PersistenceFactory;

public class GenerarListadoCanalesAction {

	private Context context;

	public GenerarListadoCanalesAction(Context context) {
		this.context = context;
	}

	public List<Canal> ejecutar() {
		return PersistenceFactory.getCanalesPersistenceService(context)
				.generarListadoCanales();
	}

}
