package com.krakedev.inventarios.entidades;

public class EstadoPedido {

	private String codigoEstado;
	private String descripcion;

	public EstadoPedido() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EstadoPedido(String codigoEstado) {
		super();
		this.codigoEstado = codigoEstado;
	}

	public String getCodigoEstado() {
		return codigoEstado;
	}

	public void setCodigoEstado(String codigoEstado) {
		this.codigoEstado = codigoEstado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "EstadoPedido [codigoEstado=" + codigoEstado + ", descripcion=" + descripcion + "]";
	}

}