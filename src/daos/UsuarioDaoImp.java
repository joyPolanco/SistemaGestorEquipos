package daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Usuario;

public class UsuarioDaoImp implements IUsuariodao {
	 private final Connection connection;

	    public UsuarioDaoImp(Connection connection) {
	        this.connection = connection;
	        
	    }

		@Override
		public List<Usuario> obtenerTodos() {
			List<Usuario> lista = new ArrayList<>();
	        try {
	            Statement stmt = connection.createStatement();
	            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");

	            while (rs.next()) {
	                
	                int id = rs.getInt(1);
	                String nombre = rs.getString(2);
	                String rol = rs.getString(3);
	                String contraseña = rs.getString(4);

	                lista.add(new Usuario( id,  nombre,  rol,  contraseña));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return lista;
		}

		@Override
		public void insertar(Usuario usuario) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void Eliminar(String id) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void Actualizar(Usuario usuario) {
			// TODO Auto-generated method stub
			
		}
}
