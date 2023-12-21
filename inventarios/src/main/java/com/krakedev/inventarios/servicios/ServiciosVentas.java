package com.krakedev.inventarios.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.VentasBDD;
import com.krakedev.inventarios.entidades.Venta;

@Path("ventas")
public class ServiciosVentas {
	
	
	@Path("guardar")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response crear(Venta venta) {
		VentasBDD ventasBDD = new VentasBDD();
		try {
			ventasBDD.insertar(venta);
			ventasBDD.actualizar(venta);
			return Response.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
			// TODO: handle exception
		}
	}

}
