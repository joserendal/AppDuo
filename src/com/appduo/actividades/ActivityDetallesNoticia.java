package com.appduo.actividades;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.appduo.R;

public class ActivityDetallesNoticia extends Activity {

	private static String textoDetallesNoticia;
	private static String textoTituloNoticia;
	private static String enlaceNoticia;
	private Long fechaNoticia;
	private Date fecha;
	private String textoOrigenNoticia;
	private static TextToSpeech tts;

	/**
	 * Método que recoge los datos que pasa la otra actividad y los almacena en
	 * el objeto
	 * 
	 * @param bundle
	 */
	private void recogerDatosIntent(Bundle bundle) {
		textoDetallesNoticia = bundle.getString("detalles_noticia");
		textoTituloNoticia = bundle.getString("titulo_noticia");
		fechaNoticia = bundle.getLong("fecha_noticia");
		fecha = new Date(fechaNoticia);
		textoOrigenNoticia = bundle.getString("origen_noticia");
		enlaceNoticia = bundle.getString("enlace_noticia");
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalles_noticia);

		// Recoger el texto de la noticiaa
		recogerDatosIntent(getIntent().getExtras());

		// escribir

		// Detalles de la noticia
		TextView textViewDetallesNoticia = (TextView) findViewById(R.id.textViewDetalleNoticias);
		textViewDetallesNoticia.setText(textoDetallesNoticia);
		// Fecha de la noticia
		TextView textViewFecha = (TextView) findViewById(R.id.textViewFechaNoticia);
		textViewFecha.setText(fecha.getDate() + "-" + (fecha.getMonth() + 1)
				+ "-" + (fecha.getYear() + 1900));
		// titulo noticia
		TextView textViewTituloNoticia = (TextView) findViewById(R.id.textViewTituloNoticias);
		textViewTituloNoticia.setText(textoTituloNoticia);
		textViewTituloNoticia.setTextColor(Color.parseColor("#637c96"));
		textViewTituloNoticia.setTypeface(null, Typeface.BOLD);
		// origen de la noticia
		TextView textViewOrigenNoticia = (TextView) findViewById(R.id.textViewOrigenNoticia);
		textViewOrigenNoticia.setText(textoOrigenNoticia);

		// Botones
		final Button botonVoz = (Button) findViewById(R.id.botonLeer);
		botonVoz.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// activar voz
				if (tts == null || !tts.isSpeaking()) {
					tts = new TextToSpeech(getBaseContext(), null);
					botonVoz.setText("Parar la lectura");
					tts.speak(textoTituloNoticia, TextToSpeech.QUEUE_ADD, null);
					tts.speak(textoDetallesNoticia, TextToSpeech.QUEUE_ADD,
							null);
				}
				// parar voz
				else {
					tts.stop();
					botonVoz.setText(R.string.boton_leer);
				}
			}
		});

		Button botonNavegador = (Button) findViewById(R.id.botonNavegador);
		botonNavegador.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						.parse(enlaceNoticia));
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_detalles_noticia, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case (R.id.action_ajustes):
			Intent mIntent = new Intent(this, AjustesActivity.class);
			startActivity(mIntent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
