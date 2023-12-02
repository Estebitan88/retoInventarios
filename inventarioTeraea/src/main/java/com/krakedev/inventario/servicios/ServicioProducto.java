package com.krakedev.inventario.servicios;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.krakedev.inventarios.entidades.Producto;
@Path("productos")
public class ServicioProducto {
	private ArrayList<Producto> productos;
	public ServicioProducto() {
		super();
		productos = new ArrayList<Producto>();
	}
	@Path("insertar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void insertar(Producto producto) {
		System.out.println("-----" + producto);
	}
	@Path("actualizar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void actualizar(Producto producto) {
		System.out.println("Actualizando cliente-----" + producto);
	}
	@Path("consultar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Producto> recuperarTodos() {
		productos.add(new Producto("12", "nutela", 1.1, 10));
		productos.add(new Producto("13", "natura", 3.1, 20));
		productos.add(new Producto("14", "fanta", 4.1, 30));
		return productos;
	}
}
