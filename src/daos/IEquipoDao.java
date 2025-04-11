package daos;

import java.util.List;

import modelo.Aeropuerto;

import modelo.Equipo;
import modelo.EquipoAeropuerto;

public interface IEquipoDao {//Equipos de cada aeropuero
    List<EquipoAeropuerto> obtenerTodos(Aeropuerto aero);

	    void eliminarEquipo(Aeropuerto aero,String serie);
	    void editar(Aeropuerto aero,EquipoAeropuerto equipoo);
	    void insertar(Aeropuerto aero,EquipoAeropuerto equipo);

}
