package vista;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import modelo.Aeropuerto;
import modelo.AlmacenGeneral;
import modelo.Equipo;

public class TabbedPanel extends JTabbedPane implements ITablaListener{
	
 private PanelHome home;
 private PanelAlmacenGeneral almacenGeneral;
 private PanelAeropuertos aeropuertos;
 private PanelAlmacen almacenAeropuerto;
 private PanelAuditoria auditoria;
private PanelAgregarEquipo agregarEquipo;
 private PanelPerfil perfil;
 private PanelAyuda ayuda;
private AlmacenGeneral almacenG;
private VistaEquipo VistaEquipo;
private EditarEquipo editarEq;
private ITablaListener listener;
    private Aeropuerto aeropuerto;

    
    
    
    
    public TabbedPanel() {
     crearPaneles();
     

    }


public void setListener(ITablaListener listener) {
	this.listener = listener;
}



public boolean isModoAeropuerto() {
    return aeropuerto != null;
}

public Aeropuerto getAeropuerto() {
    if (!isModoAeropuerto()) {
        throw new IllegalStateException("No hay aeropuerto configurado");
    }
    return aeropuerto;
}

public AlmacenGeneral getAlmacen() {
    return almacenG;
}

public TabbedPanel(AlmacenGeneral AlmacenG) {
	 this.almacenG=AlmacenG;
	 crearPaneles();
	 setBackground(Color.LIGHT_GRAY);
SwingStyle.ocultarBarraPestanas(this, 2, Color.white);
}



public PanelAlmacen getAlmacenAeropuerto() {
	return almacenAeropuerto;
}


public void setAlmacenAeropuerto(PanelAlmacen almacenAeropuerto) {
	this.almacenAeropuerto = almacenAeropuerto;
}


private void crearPaneles() {
home= new PanelHome(); 
almacenGeneral=new PanelAlmacenGeneral (almacenG, this);
aeropuertos=new PanelAeropuertos(almacenG, this);
almacenAeropuerto= new PanelAlmacen(almacenG, this);
auditoria=new  PanelAuditoria();
perfil=new PanelPerfil(VentanaPrincipal.usuario);
ayuda= new PanelAyuda ();
agregarEquipo= new PanelAgregarEquipo( almacenG, this);

VistaEquipo= new VistaEquipo(this, almacenG);
 editarEq= new EditarEquipo(this, almacenGeneral,almacenAeropuerto );
 AgregarAeropuerto agregarAero = new AgregarAeropuerto(almacenG, aeropuertos);
this.add(home);//0
this.add(almacenGeneral);//1
this.add(aeropuertos);//2
this.add(almacenAeropuerto);//3
this.add(auditoria);//4
this.add(perfil);//5
this.add(ayuda);//6
this.add(agregarEquipo );//8
this.add(VistaEquipo );//8
this.add(editarEq );//9
this.add(agregarAero);//10
//this.add(VistaEquipo,  );//8


}

public EditarEquipo getEditarEq() {
	return editarEq;
}



public VistaEquipo getVistaEquipo() {
	return VistaEquipo;
}



public void setVistaEquipo(VistaEquipo vistaEquipo) {
	VistaEquipo = vistaEquipo;
}



@Override
public void onDataChanges(boolean cambioRealizado) {
	
        if (almacenGeneral != null) {
            almacenGeneral.onDataChanges(cambioRealizado);
        }
        if (almacenAeropuerto != null) {
        	almacenAeropuerto.onDataChanges(cambioRealizado);
        }
    }


public PanelAgregarEquipo getAgregarEquipo() {
	return agregarEquipo;
}



public void setAgregarEquipo(PanelAgregarEquipo agregarEquipo) {
	this.agregarEquipo = agregarEquipo;
}

}




 
 
 
 


