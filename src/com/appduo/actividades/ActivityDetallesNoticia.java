package com.appduo.actividades;

import java.util.Date;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.appduo.R;

public class ActivityDetallesNoticia extends Activity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalles_noticia);
		
		//Recoger el texto de la noticiaa
		Bundle bundle = getIntent().getExtras();
		String textoDetallesNoticia = bundle.getString("detalles_noticia");
		String textoTituloNoticia = bundle.getString("titulo_noticia");
		Long fechaNoticia = bundle.getLong("fecha_noticia");
		Date fecha = new Date(fechaNoticia);
		String textoOrigenNoticia = bundle.getString("origen_noticia");
		
		//escribir
		TextView textViewDetallesNoticia = (TextView) findViewById(R.id.textViewDetalleNoticias);
		textViewDetallesNoticia.setText(textoDetallesNoticia);
		
		TextView textViewFecha = (TextView) findViewById(R.id.textViewFechaNoticia);

		textViewFecha.setText(fecha.getDate()+"-"+(fecha.getMonth()+1)+"-"+(fecha.getYear()+1900));
		
		TextView textViewTituloNoticia = (TextView) findViewById(R.id.textViewTituloNoticias);
		textViewTituloNoticia.setText(textoTituloNoticia);
		textViewTituloNoticia.setTextColor(Color.parseColor("#637c96"));
		textViewTituloNoticia.setTypeface(null, Typeface.BOLD);
		
		TextView textViewOrigenNoticia = (TextView) findViewById(R.id.textViewOrigenNoticia);
		textViewOrigenNoticia.setText(textoOrigenNoticia);
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
