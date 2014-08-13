package com.appduo.services.impl.actions.borrado;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.appduo.modelo.Canal;
import com.appduo.modelo.Noticia;
import com.appduo.persistencia.factoria.PersistenceFactory;

public class BorrarNoticiasCanalAction {

	private Canal canal;
	private Long fecha;
	private Context context;

	public BorrarNoticiasCanalAction(Canal canal, Long fecha, Context context) {
		this.canal = canal;
		this.fecha = fecha;
		this.context = context;
	}

	public void ejecutar() {
		List<Noticia> noticiasABorrar = new ArrayList<Noticia>();
		// Recuperar las noticias de un canal
		List<Noticia> noticias = PersistenceFactory
				.getNoticiasPersistenceService(context)
				.generarListadoNoticiasCanal(this.canal.getIdCanal());
		// Compararlas con respecto a la fecha determinada
		// Si son antiguas, añadirlas para borrarlas.
		for (Noticia noticia : noticias) {
			if (noticia.getFecha() > this.fecha)
				noticiasABorrar.add(noticia);
		}
		// Borrar las noticias (si hay alguna)
		if (!noticiasABorrar.isEmpty()) {
			PersistenceFactory.getNoticiasPersistenceService(context)
					.eliminarNoticia(noticiasABorrar);
		}
	}

}
