package vista;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import modelo.Aeropuerto;
import modelo.Equipo;
import modelo.EquipoLocal;
import modelo.EquipoAeropuerto;

public class EditarEquipo extends JPanel {

    private static final long serialVersionUID = 1L;
    private Equipo equipo;
    private TabbedPanel tabbedPanel;
    
    // Componentes UI
    private JLabel lblTitulo;
    private JLabel lblIcono;
    private JPanel panelFormulario;
    private JButton btnCancelar;
    private JButton btnGuardar;
    private Aeropuerto aeropuerto;
    
    // Opciones para el combo de estado
    private final String[] ESTADOS_AEROPUERTO = {"INSTALADO", "MANTENIMIENTO", "SIN INSTALAR"};
    private JComboBox<String> comboEstado;
    
    public Aeropuerto getAeropuerto() {
        return aeropuerto;
    }

    public void setAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
    }

    // Campos de formulario
    private JTextField txtSerie;
    private JTextField txtModelo;
    private JTextField txtMarca;
    private JTextField txtMarbete;
    private JTextArea txtDescripcion;
    private JTextArea txtAccesorios;
    
    // Colores
    private final Color COLOR_PRIMARIO = new Color(0, 102, 204);
    private final Color COLOR_SECUNDARIO = new Color(240, 240, 240);
    private final Color COLOR_TEXTO = new Color(60, 60, 60);
    private final Color COLOR_BORDE = new Color(200, 200, 200);
    private PanelAlmacenGeneral almacenGeneral;
    private PanelAlmacen almacenAero;
    
    public EditarEquipo(TabbedPanel tabbedPanel, PanelAlmacenGeneral almacenGeneral, PanelAlmacen almacen) {
        this.almacenAero = almacen;
        this.tabbedPanel = tabbedPanel;
        this.almacenGeneral = almacenGeneral;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(COLOR_SECUNDARIO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_SECUNDARIO);
        
        // Panel de cabecera
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_PRIMARIO);
        headerPanel.setBorder(new EmptyBorder(8, 15, 8, 15));
        
        lblTitulo = new JLabel("EDITAR EQUIPO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        
        lblIcono = new JLabel();
        lblIcono.setHorizontalAlignment(SwingConstants.RIGHT);
        
        headerPanel.add(lblTitulo, BorderLayout.WEST);
        headerPanel.add(lblIcono, BorderLayout.EAST);
        
        // Panel de formulario
        panelFormulario = new JPanel();
        panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(new CompoundBorder(
            new LineBorder(COLOR_BORDE, 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        // Crear campos del formulario
        crearCamposFormulario();
        
        JScrollPane scrollPane = new JScrollPane(panelFormulario);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(COLOR_SECUNDARIO);
        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        btnCancelar = crearBoton("Cancelar", false);
        btnCancelar.addActionListener(e -> tabbedPanel.setSelectedIndex(1));
        
        btnGuardar = crearBoton("Guardar", true);
        btnGuardar.addActionListener(e -> {
        	guardarCambios();
        	
        });
        
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnGuardar);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
        
        mostrarFormularioVacio();
    }
    
    private void crearCamposFormulario() {
        // Panel para campos superiores (serie, modelo, marca)
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBackground(Color.WHITE);
        panelSuperior.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 15, 15);
        
        txtSerie = crearCampoTexto("Número de Serie");
        txtModelo = crearCampoTexto("Modelo");
        txtMarca = crearCampoTexto("Marca");
        txtMarbete = crearCampoTexto("Marbete");
        
        // ComboBox para estado (solo EquipoAeropuerto)
        comboEstado = new JComboBox<>(ESTADOS_AEROPUERTO);
        comboEstado.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboEstado.setBackground(Color.WHITE);
        comboEstado.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1),
            new EmptyBorder(5, 8, 5, 8)
        ));
        comboEstado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        comboEstado.setEnabled(false); // Inicialmente deshabilitado
        
        // Primera fila
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panelSuperior.add(crearCampoConTitulo("Número de Serie", txtSerie), gbc);
        
        gbc.gridx = 1;
        panelSuperior.add(crearCampoConTitulo("Modelo", txtModelo), gbc);
        
        gbc.gridx = 2;
        panelSuperior.add(crearCampoConTitulo("Marca", txtMarca), gbc);
        
        // Segunda fila
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Ocupa dos columnas
        panelSuperior.add(crearCampoConTitulo("Marbete", txtMarbete), gbc);
        gbc.gridwidth = 1; // Restablecer
        
        // Tercera fila (solo para estado de EquipoAeropuerto)
        gbc.gridx = 2;
        gbc.gridy = 1;
        panelSuperior.add(crearCampoConTitulo("Estado", comboEstado), gbc);
        
        panelFormulario.add(panelSuperior);
        
        // Campos de texto multilínea
        JPanel panelTextAreas = new JPanel(new GridBagLayout());
        panelTextAreas.setBackground(Color.WHITE);
        panelTextAreas.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        txtDescripcion = crearAreaTexto("Descripción");
        txtAccesorios = crearAreaTexto("Accesorios (separados por comas)");
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 15);
        panelTextAreas.add(crearAreaConTitulo("Descripción", txtDescripcion), gbc);
        
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        panelTextAreas.add(crearAreaConTitulo("Accesorios", txtAccesorios), gbc);
        
        panelFormulario.add(Box.createRigidArea(new Dimension(0, 15)));
        panelFormulario.add(panelTextAreas);
    }
    
    private JTextField crearCampoTexto(String placeholder) {
        JTextField txtField = new JTextField();
        txtField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1),
            new EmptyBorder(5, 8, 5, 8)
        ));
        txtField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return txtField;
    }
    
    private JTextArea crearAreaTexto(String placeholder) {
        JTextArea txtArea = new JTextArea();
        txtArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtArea.setLineWrap(true);
        txtArea.setWrapStyleWord(true);
        txtArea.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1),
            new EmptyBorder(5, 8, 5, 8)
        ));
        return txtArea;
    }
    
    private JPanel crearCampoConTitulo(String titulo, JComponent componente) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitulo.setForeground(COLOR_PRIMARIO);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 5, 0));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(lblTitulo);
        panel.add(componente);
        
        return panel;
    }
    
    private JPanel crearAreaConTitulo(String titulo, JTextArea textArea) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitulo.setForeground(COLOR_PRIMARIO);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 5, 0));
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        scrollPane.setPreferredSize(new Dimension(100, 100));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(lblTitulo);
        panel.add(scrollPane);
        
        return panel;
    }
    
    private JButton crearBoton(String texto, boolean primario) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if(primario) {
            btn.setBackground(COLOR_PRIMARIO);
            btn.setForeground(Color.WHITE);
            btn.setBorder(new CompoundBorder(
                new LineBorder(COLOR_PRIMARIO.darker(), 1),
                new EmptyBorder(5, 20, 5, 20)
            ));
        } else {
            btn.setBackground(Color.WHITE);
            btn.setForeground(COLOR_TEXTO);
            btn.setBorder(new CompoundBorder(
                new LineBorder(COLOR_BORDE, 1),
                new EmptyBorder(5, 20, 5, 20)
            ));
        }
        
        return btn;
    }
    
    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
        if(equipo != null) {
            cargarDatosEquipo();
        } else {
            mostrarFormularioVacio();
        }
    }
    
    private void mostrarFormularioVacio() {
        txtSerie.setText("");
        txtModelo.setText("");
        txtMarca.setText("");
        txtMarbete.setText("");
        txtDescripcion.setText("");
        txtAccesorios.setText("");
        comboEstado.setEnabled(false);
        lblTitulo.setText("EDITAR EQUIPO");
    }
    
    private void cargarDatosEquipo() {
        if(equipo == null) return;
        
        txtSerie.setText(equipo.getSerie() != null ? equipo.getSerie() : "");
        txtModelo.setText(equipo.getModelo() != null ? equipo.getModelo() : "");
        txtMarca.setText(equipo.getMarca() != null ? equipo.getMarca() : "");
        txtMarbete.setText(equipo.getMarbete() != null ? equipo.getMarbete() : "");
        txtDescripcion.setText(equipo.getDescripcion() != null ? equipo.getDescripcion() : "");
        txtAccesorios.setText(equipo.getAccesorios() != null ? equipo.getAccesorios() : "");
        
        if(equipo instanceof EquipoLocal) {
            lblTitulo.setText("EDITAR EQUIPO LOCAL - " + equipo.getSerie());
            comboEstado.setEnabled(false);
        } else if(equipo instanceof EquipoAeropuerto) {
            lblTitulo.setText("EDITAR EQUIPO AEROPUERTO - " + equipo.getSerie());
            comboEstado.setEnabled(true);
            
            // Cargar estado actual del equipo aeropuerto
            String estado = ((EquipoAeropuerto)equipo).getEstado();
            if(estado != null) {
                for(int i = 0; i < ESTADOS_AEROPUERTO.length; i++) {
                    if(ESTADOS_AEROPUERTO[i].equals(estado)) {
                        comboEstado.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
    }
    
    private void guardarCambios() {
        if(equipo == null) {
            JOptionPane.showMessageDialog(this, "No hay ningún equipo seleccionado para editar", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar campos obligatorios
        if(txtSerie.getText().trim().isEmpty() || 
           txtModelo.getText().trim().isEmpty() || 
           txtMarca.getText().trim().isEmpty()) {
           DialogoConfirmacion diag = new DialogoConfirmacion(this, "Los campos Número de Serie, Modelo y Marca son obligatorios", DialogoConfirmacion.TipoMensaje.ERROR);
           diag.mostrar();
           return;
        }
        
        // Actualizar datos del equipo
        equipo.setSerie(txtSerie.getText().trim());
        equipo.setModelo(txtModelo.getText().trim());
        equipo.setMarca(txtMarca.getText().trim());
        equipo.setMarbete(txtMarbete.getText().trim());
        equipo.setDescripcion(txtDescripcion.getText().trim());
        equipo.setAccesorios(txtAccesorios.getText().trim());
        
        if (equipo instanceof EquipoLocal) {
        	
            almacenGeneral.getAlmacenG().getDao().editar((EquipoLocal) equipo);
            almacenGeneral.onDataChanges(true);
            tabbedPanel.setSelectedIndex(1);
        } 
        else if (equipo instanceof EquipoAeropuerto) {
      
        	((EquipoAeropuerto) equipo).setEstado((String)comboEstado.getSelectedItem());
            if (aeropuerto != null) {
                aeropuerto.getDaoEquiposAero().editar(aeropuerto, ((EquipoAeropuerto) equipo));
                almacenAero.onDataChanges(true);
                tabbedPanel.setSelectedIndex(3);
            }
        }
        
     
    }
}