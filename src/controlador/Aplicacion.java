package controlador;

import modelo.AlmacenGeneral;
import vista.FormularioLogin;

public class Aplicacion {
;

public static void main (String [] args) {
	
	AlmacenGeneral almacenG = new AlmacenGeneral();
	
	ControladorPrincipal controller = new ControladorPrincipal("jdbc:mysql://localhost:3306/tracker_almacen_db", "root", "Espialidoso1234*");
	controller.inicializarAlmacenes(almacenG);
	
  new FormularioLogin(almacenG);

}
}
