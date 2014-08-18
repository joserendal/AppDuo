package com.appduo.crawler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.appduo.modelo.Canal;
import com.appduo.modelo.Noticia;


public class UnioviRssReader {
	
	private Canal canal;
	private List<Noticia> noticias;
	private Long idUltimaNoticia;
	private boolean primeraDescarga;

	public UnioviRssReader(Canal canal, Long idUltimaNoticia, boolean primeraDescarga) {
		this.canal = canal;
		this.noticias = new ArrayList<Noticia>();
		this.idUltimaNoticia = idUltimaNoticia;
		this.primeraDescarga = primeraDescarga;
	}

	/**
	 * Método principal de la clase. Se encarga de descargar y parsear el canal
	 * rss. Almacena el resultado en la lista de noticias.
	 */
	public void parse() {
		try {
			// Create the document, using the URL
			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();
			Document doc = b.parse(canal.getEnlaceCanal());

			// loop through all items
			NodeList items = doc.getElementsByTagName("item");

			iterateAndParseNodelist(items);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Una vez que se descargue la página, da forma a la lista de noticias con
	 * la informacion.
	 * 
	 * @param items
	 *            - Lista de nodos de la hoja RSS.
	 */
	private void iterateAndParseNodelist(NodeList items) {
		int totalNoticias = 0;
		if(primeraDescarga)
			totalNoticias = 10;
		else
			totalNoticias = items.getLength();
		
		
		for (int i = 0; i < totalNoticias; i++) {
			Node n = items.item(i);
			if (n.getNodeType() != Node.ELEMENT_NODE)
				continue;

			// Create the element
			Element e = (Element) n;

			// Recoge la información de la noticia
			String titulo = parseTitulo(e);
			String fecha = getFecha(e);
			@SuppressWarnings("deprecation")
			Date date = new Date(fecha);
			String link = getEnlace(e);
			// Parsea la página web contenedora de la noticia para extraer
			// los detalles concretos de la noticia
			UnioviWebReader webreader = parseWebPage(link);

			// Crea la noticia, y guardala en la lista de noticias recuperadas.
			Noticia noticia = new Noticia();
			noticia.setTitulo(titulo);
			noticia.setFecha(date.getTime());
			noticia.setTextoNoticia(webreader.getDescripcion());
			noticia.setEnlace(link);
			noticia.setGuardada(false);
			noticia.setIdCanal(this.canal.getIdCanal());
			noticia.setIdNoticia(webreader.getIdNoticia());
			noticia.setOrigen(webreader.getOrigen());
			
			//Para ahorrar trafico de datos, no se descargarán mas noticias de las necesarias
			if(noticia.getIdNoticia() <= this.idUltimaNoticia)
				break;
			
			//falta añadir origen a la base de datos
			
			this.getNoticias().add(noticia);
		}
	}

	/**
	 * Este método parsea la página web para buscar el origen y el cuerpo de la
	 * noticia.
	 * 
	 * @param enlace
	 *            - Enlace de la noticia
	 * @return - Web parser de la página web.
	 */
	private UnioviWebReader parseWebPage(String enlace) {
		UnioviWebReader uw = new UnioviWebReader(enlace);
		uw.parse();
		return uw;
	}

	/**
	 * Extrae el enlace del nodo.
	 * 
	 * @param e
	 *            Nodo de la hoja RSS
	 * @return Valor de la hoja
	 */
	private String getEnlace(Element e) {
		// get the "link element"
		NodeList linkList = e.getElementsByTagName("link");
		Element linkElem = (Element) linkList.item(0);
		return linkElem.getChildNodes().item(0).getNodeValue();
	}

	/**
	 * Extrae la fecha del nodo.
	 * 
	 * @param e
	 *            Nodo de la hoja RSS
	 * @return Valor de la hoja
	 */
	private String getFecha(Element e) {
		// get the "date elem"
		NodeList dateList = e.getElementsByTagName("pubDate");
		Element dateElem = (Element) dateList.item(0);
		return dateElem.getChildNodes().item(0).getNodeValue();
	}

	/**
	 * Extrae el titulo del nodo.
	 * 
	 * @param e
	 *            Nodo de la hoja RSS
	 * @return Valor de la hoja
	 */
	private String parseTitulo(Element e) {
		// get the "title elem" in this item (only one)
		NodeList titleList = e.getElementsByTagName("title");
		Element titleElem = (Element) titleList.item(0);
		return titleElem.getChildNodes().item(0).getNodeValue();
	}

	// getters y setters
	public Canal getCanal() {
		return canal;
	}

	public void setCanal(Canal canal) {
		this.canal = canal;
	}

	public void setNoticias(List<Noticia> noticias) {
		this.noticias = noticias;
	}

	public List<Noticia> getNoticias() {
		return noticias;
	}
}
