package com.appduo.actividades;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

/**
 * Clase que enseña un dialogo si no hay internet.
 */
public class DialogoNoHayInternet extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setMessage("Para descargar las últimas noticias, es necesaria la conexión a internet.")
				.setTitle("No hay conexión a internet")
				.setPositiveButton("Ajustes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								//lanzar ajustes de internet
								startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
							}
						})
				.setNegativeButton("Salir",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								//salir de la aplicación
								System.exit(0);
							}
						});

		return builder.create();
	}

}
