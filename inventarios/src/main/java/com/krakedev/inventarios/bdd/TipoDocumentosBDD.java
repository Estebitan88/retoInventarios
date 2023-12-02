package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.TipoDocumento;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class TipoDocumentosBDD {
	public ArrayList<TipoDocumento> recuperarTodos() throws KrakeDevException {
		ArrayList<TipoDocumento> tipoDeDocumentos = new ArrayList<TipoDocumento>();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TipoDocumento tipDocumento = null;
		try {
			con = ConexionBDD.obtenerConexion();
			ps = con.prepareStatement("select codigo_tipo_documento, descripcion from tipo_de_documento");
			rs = ps.executeQuery();

			while (rs.next()) {
				String codigo_tipo_documento = rs.getString("codigo_tipo_documento");
				String descripcion = rs.getString("descripcion");
				
				tipDocumento = new TipoDocumento(codigo_tipo_documento,descripcion);
				tipoDeDocumentos.add(tipDocumento);
			}

		} catch (KrakeDevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("error al consultar. Detalle:" + e.getMessage());
		}

		return tipoDeDocumentos;
	}

}
