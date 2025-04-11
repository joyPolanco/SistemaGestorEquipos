package modelo;

public class Usuario {
private int id;
private String nombre;
private String rol;
private String contraseña;

public Usuario(int id, String correo, String rol, String contraseña) {
	super();
	this.id = id;
	this.nombre = correo;
	this.rol = rol;
	this.contraseña = contraseña;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCorreo() {
	return nombre;
}
public void setCorreo(String correo) {
	this.nombre = correo;
}
public String getRol() {
	return rol;
}
public void setRol(String rol) {
	this.rol = rol;
}
public String getContraseña() {
	return contraseña;
}
public void setContraseña(String contraseña) {
	this.contraseña = contraseña;
}

}
