package daos;

import java.sql.Connection;
import java.util.List;

import modelo.Aeropuerto;
import modelo.Usuario;

public interface IUsuariodao {
	   List<Usuario> obtenerTodos();
	    void insertar(Usuario usuario);
	    void Eliminar(String id);
	    void Actualizar(Usuario usuario);

}
