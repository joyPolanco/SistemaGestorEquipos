package modelo;


public class EquipoAeropuerto  extends Equipo{
private String estado;
	public EquipoAeropuerto(int id, String serie, String modelo, String marca, String descripcion, String marbete,
			String accesorios, String estado) {
	
		super(id, serie, modelo, marca, descripcion, marbete, accesorios);
		this.estado=estado;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}




}
