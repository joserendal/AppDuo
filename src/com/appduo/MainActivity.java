package com.appduo;

import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.appduo.actividades.ActivityDetallesNoticia;
import com.appduo.actividades.AjustesActivity;
import com.appduo.modelo.Canal;
import com.appduo.modelo.Noticia;
import com.appduo.segundoplano.BorradoReceiver;
import com.appduo.segundoplano.DescargaReceiver;
import com.appduo.services.factoria.ServicesFactory;

public class MainActivity extends Activity implements ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		// Lanzar servicios en segundo plano
		lanzarServicios();
	}

	/**
	 * Este método lanzará todos los servicios en segundo plano, que trabajarán
	 * de forma autónoma en la aplicación. Estos servicios son: el de descarga
	 * de datos, y el de limpieza de la base de datos.
	 */
	private void lanzarServicios() {
		// Tiempo de ejecución de los servicios
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(this);
		int horas = pref.getInt("pref_key_intervalo_actualizacion", 3);
		long tiempo = (long) (horas * 3600 * 1000);

		// cancelar alarmas pendientes
		cancelarAlarmas();

		// Inicio del servicio de borrado y programación de la alarma
		/*
		 * Intent myIntentBorrado = new Intent(MainActivity.this,
		 * BorradoReceiver.class); PendingIntent pendingIntentBorrado =
		 * PendingIntent.getBroadcast(MainActivity.this, 0, myIntentBorrado,0);
		 * 
		 * AlarmManager alarmManagerBorrado =
		 * (AlarmManager)getSystemService(ALARM_SERVICE); //el servicio
		 * arrancará en media hora y se repite cada 12 horas
		 * alarmManagerBorrado.setRepeating(AlarmManager.RTC_WAKEUP,
		 * System.currentTimeMillis() + (30*60*1000), 12*3600*1000,
		 * pendingIntentBorrado);
		 */

		// Inicio del servicio de descarga y programación de la alarma
		Intent myIntent = new Intent(MainActivity.this, DescargaReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		// el servicio arrancará en una hora ya se repite segun lo pida el
		// usuario //(60*60*1000)
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,	System.currentTimeMillis() + (60 * 60 * 1000), tiempo,	pendingIntent);
	}

	private void cancelarAlarmas() {
		// cancelar alarma borrado
		AlarmManager alarmManager = (AlarmManager) this
				.getSystemService(Context.ALARM_SERVICE);
		Intent myIntentBorrado = new Intent(this, BorradoReceiver.class);
		PendingIntent pendingIntentBorrado = PendingIntent.getBroadcast(this,
				0, myIntentBorrado, 0);
		alarmManager.cancel(pendingIntentBorrado);

		// cancelar alarma de descarga
		Intent myIntentDescarga = new Intent(this, DescargaReceiver.class);
		PendingIntent pendingIntentDescarga = PendingIntent.getBroadcast(this,
				0, myIntentDescarga, 0);
		alarmManager.cancel(pendingIntentDescarga);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		case (R.id.action_voz):
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 6;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			case 4:
				return getString(R.string.title_section5).toUpperCase(l);
			case 5:
				return getString(R.string.title_section6).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			ListView dummyListView = (ListView) rootView
					.findViewById(R.id.listNoticias);

			// Coge el id del canal que se presentará
			int idCanal = getArguments().getInt(ARG_SECTION_NUMBER);
			Canal canal = new Canal();
			canal.setIdCanal(idCanal);
			// recoge las noticias
			List<Noticia> noticias = ServicesFactory.crearServicioNoticias()
					.generarListadoNoticiasCanal(getActivity(), canal);
			// Establece el array de objetos entrada como el adaptador de la
			// lista.
			ArrayAdapter<Noticia> list_adapter = new ArrayAdapter<Noticia>(
					getActivity(), android.R.layout.simple_list_item_1,
					noticias);
			dummyListView.setAdapter(list_adapter);

			// Añade el listener a la lista
			// Define la ventana 'detalle' que se mostrará al pulsar una noticia
			// brevemente
			dummyListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						final int pos, long arg3) {
					ListView lv = (ListView) getView().findViewById(
							R.id.listNoticias);
					ListAdapter la = lv.getAdapter();
					Noticia[] noticias = new Noticia[la.getCount()];
					for (int i = 0; i < la.getCount(); i++)
						noticias[i] = (Noticia) la.getItem(i);

					// Lanzar el intent y pasar parametros
					Intent i = new Intent(getActivity(),
							ActivityDetallesNoticia.class);
					i.putExtra("detalles_noticia",
							noticias[pos].getTextoNoticia());
					i.putExtra("titulo_noticia", noticias[pos].getTitulo());
					i.putExtra("enlace_noticia", noticias[pos].getEnlace());
					i.putExtra("fecha_noticia", noticias[pos].getFecha());
					i.putExtra("origen_noticia", noticias[pos].getOrigen());
					startActivity(i);

				}
			});

			return rootView;
		}
	}

}
