package controlador;

import java.sql.Connection;

import daos.AeroDaoImp;
import daos.AeroEDao;
import daos.EquipoDaoImpl;
import daos.UsuarioDaoImp;
import modelo.Aeropuerto;
import modelo.AlmacenGeneral;

public class ControladorPrincipal {
    private ConexionMysql conexion;
    private EquipoDaoImpl daoEquipos;
    private AeroDaoImp daoAero;
    private AeroEDao daoEquiposAero;
    private UsuarioDaoImp usuarioDao;

    public AeroEDao getDaoEquiposAero() {
		return daoEquiposAero;
	}

	public void setDaoEquiposAero(AeroEDao daoEquiposAero) {
		this.daoEquiposAero = daoEquiposAero;
	}

	public EquipoDaoImpl getDaoEquipos() {
		return daoEquipos;
	}

	public void setDaoEquipos(EquipoDaoImpl daoEquipos) {
		this.daoEquipos = daoEquipos;
	}

	public AeroDaoImp getDaoAero() {
		return daoAero;
	}

	public void setDaoAero(AeroDaoImp daoAero) {
		this.daoAero = daoAero;
	}

	public ControladorPrincipal(String url, String user, String pass) {
        this.conexion = new ConexionMysql(url, user, pass);
        Connection con = conexion.obtenerConexion();

        this.daoEquipos = new EquipoDaoImpl(con);
        this.daoAero = new AeroDaoImp(con);
        this.daoEquiposAero= new AeroEDao(con);
        this.usuarioDao= new UsuarioDaoImp(con);
        
        
    }

    public void inicializarAlmacenes(AlmacenGeneral almacenG) {
    	almacenG.setDao(daoEquipos);
    	almacenG.setAeroDao(daoAero);

        almacenG.setLstEGeneral(daoEquipos.obtenerTodos());
        
        almacenG.setLstAeropuertos(daoAero.obtenerTodos());
        almacenG.setLstUsuarios(usuarioDao.obtenerTodos());
        for (Aeropuerto aero : almacenG.getLstAeropuertos()) 
        {
        	aero.setLstEquiposAero(daoEquiposAero.obtenerTodos(aero));
        	aero.setDaoEquiposAero(daoEquiposAero);
        }
    }

    public void cerrarConexion() {
        conexion.cerrar();
    }
}
