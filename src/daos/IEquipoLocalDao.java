package daos;

import java.util.List;

import modelo.Aeropuerto;
import modelo.AlmacenGeneral;
import modelo.Equipo;
import modelo.EquipoLocal;

public interface IEquipoLocalDao {//almacen local
    List<EquipoLocal> obtenerTodos();
	  void cargarEquiposLocales(AlmacenGeneral almacen);
	    void eliminarEquipo(String serie);
	    void editar(EquipoLocal equipo);
	    void insertar(EquipoLocal equipo);

}
