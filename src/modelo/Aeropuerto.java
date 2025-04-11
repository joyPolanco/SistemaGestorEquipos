package modelo;

import java.util.ArrayList;
import java.util.List;

import daos.AeroEDao;


public class Aeropuerto {
	int id;
	String nombre;
	String codIATA;
	private AeroEDao daoEquiposAero;
	public AeroEDao getDaoEquiposAero() {
		return daoEquiposAero;
	}
	public void setDaoEquiposAero(AeroEDao daoEquiposAero) {
		this.daoEquiposAero = daoEquiposAero;
	}
	private List<EquipoAeropuerto> lstEquiposAero = new ArrayList<>();
	
	public Aeropuerto(int id, String nombre, String codIATA) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.codIATA = codIATA;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodIATA() {
		return codIATA;
	}
	public void setCodIATA(String codIATA) {
		this.codIATA = codIATA;
	}
	public List<EquipoAeropuerto> getEquipos() {
		return lstEquiposAero;
	}
	public void setLstEquiposAero(List<EquipoAeropuerto> lstEquiposAero) {
		this.lstEquiposAero = lstEquiposAero;
	}
	
	public int   getSizeListado() {
      return lstEquiposAero.size();
		}
	public boolean buscarEquipo (String serie) {
		boolean exists= false;
		for (Equipo equipo: this.getEquipos()) {
			if (equipo.getSerie().equals(serie)){
				exists= true;
				break;
			}
		}
		return exists;
	}

	
	public Equipo buscarEquipo (String serie, String modelo, String marca) {
		Equipo exists= null;
		for (Equipo equipo: this.getEquipos()) {
			if (equipo.getSerie().equals(serie)&& equipo.getModelo().equals(modelo)&& equipo.getMarca().equals(marca)){
				exists= equipo;
				break;
			}
		}
		return exists;
	}

}
