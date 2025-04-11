package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Aeropuerto;

public class AeroDaoImp implements IAeropuertoDao{
	  private final Connection connection;

	    public AeroDaoImp(Connection connection) {
	        this.connection = connection;
	    }
	
	 

	
	@Override
	public List<Aeropuerto> obtenerTodos() {
		  List<Aeropuerto> lista = new ArrayList<>();
	        try {
	            Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT * FROM aeropuertos;");

	            while (rs.next()) {
	                int id = rs.getInt(1);
	                String nombre = rs.getString(2);
	                String codIata = rs.getString(3);
	                

	                lista.add(new Aeropuerto(id, nombre,codIata));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return lista;
	}




	@Override
	public void insertar(Aeropuerto aeropuerto) {
		String sql = "INSERT INTO AEROPUERTOS (NOMBRE, CODIATA) VALUES (?, ?)";
        try {
        
    		PreparedStatement pstmt = connection.prepareStatement(sql);
    		pstmt.setString(1, aeropuerto.getNombre());
    		pstmt.setString(2, aeropuerto.getCodIATA());
    
    		pstmt.executeUpdate();

        
    }
    catch(Exception e) {
    	e.printStackTrace();
    }
        
		
	}




	@Override
	public void Eliminar(String id) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void Actualizar(Aeropuerto aeropuerto) {
		// TODO Auto-generated method stub
		
	}


}
