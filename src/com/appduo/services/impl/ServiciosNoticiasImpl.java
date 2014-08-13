/**
 * 
 */
package com.appduo.services.impl;

import java.util.List;

import android.content.Context;

import com.appduo.modelo.Canal;
import com.appduo.modelo.Noticia;
import com.appduo.services.ServiciosNoticias;
import com.appduo.services.impl.actions.noticias.AnularNoticiaGuardadaAction;
import com.appduo.services.impl.actions.noticias.GenerarListadoNoticiasAction;
import com.appduo.services.impl.actions.noticias.GenerarListadoNoticiasCanalAction;
import com.appduo.services.impl.actions.noticias.GenerarListadoNoticiasGuardadasAction;
import com.appduo.services.impl.actions.noticias.GuardarNoticiaAction;
import com.appduo.services.impl.actions.noticias.LeerNoticiaPorIdentificadorAction;

public class ServiciosNoticiasImpl implements ServiciosNoticias {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.services.ServiciosNoticias#generarListadoNoticiasCanal(android
	 * .content.Context, com.appduo.modelo.Canal)
	 */
	@Override
	public List<Noticia> generarListadoNoticiasCanal(Context context,
			Canal canal) {
		return new GenerarListadoNoticiasCanalAction(context, canal).ejecutar();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.services.ServiciosNoticias#generarListadoCompletoNoticias(
	 * android.content.Context)
	 */
	@Override
	public List<Noticia> generarListadoCompletoNoticias(Context context) {
		return new GenerarListadoNoticiasAction(context).ejecutar();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.services.ServiciosNoticias#generarListadoNoticiasGuardadas
	 * (android.content.Context)
	 */
	@Override
	public List<Noticia> generarListadoNoticiasGuardadas(Context context) {
		return new GenerarListadoNoticiasGuardadasAction(context).ejecutar();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.services.ServiciosNoticias#getNoticiaPorIdentificador(android
	 * .content.Context, java.lang.Long)
	 */
	@Override
	public Noticia getNoticiaPorIdentificador(Context context, Long idNoticia) {
		return new LeerNoticiaPorIdentificadorAction(context, idNoticia)
				.ejecutar();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.services.ServiciosNoticias#guardarNoticia(android.content.
	 * Context, java.lang.Long)
	 */
	@Override
	public void guardarNoticia(Context context, Noticia noticia) {
		new GuardarNoticiaAction(context, noticia).ejecutar();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.appduo.services.ServiciosNoticias#anularNoticiaGuardada(android.content
	 * .Context, java.lang.Long)
	 */
	@Override
	public void anularNoticiaGuardada(Context context, Noticia noticia) {
		new AnularNoticiaGuardadaAction(context, noticia).ejecutar();
	}

}
