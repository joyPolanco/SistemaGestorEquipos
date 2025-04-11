package modelo;

import java.time.LocalDate;

public class Actividades {
private LocalDate fecha;
private String cambio;
private Usuario usuario;
private Equipo equipo;
public LocalDate getFecha() {
	return fecha;
}
public void setFecha(LocalDate fecha) {
	this.fecha = fecha;
}
public String getCambio() {
	return cambio;
}
public void setCambio(String cambio) {
	this.cambio = cambio;
}
public Usuario getUsuario() {
	return usuario;
}
public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
}
public Equipo getEquipo() {
	return equipo;
}
public void setEquipo(Equipo equipo) {
	this.equipo = equipo;
};

}
