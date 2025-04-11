package vista;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import modelo.Aeropuerto;
import modelo.AlmacenGeneral;
import modelo.Equipo;
import modelo.EquipoAeropuerto;

public class PanelAlmacen extends JPanel implements ITablaListener {
    private static final long serialVersionUID = 1L;
    private final Color COLOR_FONDO = new Color(248, 249, 250);
    private final Color COLOR_PRIMARIO = new Color(13, 110, 253);
    private final Color COLOR_ENCABEZADO = new Color(70, 130, 180);
    private final Color COLOR_TEXTO_ENCABEZADO = Color.WHITE;
    private final Color COLOR_BORDE = new Color(230, 230, 230);
    private final Color COLOR_ADVERTENCIA = new Color(220, 53, 69);
    
    private TablaAlmacen tabla;
    private AlmacenGeneral almacenG;
    private Aeropuerto aeropuertoActual;
    private TabbedPanel tabbed;
    
    // Componentes de título y localidad
    private JLabel lblTitulo;
    private JLabel lblLocalidad;
    
    // Componentes de estadísticas
    private JPanel estadisticaCont;
    private JLabel lblEquiposTotal;
    private JLabel lblEquiposOperativos;
    private JLabel lblEquiposMantenimiento;
    
    // Componentes de filtros (como en AlmacenGeneral)
    private JPanel panelFiltros;
    private JComboBox<String> comboFiltros;
    private JTextField txtBusqueda;
    private JComboBox<String> cmbxModelo;
    private JComboBox<String> cmbxMarca;
    private JButton btnBuscar;
    private JButton btnActualizar;

    public void setAeropuertoActual(Aeropuerto aeropuertoActual) {
		this.aeropuertoActual = aeropuertoActual;
	}

	public PanelAlmacen(AlmacenGeneral almacenG, TabbedPanel tabbed) {
        this.almacenG = almacenG;
        this.tabbed = tabbed;
        this.tabla = new TablaAlmacen(aeropuertoActual, this);
        
        setLayout(new BorderLayout());
        setBackground(COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        inicializarComponentes();
        actualizarVistaCompleta();
    }
    
    public TabbedPanel getTabbed() {
		return tabbed;
	}

	private void inicializarComponentes() {
        crearPanelSuperior();
        configurarPanelTabla();
    }
    
    public void setAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuertoActual = aeropuerto;
        actualizarVistaCompleta();
    }
    
    private void actualizarVistaCompleta() {
        if (aeropuertoActual != null) {
            lblTitulo.setText("Almacén Aeropuerto");
            lblLocalidad.setText(aeropuertoActual.getNombre());
            tabla.setAeropuerto(aeropuertoActual);
            actualizarEstadisticas();
            cargarModelos();
            cargarMarcas();
        }
    }

