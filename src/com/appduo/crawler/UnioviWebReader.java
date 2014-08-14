package com.appduo.crawler;

import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class UnioviWebReader {

    private String enlace;
    private String descripcion;
    private String origen;

    public UnioviWebReader(String enlace) {
        this.enlace = enlace;
        this.origen = "Universidad de Oviedo";
    }

    /**
     * Este método descarga la página web a la máquina local
     * para extraer los detalles adicionales de la noticia
     * que no se incluyen en el canal RSS de la Universidad.
     */
    public void parse()
    {
        try {
        	//Descargamos la página con JSOUP
            Document doc = Jsoup.connect(this.enlace).timeout(10*1000).get();

            //Lee el elemento origen de la noticia
            Element origen = doc.select("p.origen").first();
            if(origen != null)
                 this.origen = origen.text();

            //Lee la descripcion
            Elements nodos = doc.select("p");

            if(origen == null) //En el caso de que no lleven origen, la descripción va en el parrafo 2
                this.descripcion = nodos.get(2).text();
            //Para el canal 'becas' el parrafo es el 3...
            else if(!nodos.get(3).text().isEmpty())
                this.descripcion = nodos.get(3).text();
            else
                this.descripcion = nodos.get(4).text();

            //Comprobación de que no hay descripcion
            if(this.descripcion.contains("Síguenos en: LinkedIn FacebookTwitter"))
                this.descripcion= "No se han podido recuperar mas detalles.";

        }catch(Exception e)
        {
           e.printStackTrace();
        }
    }

    public String getDescripcion() {
        return descripcion;
    }

	public String getEnlace() {
		return enlace;
	}

	public Long getIdNoticia() {
		//Procedemos a parsear el enlace en busca del identificador único
		Long idNoticia = null;
		//Troceamos la url
		String[] urlTroceada = Pattern.compile("/").split(this.enlace);
		//Seleccionamos el ultimo trozo
		String identificador = urlTroceada[urlTroceada.length-1];
		//Lo convertimos a Long y lo devolvemos
		idNoticia = Long.parseLong(identificador);
		
		return idNoticia;
	}

	public String getOrigen() {
		return origen;
	}

   
}
