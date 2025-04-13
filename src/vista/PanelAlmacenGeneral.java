package vista;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;

import modelo.AlmacenGeneral;
import modelo.Equipo;

public class PanelAlmacenGeneral extends JPanel implements ITablaListener {
    private static final long serialVersionUID = 1L;
    private final Color COLOR_FONDO = new Color(248, 249, 250);
    private final Color COLOR_PRIMARIO = new Color(13, 110, 253);
    private final Color COLOR_ENCABEZADO = new Color(70, 130, 180);
    private final Color COLOR_TEXTO_ENCABEZADO = Color.WHITE;
    private final Color COLOR_BORDE = new Color(230, 230, 230);
    
    private  TablaAlmacenG tabla;
    private final AlmacenGeneral almacenG;
    private JPanel panelTabla;
    
    // Componentes de estadísticas
    private JPanel estadisticaCont;
    private JLabel lblEquiposStock=null;
    private JLabel lblModelos=null;
    private JLabel lblMarcas=null;
   
	public void setEquipoVer(Equipo equipoVer) {
		getTabbed().getVistaEquipo().setEqElegido(equipoVer);
	}

	public TabbedPanel getTabbed() {
		return tabbed;
	}

	// Componentes de filtros
    private JPanel panelFiltros;
    private JComboBox<String> comboFiltros;
    private JTextField txtBusqueda;
    private JComboBox<String> cmbxModelo;
    private JComboBox<String> cmbxMarca;
	private TabbedPanel tabbed;
	private JButton btnBuscar;
 
	public PanelAlmacenGeneral(AlmacenGeneral almacenG, TabbedPanel tabbed) {
        this.almacenG = almacenG != null ? almacenG : new AlmacenGeneral();
        this.tabla = new TablaAlmacenG(this.almacenG, this);
     this.tabbed= tabbed;
        inicializarPanel();
        
    }

    private void inicializarPanel() {
        setLayout(new BorderLayout());
        setBackground(COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        crearPanelSuperior();
        configurarPanelTabla();
        tabla.refrescar();
        

    }

    private void crearPanelSuperior() {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setPreferredSize(new Dimension(200, 300));
        panelSuperior.setBorder(BorderFactory.createMatteBorder(20, 20, 5, 20, COLOR_BORDE));
        
        // Panel de título
        JPanel panelTitulo = crearPanelTitulo();
        
        // Panel de estadísticas
        estadisticaCont = new JPanel();
        configurarPnlEstadistica();
        
        // Panel inferior (filtros y botón)
        JPanel panelInferior = crearPanelInferior();
        
        panelSuperior.add(panelTitulo, BorderLayout.NORTH);
        panelSuperior.add(estadisticaCont, BorderLayout.CENTER);
        panelSuperior.add(panelInferior, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);
    }

    private JPanel crearPanelTitulo() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(new EmptyBorder(10, 40, 0, 10));
        SwingStyle.redondear(panel, 40, COLOR_FONDO);
        
        JLabel lblTitulo = new JLabel("Almacén General");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        panel.add(lblTitulo);
        return panel;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(COLOR_FONDO);
        
        // Panel de filtros
        panelFiltros = new JPanel();
        configurarPanelFiltros();
        
        // Botones (Actualizar y Agregar)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.add(crearBotonAgregar());
        
        // Panel contenedor
        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.add(panelFiltros, BorderLayout.CENTER);
        panelContenedor.add(panelBotones, BorderLayout.EAST);
        
        panel.add(panelContenedor, BorderLayout.CENTER);
        return panel;
    }

