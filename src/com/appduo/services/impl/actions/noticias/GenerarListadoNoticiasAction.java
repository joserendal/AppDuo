package com.appduo.services.impl.actions.noticias;

import java.util.List;

import android.content.Context;

import com.appduo.modelo.Noticia;
import com.appduo.persistencia.factoria.PersistenceFactory;

public class GenerarListadoNoticiasAction {
	
	private Context context;

	public GenerarListadoNoticiasAction(Context context) {
		this.context = context;
	}

	public List<Noticia> ejecutar() {
		return PersistenceFactory.getNoticiasPersistenceService(context).generarListadoCompletoNoticias();
	}

}
