package com.appduo.services.impl.actions.descarga;

import java.util.List;

import com.appduo.crawler.UnioviRssReader;
import com.appduo.modelo.Canal;
import com.appduo.modelo.Noticia;

public class DescargarNoticiasCanalAction {

	private Canal canal;
	private Long idUltimaNoticia;
	private boolean primeraDescarga;

	public DescargarNoticiasCanalAction(Canal canal, Long idUltimaNoticia, boolean primeraDescarga) {
		this.canal = canal;
		this.idUltimaNoticia = idUltimaNoticia;
		this.primeraDescarga = primeraDescarga;
	}

	public List<Noticia> ejecutar() {
		UnioviRssReader uniovi = new UnioviRssReader(canal, idUltimaNoticia, primeraDescarga);
		uniovi.parse();
		return uniovi.getNoticias();
	}

}