    private JButton crearBotonAgregar() {
        JButton btn = new JButton("Agregar equipo");
        btn.setPreferredSize(new Dimension(150, 30));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        SwingStyle.redondearBoton(btn, 10, COLOR_PRIMARIO);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(10, 88, 202)),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> {
        	tabbed.getAgregarEquipo().setModoAeropuerto(false);
        	tabbed.setSelectedIndex(7);
        });
        return btn;
    }

    public TablaAlmacenG getTabla() {
		return tabla;
	}

	private JButton crearBotonActualizar() {
        JButton btn = new JButton();
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(40, 30));
        ImageIcon icon = new ImageIcon(getClass().getResource("/RecursosGraficos/imagenes/devolver.png"));

        btn.setIcon(icon);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBackground(Color.WHITE);

        btn.addActionListener(e -> {
          onDataChanges(true);
        });
        return btn;
    }

    private void configurarPnlEstadistica() {
        estadisticaCont.setLayout(new GridLayout(1, 0, 70, 0));
        estadisticaCont.setBorder(new EmptyBorder(20, 40, 20, 40));
        
        lblEquiposStock = new JLabel("0");
        lblModelos = new JLabel("0");
        lblMarcas = new JLabel("0");

        estadisticaCont.add(crearTarjetaEstadistica("Equipos en stock", lblEquiposStock.getText(), new Color(87, 143, 202)));
        estadisticaCont.add(crearTarjetaEstadistica("Modelos", lblModelos.getText(), new Color(255, 189, 115)));
        estadisticaCont.add(crearTarjetaEstadistica("Marcas", lblMarcas.getText(), new Color(240, 84, 84)));
        actualizarEstadisticas();
    }

    private JPanel crearTarjetaEstadistica(String titulo, String valor, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(150, 90));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTitulo = new JLabel(titulo);
        JLabel lblValor = new JLabel(valor);
        
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblValor.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitulo.setForeground(Color.white);
        lblValor.setForeground(Color.white);

        panel.add(Box.createVerticalGlue());
        panel.add(lblTitulo);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(lblValor);
        panel.add(Box.createVerticalGlue());
        
        SwingStyle.redondear(panel, 15, color);
        
        return panel;
    }

    public void actualizarEstadisticas() {
      
    
        if (almacenG == null || almacenG.getLstEGeneral() == null || almacenG.getLstEGeneral().isEmpty()) {
            lblEquiposStock.setText("0");
            lblModelos.setText("0");
            lblMarcas.setText("0");
        }
        else {
        	
        int equipos =almacenG.getLstEGeneral().size();
       
        
        
        int modelos = (int) almacenG.getLstEGeneral().stream()
                                  .filter(e -> e != null && e.getModelo() != null)
                                  .map(Equipo::getModelo)
                                  .distinct()
                                  .count();
        int marcas = (int) almacenG.getLstEGeneral().stream()
                                 .filter(e -> e != null && e.getMarca() != null)
                                 .map(Equipo::getMarca)
                                 .distinct()
                                 .count();
     
        lblEquiposStock.setText(String.valueOf(equipos));
        lblModelos.setText(String.valueOf(modelos));
        lblMarcas.setText(String.valueOf(marcas));
        actualizarTarjetasEstadisticas();
        }
    }
    
    private void actualizarTarjetasEstadisticas() {
        estadisticaCont.removeAll();
        estadisticaCont.add(crearTarjetaEstadistica("Equipos en stock", lblEquiposStock.getText(), new Color(87, 143, 202)));
        estadisticaCont.add(crearTarjetaEstadistica("Modelos", lblModelos.getText(), new Color(255, 189, 115)));
        estadisticaCont.add(crearTarjetaEstadistica("Marcas", lblMarcas.getText(), new Color(240, 84, 84)));
        
        estadisticaCont.revalidate();
        estadisticaCont.repaint();
    } 

    private void configurarPanelTabla() {
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setUnitIncrement(40); // Velocidad del scroll
        scrollPane.getVerticalScrollBar().setBackground(Color.WHITE);
        scrollPane.getVerticalScrollBar().setForeground(Color.red);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension (30, this.getWidth()));
        SwingStyle.redondear(scrollPane.getVerticalScrollBar(), 10, Color.white);

        panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        
        add(panelTabla, BorderLayout.CENTER);
    }

    private void configurarPanelFiltros() {
        panelFiltros.setBackground(Color.WHITE);
        panelFiltros.setLayout(new BoxLayout(panelFiltros, BoxLayout.X_AXIS));
        panelFiltros.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
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
        panelFiltros.add(crearBotonActualizar());
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
        
        comboFiltros.addActionListener(this::manejarCambioFiltro);
    }

    private void manejarCambioFiltro(ActionEvent e) {
        String filtro = (String) comboFiltros.getSelectedItem();
        if (filtro == null) return;
           
        	btnBuscar.setVisible(!filtro.equals("ninguno"));
  if (filtro.equals("ninguno")) { getTabla().refrescar();
}
        txtBusqueda.setVisible(!filtro.equals("ninguno")&&!filtro.equals("Modelo") && !filtro.equals("Marca"));
        cmbxModelo.setVisible(filtro.equals("Modelo"));
        cmbxMarca.setVisible(filtro.equals("Marca"));
        
        if (filtro.equals("Modelo")) cargarModelos();
        else if (filtro.equals("Marca")) cargarMarcas();
    }

    private void cargarModelos() {
        cmbxModelo.removeAllItems();
        if (almacenG != null && almacenG.getLstEGeneral() != null) {
            almacenG.getLstEGeneral().stream()
                   .filter(e -> e != null && e.getModelo() != null)
                   .map(Equipo::getModelo)
                   .distinct()
                   .forEach(cmbxModelo::addItem);
        }
    }

    private void cargarMarcas() {
        cmbxMarca.removeAllItems();
        if (almacenG != null && almacenG.getLstEGeneral() != null) {
            almacenG.getLstEGeneral().stream()
                   .filter(e -> e != null && e.getMarca() != null)
                   .map(Equipo::getMarca)
                   .distinct()
                   .forEach(cmbxMarca::addItem);
        }
    }

    private void manejarBusqueda(ActionEvent e) {
        String tipoFiltro = (String) comboFiltros.getSelectedItem();
        if (tipoFiltro == null || almacenG == null || almacenG.getLstEGeneral() == null) {
            tabla.refrescar();
            actualizarEstadisticas();
            return;
        }
        
        String filtro = obtenerTextoFiltro(tipoFiltro);
        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
        model.setRowCount(0); // Limpiar la tabla
        
        if (filtro.isEmpty()) {
            tabla.refrescar();
            return;
        }
        
        for (Equipo equipo : almacenG.getLstEGeneral()) {
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
                    equipo.getMarbete(),
                    "" // Esto mantendrá los botones de acción
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

	@Override
	public void onDataChanges(boolean cambioRealizado) {
		almacenG.setLstEGeneral(almacenG.getDao().obtenerTodos());
        tabla.refrescar();
        actualizarEstadisticas();		
	}

	public AlmacenGeneral getAlmacenG() {
		return almacenG;
	}

	
}