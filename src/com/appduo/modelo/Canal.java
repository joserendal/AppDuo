package com.appduo.modelo;

public class Canal {
	
	private int idCanal;
	private String nombreCanal;
	private String enlaceCanal;
	
	

	public Canal() {
	}

	public Canal(int idCanal, String nombreCanal, String enlaceCanal) {
		this.idCanal = idCanal;
		this.nombreCanal = nombreCanal;
		this.enlaceCanal = enlaceCanal;
	}

	public int getIdCanal() {
		return idCanal;
	}

	public void setIdCanal(int idCanal) {
		this.idCanal = idCanal;
	}

	public String getNombreCanal() {
		return nombreCanal;
	}

	public void setNombreCanal(String nombreCanal) {
		this.nombreCanal = nombreCanal;
	}

	public String getEnlaceCanal() {
		return enlaceCanal;
	}

	public void setEnlaceCanal(String enlaceCanal) {
		this.enlaceCanal = enlaceCanal;
	}

	@Override
	public String toString() {
		return "Canal " + idCanal + ", '" + nombreCanal + "', en "
				+ enlaceCanal + "]";
	}

}
