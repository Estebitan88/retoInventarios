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

import com.krakedev.inventarios.bdd.PedidosBDD;
import com.krakedev.inventarios.entidades.Pedido;

@Path("pedidos")
public class ServiciosPedidos {

	@Path("registrar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear(Pedido pedido) {
		PedidosBDD pedidoBDD = new PedidosBDD();
		try {
			pedidoBDD.insertar(pedido);
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
			// TODO: handle exception
		}
	}

	@Path("recibir")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response editar(Pedido pedido) {
		PedidosBDD pedidoBDD = new PedidosBDD();
		try {
			pedidoBDD.editar(pedido);
			pedidoBDD.actualizarRecibido(pedido);
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
			// TODO: handle exception
		}
	}

	@Path("buscarPorProveedor/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPorProveedor(@PathParam("id") String id) {
		PedidosBDD pedidosBDD = new PedidosBDD();
		ArrayList<Pedido> pedidos = null;
		try {
			pedidos = pedidosBDD.buscarPedidosPorProveedor(id);
			return Response.ok(pedidos).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
			// TODO: handle exception
		}
	}

}