package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.ProductosBDD;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("productos")
public class ServiciosProductos {

	@Path("buscar/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(@PathParam("sub") String subcadena) {
		ProductosBDD prodBDD = new ProductosBDD();
		ArrayList<Producto> productos = null;
		try {
			productos = prodBDD.buscar(subcadena);
			return Response.ok(productos).build();
		} catch (KrakeDevException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

	@Path("crear")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear(Producto producto) {
		ProductosBDD prodBDD = new ProductosBDD();
		try {
			prodBDD.insertar(producto);
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
			// TODO: handle exception
		}
	}

	@Path("actualizar")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response actualizar(Producto producto) {
		ProductosBDD prodBDD = new ProductosBDD();
		try {
			prodBDD.actualizar(producto);
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
			// TODO: handle exception
		}
	}

	@Path("buscarPorIdentificador/{sub}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorIdentificador(@PathParam("sub") int subcadena) {
		ProductosBDD prodBDD = new ProductosBDD();
		ArrayList<Producto> productos = null;
		try {
			productos = prodBDD.buscarPorIdentificador(subcadena);
			return Response.ok(productos).build();
		} catch (KrakeDevException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

}
