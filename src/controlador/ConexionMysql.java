package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMysql {
    private final String url;
    private final String user;
    private final String password;
    private Connection con;

    public ConexionMysql(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection obtenerConexion() {
        try {
        	con =DriverManager.getConnection(url, user, password);
			return con;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
    }

	public void cerrar() {
     try {
		con.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}		
	}
}
