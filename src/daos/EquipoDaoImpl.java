package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.AlmacenGeneral;
import modelo.EquipoLocal;

public class EquipoDaoImpl implements IEquipoLocalDao {
    private final Connection connection;

    public EquipoDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<EquipoLocal> obtenerTodos() {
        List<EquipoLocal> lista = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM EQUIPOS_ALMACEN;");

            while (rs.next()) {
                 int id = rs.getInt(1);

                String serie = rs.getString(2);
                String modelo = rs.getString(3);
                String marca = rs.getString(4);
                String descripcion = rs.getString(5);
                String marbete = rs.getString(6);
                String accesorios = rs.getString(7);

                lista.add(new EquipoLocal(id, serie, modelo, marca, descripcion, marbete, accesorios));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void eliminarEquipo(String serie) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM EQUIPOS_ALMACEN WHERE SERIE = ?");
            ps.setString(1, serie);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editar(EquipoLocal equipo) {
    	String sql = "UPDATE EQUIPOS_ALMACEN SET SERIE=?, MODELO=?, MARCA=?, DESCRIPCION=?, MARBETE=?, ACCESORIOS=? WHERE ID=?" ;
        try {
        
    		PreparedStatement pstmt = connection.prepareStatement(sql);
    		pstmt.setString(1, equipo.getSerie());
    		pstmt.setString(2, equipo.getModelo());
    		pstmt.setString(3, equipo.getMarca());
    		pstmt.setString(4, equipo.getDescripcion());
    		pstmt.setString(5, equipo.getMarbete());
    		pstmt.setString(6, equipo.getAccesorios());
    		pstmt.setInt(7, equipo.getId());

    		
    		pstmt.executeUpdate();

        
    }
    catch(Exception e) {
    	e.printStackTrace();
    }
            }

    @Override
    public void insertar(EquipoLocal equipo) {
    	String sql = "INSERT INTO EQUIPOS_ALMACEN (SERIE, MODELO, MARCA, DESCRIPCION, MARBETE, ACCESORIOS) VALUES (?, ?, ?, ?, ?,?)";
        try {
        
    		PreparedStatement pstmt = connection.prepareStatement(sql);
    		pstmt.setString(1, equipo.getSerie());
    		pstmt.setString(2, equipo.getModelo());
    		pstmt.setString(3, equipo.getMarca());
    		pstmt.setString(4, equipo.getDescripcion());
    		pstmt.setString(5, equipo.getMarbete());
    		pstmt.setString(6, equipo.getAccesorios());

    		
    		pstmt.executeUpdate();

        
    }
    catch(Exception e) {
    	e.printStackTrace();
    }
        
    }
        

	@Override
	public void cargarEquiposLocales(AlmacenGeneral almacen) {
		// TODO Auto-generated method stub
		
	}
}

