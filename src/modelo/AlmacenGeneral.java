package modelo;

import java.util.ArrayList;
import java.util.List;

import daos.AeroDaoImp;
import daos.EquipoDaoImpl;
import daos.UsuarioDaoImp;



public  class AlmacenGeneral   {
private  List<EquipoLocal> lstEGeneral = new ArrayList<>();
private List<Aeropuerto> lstAeropuertos = new ArrayList<>();
private List<Usuario> lstUsuarios = new ArrayList<>();
private EquipoDaoImpl dao;
private AeroDaoImp aeroDao;
private UsuarioDaoImp usuarioDao;
private Usuario usuario;
public Usuario getUsuario() {
	return usuario;
}

public void setUsuario(Usuario usuario) {
	this.usuario = usuario;
}

public List<Usuario> getLstUsuarios() {
	return lstUsuarios;
}

public void setLstUsuarios(List<Usuario> lstUsuarios) {
	this.lstUsuarios = lstUsuarios;
}
public int   getSizeListado() {
    return lstEGeneral.size();
		}


public EquipoDaoImpl getDao() {
	return dao;
}

public void setDao(EquipoDaoImpl dao) {
	this.dao = dao;
}

public AeroDaoImp getAeroDao() {
	return aeroDao;
}

public void setAeroDao(AeroDaoImp aeroDao) {
	this.aeroDao = aeroDao;
}

public List<EquipoLocal> getLstEGeneral() {
	return lstEGeneral;

}

public List<Aeropuerto> getLstAeropuertos() {
	return lstAeropuertos;
}

public void setLstAeropuertos(List<Aeropuerto> lstAeropuertos) {
	this.lstAeropuertos = lstAeropuertos;
}

public void setLstEGeneral(List<EquipoLocal> lstEGeneral) {
	this.lstEGeneral = lstEGeneral;
}

public Aeropuerto buscarAeropuerto ( String nombre) {
	Aeropuerto aero=null;
	for (Aeropuerto aeropuerto: this.lstAeropuertos) {
		if (aeropuerto.getNombre().equals(nombre)){
			aero=aeropuerto;
			break;
		}
	}
	return aero;
}

public UsuarioDaoImp getUsuarioDao() {
	return usuarioDao;
}

public void setUsuarioDao(UsuarioDaoImp usuarioDao) {
	this.usuarioDao = usuarioDao;
}

public Usuario buscarUsuario (String  nombre) {
	Usuario user=null;
	for (Usuario usuario: this.getLstUsuarios()) {
		if (usuario.getCorreo().equals(nombre)){
			user=usuario;
			break;
		}
	}
	return user;
}


public Equipo buscarEquipo (String serie, String modelo, String marca) {
	Equipo eq=null;
	for (Equipo equipo: this.getLstEGeneral()) {
		if (equipo.getSerie().equals(serie)|| (equipo.getModelo().equals(modelo)&& equipo.getMarca().equals(marca)) ){
			eq=equipo; 
			break;
		}
	}
	return eq;
}
public boolean  buscarEquipo (String serie) {
	boolean eq=false;
	for (Equipo equipo: this.getLstEGeneral()) {
		if (equipo.getSerie().equals(serie)){
			eq=true; 
			break;
		}
	}
	return eq;
}

public Equipo buscarEquipo (String serie, String modelo, String marca, String marbete) { 
Equipo eq=null;
for (Equipo equipo: lstEGeneral) {
	if (equipo.getSerie().equals(serie)
		&& equipo.getModelo().equals(modelo)
		&& equipo.getMarca().equals(marca)
		&& equipo.getMarbete().equals(marbete)
			) {
		eq=equipo;
		break;
	}
}
return eq;
}
public boolean equipoExistente (String serie) {
	boolean eq=false;
	for (Equipo equipo: this.getLstEGeneral()) {
		if (equipo.getSerie().equals(serie)){
			eq=true; 
			break;
		}
	}
	return eq;
}

}