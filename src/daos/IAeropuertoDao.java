package daos;

import java.util.List;

import modelo.Aeropuerto;

public interface IAeropuertoDao {
    List<Aeropuerto> obtenerTodos();
    void insertar(Aeropuerto aeropuerto);
    void Eliminar(String id);
    void Actualizar(Aeropuerto aeropuerto);

}
