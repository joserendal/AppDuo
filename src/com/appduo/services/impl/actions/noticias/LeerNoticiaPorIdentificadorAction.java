package com.appduo.services.impl.actions.noticias;

import android.content.Context;

import com.appduo.modelo.Noticia;
import com.appduo.persistencia.factoria.PersistenceFactory;

public class LeerNoticiaPorIdentificadorAction {

	private Context context;
	private Long idNoticia;

	public LeerNoticiaPorIdentificadorAction(Context context, Long idNoticia) {
		this.context = context;
		this.idNoticia = idNoticia;
	}

	public Noticia ejecutar() {
		return PersistenceFactory.getNoticiasPersistenceService(context).getNoticiaPorIdentificador(idNoticia);
	}

}
