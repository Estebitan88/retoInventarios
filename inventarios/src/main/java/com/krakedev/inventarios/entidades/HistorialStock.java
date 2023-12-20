package com.krakedev.inventarios.entidades;

import java.util.Date;

public class HistorialStock {

	private int codigo;
	private Date fecha;
	private String referencia;
	private int producto;
	private int cantidad;

	public HistorialStock() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HistorialStock(int codigo, Date fecha, String referencia, int producto, int cantidad) {
		super();
		this.codigo = codigo;
		this.fecha = fecha;
		this.referencia = referencia;
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public int getProducto() {
		return producto;
	}

	public void setProducto(int producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
