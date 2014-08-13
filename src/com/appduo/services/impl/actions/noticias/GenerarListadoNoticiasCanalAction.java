package com.appduo.services.impl.actions.noticias;

import java.util.List;

import android.content.Context;

import com.appduo.modelo.Canal;
import com.appduo.modelo.Noticia;
import com.appduo.persistencia.factoria.PersistenceFactory;

public class GenerarListadoNoticiasCanalAction {
	
	private Context context;
	private Canal canal;

	public GenerarListadoNoticiasCanalAction(Context context, Canal canal) {
		this.context = context;
		this.canal = canal;
	}

	public List<Noticia> ejecutar() {
		return PersistenceFactory.getNoticiasPersistenceService(context)
				.generarListadoNoticiasCanal(canal.getIdCanal());
	}

}
