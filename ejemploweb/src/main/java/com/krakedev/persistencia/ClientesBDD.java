package com.krakedev.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.entidades.Cliente;
import com.krakedev.excepciones.KrakeDevException;
import com.krakedev.utils.ConexionBDD;

public class ClientesBDD {

	public void insertar(Cliente cliente) throws KrakeDevException {
		Connection con = null;
		try {
			con = ConexionBDD.obtenerConexion();
			PreparedStatement ps = con
					.prepareStatement("insert into clientes(cedula,nombre,numeroHijos) values (?,?,?)");
			ps.setString(1, cliente.getCedula());
			ps.setString(2, cliente.getNombre());
			ps.setInt(3, cliente.getNumeroHijos());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertar un cliente");
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

	public void actualizar(Cliente cliente) throws KrakeDevException {
		Connection con = null;
		try {
			con = ConexionBDD.obtenerConexion();
			PreparedStatement ps = con.prepareStatement("UPDATE clientes SET nombre=?, numerohijos=? WHERE cedula=?");

			ps.setString(3, cliente.getCedula());
			ps.setString(1, cliente.getNombre());
			ps.setInt(2, cliente.getNumeroHijos());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertar un cliente. Detalle:" + e.getMessage());
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

	public ArrayList<Cliente> recuperarTodos() throws KrakeDevException {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Cliente cliente = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select cedula,nombre,numerohijos from clientes");
			rs = ps.executeQuery();

			while (rs.next()) {
				String cedula = rs.getString("cedula");
				String nombre = rs.getString("nombre");
				int numerohijos = rs.getInt("numerohijos");
				cliente = new Cliente(cedula, nombre, numerohijos);
				clientes.add(cliente);
			}

		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("error al consultar. Detalle:" + e.getMessage());
		}

		return clientes;
	}

	public Cliente buscarPorPK(String cedulaBusqueda) throws KrakeDevException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Cliente cliente = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select cedula,nombre,numerohijos from clientes where cedula=?");
			ps.setString(1, cedulaBusqueda);
			rs = ps.executeQuery();

			if (rs.next()) {
				String cedula = rs.getString("cedula");
				String nombre = rs.getString("nombre");
				int numerohijos = rs.getInt("numerohijos");
				cliente = new Cliente(cedula, nombre, numerohijos);
			}

		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("error al consultar. Detalle:" + e.getMessage());
		}

		return cliente;
	}
	
	public ArrayList<Cliente> buscarNumeroHijos(int numeroHijos) throws KrakeDevException {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Cliente cliente = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select cedula,nombre,numerohijos from clientes where numerohijos=?");
			ps.setInt(1, numeroHijos);
			rs = ps.executeQuery();

			while (rs.next()) {
				String cedula = rs.getString("cedula");
				String nombre = rs.getString("nombre");
				int numerohijos = rs.getInt("numerohijos");
				cliente = new Cliente(cedula, nombre, numerohijos);
				clientes.add(cliente);
			}

		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("error al consultar. Detalle:" + e.getMessage());
		}

		return clientes;
	}
}
