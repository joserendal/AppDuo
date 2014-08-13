package com.appduo.services.factoria;

import com.appduo.services.ServicioBorrado;
import com.appduo.services.ServicioDescarga;
import com.appduo.services.ServiciosCanales;
import com.appduo.services.ServiciosNoticias;
import com.appduo.services.impl.ServicioBorradoImpl;
import com.appduo.services.impl.ServicioDescargaImpl;
import com.appduo.services.impl.ServiciosCanalesImpl;
import com.appduo.services.impl.ServiciosNoticiasImpl;

public class ServicesFactory {

	public static ServicioBorrado crearServicioBorrado() {
		return new ServicioBorradoImpl();
	}

	public static ServicioDescarga crearServicioDescarga() {
		return new ServicioDescargaImpl();
	}

	public static ServiciosCanales crearServicioCanales() {
		return new ServiciosCanalesImpl();
	}

	public static ServiciosNoticias crearServicioNoticias() {
		return new ServiciosNoticiasImpl();
	}
}
