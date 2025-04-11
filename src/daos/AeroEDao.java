package daos;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Aeropuerto;
import modelo.AlmacenGeneral;
import modelo.Equipo;
import modelo.EquipoAeropuerto;

public class AeroEDao implements IEquipoDao {

	  private final Connection connection;

	    public AeroEDao(Connection connection) {
	        this.connection = connection;
	    }

	@Override
	public List<EquipoAeropuerto> obtenerTodos(Aeropuerto aero) {
		 List<EquipoAeropuerto> lista = new ArrayList<>();
	        try { 
	        	
	        	int id=aero.getId();
	        	String sql ="SELECT * FROM EQUIPOS_AEROPUERTO WHERE id_aero="+id;
	            Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery(sql);
	            
	            
	            while (rs.next()) {
	            	
	            	int idEq= rs.getInt(1);
	                String serie = rs.getString(2);
	                String modelo = rs.getString(3);
	                String marca = rs.getString(4);
	                String descripcion = rs.getString(5);
	                String marbete = rs.getString(6);
	                String accesorios = rs.getString(7);

	                String estado = rs.getString(8);
	                lista.add(new EquipoAeropuerto(idEq,serie, modelo, marca, descripcion, marbete, accesorios, estado));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return lista;
	}



	@Override
	public void eliminarEquipo(Aeropuerto aero, String serie) {
		  try {
	            PreparedStatement ps = connection.prepareStatement("DELETE FROM EQUIPOS_aeropuerto WHERE SERIE = ?");
	            ps.setString(1, serie);
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}



	@Override
	public void editar(Aeropuerto aero, EquipoAeropuerto equipo) {
	  	String sql = "UPDATE EQUIPOS_AEROPUERTO SET SERIE=?, MODELO=?, MARCA=?, DESCRIPCION=?, MARBETE=?, ACCESORIOS=?, ESTADO=? WHERE Id_eq=?" ;
        try {
     
    		PreparedStatement pstmt = connection.prepareStatement(sql);
    		pstmt.setString(1, equipo.getSerie());
    		pstmt.setString(2, equipo.getModelo());
    		pstmt.setString(3, equipo.getMarca());
    		pstmt.setString(4, equipo.getDescripcion());
    		pstmt.setString(5, equipo.getMarbete());
    		pstmt.setString(6, equipo.getAccesorios());
    		pstmt.setString(7,equipo.getEstado());
    		pstmt.setInt(8, equipo.getId());

    		
    		pstmt.executeUpdate();

		
	}
       catch (Exception e) {
		// TODO: handle exception
	}
	}


	@Override
	public void insertar(Aeropuerto aero, EquipoAeropuerto equipo) {
		String sql = "INSERT INTO EQUIPOS_AEROPUERTO (SERIE, MODELO, MARCA, DESCRIPCION, MARBETE, ACCESORIOS,ESTADO, ID_AERO) VALUES (?, ?, ?, ?,?, ?,?,?)";
		try {
          
    		PreparedStatement pstmt = connection.prepareStatement(sql);
    		pstmt.setString(1, equipo.getSerie());
    		pstmt.setString(2, equipo.getModelo());
    		pstmt.setString(3, equipo.getMarca());
    		pstmt.setString(4, equipo.getDescripcion());
    		pstmt.setString(5, equipo.getMarbete());
    		pstmt.setString(6, equipo.getAccesorios());
    		pstmt.setString(7, equipo.getEstado());
    		pstmt.setInt(8, aero.getId());

    		
    		pstmt.executeUpdate();
		
	}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
