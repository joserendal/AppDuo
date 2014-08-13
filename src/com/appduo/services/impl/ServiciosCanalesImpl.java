/**
 * 
 */
package com.appduo.services.impl;

import java.util.List;

import android.content.Context;

import com.appduo.modelo.Canal;
import com.appduo.services.ServiciosCanales;
import com.appduo.services.impl.actions.canales.BuscarCanalPorIdAction;
import com.appduo.services.impl.actions.canales.BuscarCanalPorNombreAction;
import com.appduo.services.impl.actions.canales.GenerarListadoCanalesAction;

public class ServiciosCanalesImpl implements ServiciosCanales {

	/* (non-Javadoc)
	 * @see com.appduo.services.ServiciosCanales#generarListadoCanales(android.content.Context)
	 */
	@Override
	public List<Canal> generarListadoCanales(Context context) {
		return new GenerarListadoCanalesAction(context).ejecutar();
	}

	/* (non-Javadoc)
	 * @see com.appduo.services.ServiciosCanales#buscarCanalPorIdentificador(android.content.Context, java.lang.Long)
	 */
	@Override
	public Canal buscarCanalPorIdentificador(Context context, int idCanal) {
		return new BuscarCanalPorIdAction(context, idCanal).ejecutar();
	}

	/* (non-Javadoc)
	 * @see com.appduo.services.ServiciosCanales#buscarCanalPorNombre(android.content.Context, java.lang.String)
	 */
	@Override
	public Canal buscarCanalPorNombre(Context context, String nombreCanal) {
		return new BuscarCanalPorNombreAction(context, nombreCanal).ejecutar();
	}

}
