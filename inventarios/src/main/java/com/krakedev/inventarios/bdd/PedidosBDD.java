package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.DetallePedido;
import com.krakedev.inventarios.entidades.EstadoPedido;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class PedidosBDD {

	public void insertar(Pedido pedido) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psDet = null;
		ResultSet rsClave;
		int codigoCabecera = 0;

		Date fechaActual = new Date();
		java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());

		try {
			con = ConexionBDD.obtenerConexion();

			ps = con.prepareStatement("insert into cabecera_pedido(proveedor,fecha,estado)\r\n" + "values (?,?,?);",
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, pedido.getProveedor().getIdentificador());
			ps.setDate(2, fechaSQL);
			ps.setString(3, "S");

			ps.executeUpdate();
			rsClave = ps.getGeneratedKeys();

			if (rsClave.next()) {
				codigoCabecera = rsClave.getInt(1);
			}

			ArrayList<DetallePedido> detallesPedidos = pedido.getDetalles();
			DetallePedido det;
			for (int i = 0; i < detallesPedidos.size(); i++) {
				det = detallesPedidos.get(i);
				psDet = con.prepareStatement(
						"insert into detalle_pedido(cabecera_pedido,producto,cantidad_solicitada,cantidad_recibida,subtotal)"
								+ "values(?,?,?,?,?);");
				psDet.setInt(1, codigoCabecera);
				psDet.setInt(2, det.getProducto().getCodigo());
				psDet.setInt(3, det.getCantidadSolicitada());
				psDet.setInt(4, 0);
				BigDecimal pv = det.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(det.getCantidadSolicitada());
				BigDecimal subtotal = pv.multiply(cantidad);
				psDet.setBigDecimal(5, subtotal);

				psDet.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertar productos. Detalle:" + e.getErrorCode());
		} catch (KrakeDevException e) {
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	public void editar(Pedido pedido) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement psDet = null;
		ResultSet rsClave;
		int codigoCabecera = 0;

		Date fechaActual = new Date();
		java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());

		try {
			con = ConexionBDD.obtenerConexion();

			ps = con.prepareStatement("update cabecera_pedido set estado ='R' where codigo=?");

			ps.setInt(1, pedido.getCodigo());

			ps.executeUpdate();
			rsClave = ps.getGeneratedKeys();

			if (rsClave.next()) {
				codigoCabecera = rsClave.getInt(1);
			}

			ArrayList<DetallePedido> detallesPedidos = pedido.getDetalles();
			DetallePedido det;
			for (int i = 0; i < detallesPedidos.size(); i++) {
				det = detallesPedidos.get(i);
				psDet = con.prepareStatement(
						"update detalle_pedido set cabecera_pedido=?, cantidad_recibida =?, subtotal=? where codigo=?");

				psDet.setInt(1, pedido.getCodigo());
				psDet.setInt(2, det.getCantidadRecibida());

				BigDecimal pv = det.getProducto().getPrecioVenta();
				BigDecimal cantidad = new BigDecimal(det.getCantidadRecibida());
				BigDecimal subtotal = pv.multiply(cantidad);
				psDet.setBigDecimal(3, subtotal);

				psDet.setInt(4, det.getCodigo());

				psDet.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertar productos. Detalle:" + e.getErrorCode());
		} catch (KrakeDevException e) {
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	public void actualizarRecibido(Pedido pedido) throws KrakeDevException {
		Connection con = null;
		PreparedStatement psHis = null;

		Date fechaActual = new Date();
		Timestamp fechaHoraActual = new Timestamp(fechaActual.getTime());
		Producto prod = new Producto();

		try {
			con = ConexionBDD.obtenerConexion();
			ArrayList<DetallePedido> detallesPedidos = pedido.getDetalles();
			DetallePedido det = new DetallePedido();
			for (int i = 0; i < detallesPedidos.size(); i++) {
				det = detallesPedidos.get(i);
				psHis = con.prepareStatement(
						"insert into historial_stock(fecha,referencia,producto,cantidad)" + "values(?,?,?,?);");

				psHis.setTimestamp(1, fechaHoraActual);
				psHis.setString(2, "Pedido " + pedido.getCodigo());
				psHis.setInt(3, det.getProducto().getCodigo());
				psHis.setInt(4, det.getCantidadRecibida());

				psHis.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertar productos. Detalle:" + e.getErrorCode());
		} catch (KrakeDevException e) {
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	public ArrayList<Pedido> buscarPedidosPorProveedor(String subcadena) throws KrakeDevException {
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Pedido pedido = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select cp.codigo, cp.proveedor, cp.fecha, cp.estado, "
					+ "dp.codigo, dp.producto, p.nombre, dp.cantidad_solicitada, "
					+ "cast(dp.subtotal as decimal(6,2)), dp.cantidad_recibida "
					+ "from cabecera_pedido cp, detalle_pedido dp, productos p "
					+ "where cp.codigo = dp.cabecera_pedido "
					+ "and dp.producto = p.codigo_prod "
					+ "and cp.proveedor like ?");

			System.out.println(subcadena);

			ps.setString(1, subcadena);
			rs = ps.executeQuery();

			while (rs.next()) {
				int codigoCP = rs.getInt("codigo");
				String identificador = rs.getString("proveedor");
				Date fecha = rs.getDate("fecha");
				String estadoPedido = rs.getString("estado");
				int codigoDP = rs.getInt("codigo");
				int codigoProducto = rs.getInt("producto");
				String nombreProducto = rs.getString("nombre");
				int cantidaSolicitada = rs.getInt("cantidad_solicitada");
				BigDecimal subtotal = rs.getBigDecimal("subtotal");
				int cantidadRecibida = rs.getInt("cantidad_recibida");

				Proveedor proveedor = new Proveedor();
				proveedor.setIdentificador(identificador);

				EstadoPedido estado = new EstadoPedido();
				estado.setCodigoEstado(estadoPedido);

				Producto p = new Producto();
				p.setCodigo(codigoProducto);
				p.setNombre(nombreProducto);

				ArrayList<DetallePedido> detPed = new ArrayList<DetallePedido>();
				DetallePedido dp = new DetallePedido();
				dp.setCodigo(codigoDP);
				dp.setProducto(p);
				dp.setCantidadSolicitada(cantidaSolicitada);
				dp.setSubtotal(subtotal);
				dp.setCantidadRecibida(cantidadRecibida);

				detPed.add(dp);

				pedido = new Pedido(codigoCP, proveedor, fecha, estado, detPed);
				pedidos.add(pedido);

			}

		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("error al consultar. Detalle:" + e.getMessage());
		}

		return pedidos;
	}

}