    private void crearPanelSuperior() {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDE));
        
        // Panel de título y localidad
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTitulo.setBorder(new EmptyBorder(10, 40, 0, 10));
        
        lblTitulo = new JLabel("Almacén Aeropuerto");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        lblLocalidad = new JLabel();
        lblLocalidad.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblLocalidad.setForeground(COLOR_PRIMARIO);
        
        panelTitulo.add(lblTitulo);
        panelTitulo.add(Box.createHorizontalStrut(20));
        panelTitulo.add(lblLocalidad);
        
        // Panel de estadísticas
        estadisticaCont = new JPanel();
        configurarPanelEstadisticas();
        
        // Panel de filtros (como en AlmacenGeneral)
        panelFiltros = new JPanel();
        configurarPanelFiltros();
        
        panelSuperior.add(panelTitulo, BorderLayout.NORTH);
        panelSuperior.add(estadisticaCont, BorderLayout.CENTER);
        panelSuperior.add(panelFiltros, BorderLayout.SOUTH);
        
        add(panelSuperior, BorderLayout.NORTH);
    }
    
    private void configurarPanelEstadisticas() {
        estadisticaCont.setLayout(new GridLayout(1, 0, 70, 0));
        estadisticaCont.setBorder(new EmptyBorder(20, 40, 20, 40));
        
        lblEquiposTotal = new JLabel("0", SwingConstants.CENTER);
        lblEquiposOperativos = new JLabel("0", SwingConstants.CENTER);
        lblEquiposMantenimiento = new JLabel("0", SwingConstants.CENTER);
        
        estadisticaCont.add(crearTarjetaEstadistica("Total Equipos", lblEquiposTotal, new Color(87, 143, 202)));
        estadisticaCont.add(crearTarjetaEstadistica("Operativos", lblEquiposOperativos, new Color(40, 167, 69)));
        estadisticaCont.add(crearTarjetaEstadistica("En Mantenimiento", lblEquiposMantenimiento, new Color(255, 193, 7)));
    }
    
    private JPanel crearTarjetaEstadistica(String titulo, JLabel valor, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(150, 90));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(Color.white);
        
        valor.setAlignmentX(Component.CENTER_ALIGNMENT);
        valor.setFont(new Font("Segoe UI", Font.BOLD, 30));
        valor.setForeground(Color.white);

        panel.add(Box.createVerticalGlue());
        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(valor);
        panel.add(Box.createVerticalGlue());
        
        SwingStyle.redondear(panel, 15, color);
        
        return panel;
    }
    
    public void actualizarEstadisticas() {
        if (aeropuertoActual == null || aeropuertoActual.getEquipos() == null || aeropuertoActual.getEquipos().isEmpty()) {
            lblEquiposTotal.setText("0");
            lblEquiposOperativos.setText("0");
            lblEquiposMantenimiento.setText("0");
            return;
        }
        
        int total = aeropuertoActual.getEquipos().size();
        int operativos = (int) aeropuertoActual.getEquipos().stream()
                .filter(e -> e != null && "Operativo".equals(((EquipoAeropuerto)e).getEstado()))
                .count();
        int mantenimiento = total - operativos;
        
        lblEquiposTotal.setText(String.valueOf(total));
        lblEquiposOperativos.setText(String.valueOf(operativos));
        lblEquiposMantenimiento.setText(String.valueOf(mantenimiento));
        
        actualizarTarjetasEstadisticas();
    }
    
    private void actualizarTarjetasEstadisticas() {
        estadisticaCont.removeAll();
        estadisticaCont.add(crearTarjetaEstadistica("Total Equipos", lblEquiposTotal, new Color(87, 143, 202)));
        estadisticaCont.add(crearTarjetaEstadistica("Operativos", lblEquiposOperativos, new Color(40, 167, 69)));
        estadisticaCont.add(crearTarjetaEstadistica("En Mantenimiento", lblEquiposMantenimiento, new Color(255, 193, 7)));
        
        estadisticaCont.revalidate();
        estadisticaCont.repaint();
    }

    private void configurarPanelFiltros() {
        panelFiltros.setLayout(new BoxLayout(panelFiltros, BoxLayout.X_AXIS));
        panelFiltros.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Botón Actualizar
        btnActualizar = new JButton();
        btnActualizar.setIcon(new ImageIcon(getClass().getResource("/RecursosGraficos/imagenes/devolver.png")));
        btnActualizar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btnActualizar.addActionListener(e -> onDataChanges(true));
        panelFiltros.add(btnActualizar);
        
        // Filtros como en AlmacenGeneral
        JLabel lblFiltrarPor = new JLabel("Filtrar por:");
        lblFiltrarPor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFiltrarPor.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        
        comboFiltros = new JComboBox<>(new String[]{"ninguno", "Modelo", "Marca", "Serie"});
        comboFiltros.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboFiltros.setMaximumSize(new Dimension(150, comboFiltros.getPreferredSize().height));
        
        txtBusqueda = new JTextField();
        txtBusqueda.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBusqueda.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtBusqueda.getPreferredSize().height));
        txtBusqueda.setVisible(false);
        
        cmbxModelo = new JComboBox<>();
        cmbxModelo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbxModelo.setMaximumSize(new Dimension(150, cmbxModelo.getPreferredSize().height));
        cmbxModelo.setVisible(false);
        
        cmbxMarca = new JComboBox<>();
        cmbxMarca.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbxMarca.setMaximumSize(new Dimension(150, cmbxMarca.getPreferredSize().height));
        cmbxMarca.setVisible(false);
        
        btnBuscar = new JButton();
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBuscar.setBackground(Color.white);
        btnBuscar.setFocusable(false);
        btnBuscar.setIcon(new ImageIcon(getClass().getResource("/RecursosGraficos/imagenes/buscar.png")));
        btnBuscar.setOpaque(true);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        btnBuscar.setVisible(false);
        btnBuscar.addActionListener(this::manejarBusqueda);
        
        panelFiltros.add(lblFiltrarPor);
        panelFiltros.add(Box.createRigidArea(new Dimension(5, 0)));
        panelFiltros.add(comboFiltros);
        panelFiltros.add(Box.createRigidArea(new Dimension(10, 0)));
        panelFiltros.add(txtBusqueda);
        panelFiltros.add(Box.createRigidArea(new Dimension(10, 0)));
        panelFiltros.add(cmbxModelo);
        panelFiltros.add(Box.createRigidArea(new Dimension(10, 0)));
        panelFiltros.add(cmbxMarca);
        panelFiltros.add(Box.createHorizontalGlue());
        panelFiltros.add(btnBuscar);
        
        // Botón Agregar
        JButton btnAgregar = new JButton("Agregar equipo");
        btnAgregar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnAgregar.setForeground(Color.WHITE);
        SwingStyle.redondearBoton(btnAgregar, 10, COLOR_PRIMARIO);
        btnAgregar.addActionListener(e -> 
        
        {     	
        	
        	tabbed.getAgregarEquipo().setModoAeropuerto(true);
        	tabbed.getAgregarEquipo().setAeropuerto(aeropuertoActual);
            tabbed.setSelectedIndex(7);
        
        });
        panelFiltros.add(btnAgregar);
        
        comboFiltros.addActionListener(this::manejarCambioFiltro);
    }

    private void manejarCambioFiltro(ActionEvent e) {
        String filtro = (String) comboFiltros.getSelectedItem();
        if (filtro == null) return;
           
        btnBuscar.setVisible(!filtro.equals("ninguno"));
        txtBusqueda.setVisible(!filtro.equals("ninguno") && !filtro.equals("Modelo") && !filtro.equals("Marca"));
        cmbxModelo.setVisible(filtro.equals("Modelo"));
        cmbxMarca.setVisible(filtro.equals("Marca"));
        
        if (filtro.equals("Modelo")) cargarModelos();
        else if (filtro.equals("Marca")) cargarMarcas();
    }

    private void cargarModelos() {
        cmbxModelo.removeAllItems();
        if (aeropuertoActual != null && aeropuertoActual.getEquipos() != null) {
            aeropuertoActual.getEquipos().stream()
                   .filter(e -> e != null && e.getModelo() != null)
                   .map(Equipo::getModelo)
                   .distinct()
                   .forEach(cmbxModelo::addItem);
        }
    }

    private void cargarMarcas() {
        cmbxMarca.removeAllItems();
        if (aeropuertoActual != null && aeropuertoActual.getEquipos() != null) {
            aeropuertoActual.getEquipos().stream()
                   .filter(e -> e != null && e.getMarca() != null)
                   .map(Equipo::getMarca)
                   .distinct()
                   .forEach(cmbxMarca::addItem);
        }
    }

    private void manejarBusqueda(ActionEvent e) {
        String tipoFiltro = (String) comboFiltros.getSelectedItem();
        if (tipoFiltro == null || aeropuertoActual == null || aeropuertoActual.getEquipos() == null) {
            tabla.refrescar();
            actualizarEstadisticas();
            return;
        }
        
        String filtro = obtenerTextoFiltro(tipoFiltro);
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0);
        
        if (filtro.isEmpty()) {
            tabla.refrescar();
            return;
        }
        
        for (Equipo equipo : aeropuertoActual.getEquipos()) {
            if (equipo == null) continue;
            
            boolean coincide = false;
            switch (tipoFiltro) {
                case "Modelo":
                    coincide = equipo.getModelo() != null && equipo.getModelo().equalsIgnoreCase(filtro);
                    break;
                case "Marca":
                    coincide = equipo.getMarca() != null && equipo.getMarca().equalsIgnoreCase(filtro);
                    break;
                case "Serie":
                    coincide = equipo.getSerie() != null && equipo.getSerie().equalsIgnoreCase(filtro);
                    break;
                default:
                    // Búsqueda general
                    coincide = (equipo.getSerie() != null && equipo.getSerie().toLowerCase().contains(filtro.toLowerCase())) ||
                              (equipo.getModelo() != null && equipo.getModelo().toLowerCase().contains(filtro.toLowerCase())) ||
                              (equipo.getMarca() != null && equipo.getMarca().toLowerCase().contains(filtro.toLowerCase()));
            }
            
            if (coincide) {
                model.addRow(new Object[]{
                    equipo.getSerie(),
                    equipo.getModelo(),
                    equipo.getMarca(),
                    ((EquipoAeropuerto) equipo).getEstado(),
                    "" // Para botones de acción
                });
            }
        }
    }

    private String obtenerTextoFiltro(String tipoFiltro) {
        if (tipoFiltro.equals("Modelo")) {
            Object item = cmbxModelo.getSelectedItem();
            return item != null ? item.toString().trim() : "";
        } else if (tipoFiltro.equals("Marca")) {
            Object item = cmbxMarca.getSelectedItem();
            return item != null ? item.toString().trim() : "";
        }
        return txtBusqueda.getText().trim();
    }

    private void configurarPanelTabla() {
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        JPanel panelTablaContenedor = new JPanel(new BorderLayout());
        panelTablaContenedor.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        panelTablaContenedor.add(scrollPane, BorderLayout.CENTER);
        
        add(panelTablaContenedor, BorderLayout.CENTER);
    }

    @Override
    public void onDataChanges(boolean cambioRealizado) {
        if (aeropuertoActual != null) {
        	aeropuertoActual.setLstEquiposAero(aeropuertoActual.getDaoEquiposAero().obtenerTodos(aeropuertoActual));
            tabla.refrescar();
            actualizarVistaCompleta();
        }
    }
    
    public TablaAlmacen getTabla() {
        return tabla;
    }
    
    public Aeropuerto getAeropuertoActual() {
        return aeropuertoActual;
    }
}