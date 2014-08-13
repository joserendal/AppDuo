package com.appduo.services.impl.actions.noticias;

import android.content.Context;

import com.appduo.modelo.Noticia;
import com.appduo.persistencia.factoria.PersistenceFactory;

public class AnularNoticiaGuardadaAction {
	
	private Context context;
	private Noticia noticia;

	public AnularNoticiaGuardadaAction(Context context, Noticia noticia) {
		this.context = context;
		this.noticia = noticia;
	}

	public void ejecutar() {
		PersistenceFactory.getNoticiasPersistenceService(context)
				.anularNoticiaGuardada(noticia.getIdNoticia());
	}
}
