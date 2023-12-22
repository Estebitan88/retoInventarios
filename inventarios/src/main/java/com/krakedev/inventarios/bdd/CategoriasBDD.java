package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class CategoriasBDD {

	public void insertar(Categoria categoria) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("insert into categorias (nombre,categoria_padre) " + "values (?,?);");

			ps.setString(1, categoria.getNombre());
			ps.setInt(2, categoria.getCategoriaPadre().getCodigo());

			ps.executeUpdate();
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

	public void actualizar(Categoria categoria) throws KrakeDevException {
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = ConexionBDD.obtenerConexion();

			ps = con.prepareStatement("UPDATE categorias SET nombre =?, categoria_padre=? WHERE codigo_cat = ?;");

			ps.setString(1, categoria.getNombre());
			ps.setInt(2, categoria.getCategoriaPadre().getCodigo());
			ps.setInt(3, categoria.getCodigo());

			ps.executeUpdate();

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

	public ArrayList<Categoria> recuperar() throws KrakeDevException {
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Categoria cate = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select codigo_cat,nombre,categoria_padre from categorias");
			rs = ps.executeQuery();

			while (rs.next()) {
				int codigo_cat = rs.getInt("codigo_cat");
				String nombre = rs.getString("nombre");

				cate = new Categoria(codigo_cat,nombre);
			    categorias.add(cate);
			}

		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("error al consultar. Detalle:" + e.getMessage());
		}

		return categorias;
	}

}
