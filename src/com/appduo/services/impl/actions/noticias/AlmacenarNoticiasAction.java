package com.appduo.services.impl.actions.noticias;

import java.util.List;

import android.content.Context;

import com.appduo.modelo.Noticia;
import com.appduo.persistencia.factoria.PersistenceFactory;

public class AlmacenarNoticiasAction 
{
	private List<Noticia> noticias;
	private Context context;
	
	public AlmacenarNoticiasAction(List<Noticia> noticias, Context context) 
	{
		this.noticias = noticias;
		this.context = context;
	}
	
	public void ejecutar()
	{
		PersistenceFactory.getNoticiasPersistenceService(context).crearNoticia(noticias);
	}
	
	

}
