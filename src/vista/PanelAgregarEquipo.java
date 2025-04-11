package vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import modelo.Aeropuerto;
import modelo.AlmacenGeneral;
import modelo.EquipoAeropuerto;
import modelo.EquipoLocal;

public class PanelAgregarEquipo extends JPanel {
    private static final long serialVersionUID = 1L;
    
    // Colores mejorados con tonos azules
    private final Color COLOR_PRIMARIO = new Color(0, 82, 164); // Azul más oscuro
    private final Color COLOR_SECUNDARIO = new Color(245, 249, 255); // Azul muy claro
    private final Color COLOR_TEXTO = new Color(60, 60, 60);
    private final Color COLOR_BORDE = new Color(200, 215, 230); // Borde azul claro
    private final Color COLOR_FONDO_FORM = Color.WHITE;
    private final Color COLOR_ETIQUETA = new Color(0, 82, 164); // Azul oscuro
    
    // Componentes
    private JLabel lblTitulo, lblAeropuerto;
    private JTextField txtSerie, txtModelo, txtMarca, txtMarbete;
    private JTextArea txtDescripcion, txtAccesorios;
    private JComboBox<String> comboEstado;
    private JButton btnCancelar, btnRegistrar;
    
    // Modelos
    private AlmacenGeneral almacenG;
    private Aeropuerto aeropuerto;
    private boolean modoAeropuerto;
    private TabbedPanel listener;
    
    // Estados para equipos de aeropuerto
    private final String[] ESTADOS_AEROPUERTO = {"INSTALADO", "MANTENIMIENTO", "SIN INSTALAR"};
    private final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 13);
    private final Font FUENTE_ETIQUETA = new Font("Segoe UI", Font.BOLD, 13);

    public PanelAgregarEquipo(AlmacenGeneral almacenG, TabbedPanel tabbed) {
        this.almacenG = almacenG;
        this.listener = tabbed;
        this.modoAeropuerto = false;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(COLOR_SECUNDARIO);
        setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(COLOR_SECUNDARIO);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Cabecera
        JPanel headerPanel = crearHeaderPanel();
        
        // Formulario
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(COLOR_FONDO_FORM);
        formPanel.setBorder(new CompoundBorder(
            new LineBorder(COLOR_BORDE, 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        // Campos del formulario
        crearCamposFormulario(formPanel);
        
        // Panel de botones
        JPanel buttonPanel = crearButtonPanel();
        
        // Ensamblar componentes
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(formPanel), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel crearHeaderPanel() {
        lblTitulo = new JLabel("AGREGAR EQUIPO LOCAL");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(Color.WHITE);
        
        lblAeropuerto = new JLabel("");
        lblAeropuerto.setFont(FUENTE_NORMAL);
        lblAeropuerto.setForeground(new Color(220, 230, 240));
        lblAeropuerto.setVisible(false);
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_PRIMARIO);
        headerPanel.setBorder(new CompoundBorder(
            new MatteBorder(0, 0, 2, 0, COLOR_PRIMARIO.darker()),
            new EmptyBorder(12, 20, 12, 20)
        ));
        
        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 5));
        titlePanel.setOpaque(false);
        titlePanel.add(lblTitulo);
        titlePanel.add(lblAeropuerto);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        
        return headerPanel;
    }
    
    private JPanel crearButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(COLOR_SECUNDARIO);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        
        btnCancelar = crearBoton("Cancelar", false);
        btnCancelar.addActionListener(e -> {limpiarFormulario();
        if (modoAeropuerto) {
        	listener.setSelectedIndex(3);
        }
        else {
        	listener.setSelectedIndex(1);

        }


        });
        
        btnRegistrar = crearBoton("Registrar Equipo", true);
        btnRegistrar.addActionListener(e -> registrarEquipo());
        
        buttonPanel.add(btnCancelar);
        buttonPanel.add(btnRegistrar);
        
        return buttonPanel;
    }
    
    private void crearCamposFormulario(JPanel panel) {
        // Panel para campos superiores
        JPanel topPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        topPanel.setBackground(COLOR_FONDO_FORM);
        
        // Campos básicos
        txtSerie = crearCampoTexto("Número de serie *");
        txtModelo = crearCampoTexto("Modelo *");
        txtMarca = crearCampoTexto("Marca *");
        txtMarbete = crearCampoTexto("Marbete");
        
        topPanel.add(crearCampoConTitulo("Número de serie *", txtSerie));
        topPanel.add(crearCampoConTitulo("Modelo *", txtModelo));
        topPanel.add(crearCampoConTitulo("Marca *", txtMarca));
        topPanel.add(crearCampoConTitulo("Marbete", txtMarbete));
        
        // Combo de estado (solo visible en modo aeropuerto)
        comboEstado = new JComboBox<>(ESTADOS_AEROPUERTO);
        comboEstado.setFont(FUENTE_NORMAL);
        comboEstado.setVisible(false);
        comboEstado.setSelectedIndex(2);
        topPanel.add(crearCampoConTitulo("Estado", comboEstado));
        
        panel.add(topPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Áreas de texto
        JPanel middlePanel = new JPanel(new GridLayout(1, 2, 15, 0));
        middlePanel.setBackground(COLOR_FONDO_FORM);
        
        txtDescripcion = crearAreaTexto("Descripción del equipo");
        txtAccesorios = crearAreaTexto("Accesorios (separados por comas)");
        
        middlePanel.add(crearAreaConTitulo("Descripción", txtDescripcion));
        middlePanel.add(crearAreaConTitulo("Accesorios", txtAccesorios));
        
        panel.add(middlePanel);
    }
    
    private JTextField crearCampoTexto(String titulo) {
        JTextField campo = new JTextField();
        campo.setFont(FUENTE_NORMAL);
        campo.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        return campo;
    }
    
    private JTextArea crearAreaTexto(String titulo) {
        JTextArea area = new JTextArea(4, 20);
        area.setFont(FUENTE_NORMAL);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        return area;
    }
    
    private JPanel crearCampoConTitulo(String titulo, JComponent componente) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(COLOR_FONDO_FORM);
        
        JLabel label = new JLabel(titulo);
        label.setFont(FUENTE_ETIQUETA);
        label.setForeground(COLOR_ETIQUETA);
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(componente, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearAreaConTitulo(String titulo, JTextArea area) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(COLOR_FONDO_FORM);
        
        JLabel label = new JLabel(titulo);
        label.setFont(FUENTE_ETIQUETA);
        label.setForeground(COLOR_ETIQUETA);
        
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(null);
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JButton crearBoton(String texto, boolean primario) {
        JButton btn = new JButton(texto);
        btn.setFont(FUENTE_NORMAL);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        if (primario) {
            btn.setBackground(COLOR_PRIMARIO);
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_PRIMARIO.darker(), 1, true),
                new EmptyBorder(8, 25, 8, 25)
            ));
        } else {
            btn.setBackground(Color.WHITE);
            btn.setForeground(COLOR_TEXTO);
            btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(COLOR_BORDE, 1, true),
                new EmptyBorder(8, 25, 8, 25)
            ));
        }
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (primario) {
                    btn.setBackground(COLOR_PRIMARIO.brighter());
                } else {
                    btn.setBackground(new Color(240, 245, 250));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (primario) {
                    btn.setBackground(COLOR_PRIMARIO);
                } else {
                    btn.setBackground(Color.WHITE);
                }
            }
        });
        
        return btn;
    }
    
    private void registrarEquipo() {
        // Validar campos obligatorios
        if (txtSerie.getText().trim().isEmpty() || 
            txtMarca.getText().trim().isEmpty() || 
            txtModelo.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Los campos marcados con (*) son obligatorios", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (modoAeropuerto) {
            if (aeropuerto == null) {
                JOptionPane.showMessageDialog(this, 
                    "No se ha seleccionado un aeropuerto válido", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            registrarEquipoAeropuerto();
        } else {
            registrarEquipoLocal();
        }
    }
    
    private void registrarEquipoAeropuerto() {
        String serie = txtSerie.getText().trim();
        String marca = txtMarca.getText().trim();
        String modelo = txtModelo.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String marbete = txtMarbete.getText().trim();
        String accesorios = txtAccesorios.getText().trim();
        String estado = (String) comboEstado.getSelectedItem();
        
        EquipoAeropuerto equipo = new EquipoAeropuerto(
            0, serie, marca, modelo, descripcion, marbete, accesorios, estado);
        
        try {
            aeropuerto.getDaoEquiposAero().insertar(aeropuerto, equipo);
            
            JOptionPane.showMessageDialog(this, 
                "Equipo registrado correctamente en el aeropuerto " + aeropuerto.getNombre(), 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            listener.setSelectedIndex(3);
            if (listener != null) {
                listener.onDataChanges(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al registrar el equipo: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void registrarEquipoLocal() {
        String serie = txtSerie.getText().trim();
        String marca = txtMarca.getText().trim();
        String modelo = txtModelo.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String marbete = txtMarbete.getText().trim();
        String accesorios = txtAccesorios.getText().trim();
        
        if (almacenG.equipoExistente(serie)) {
            JOptionPane.showMessageDialog(this, 
                "Ya existe un equipo con ese número de serie", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        EquipoLocal equipo = new EquipoLocal(
            0, serie, marca, modelo, descripcion, marbete, accesorios);
        
        try {
            almacenG.getDao().insertar(equipo);
            JOptionPane.showMessageDialog(this, 
                "Equipo registrado correctamente en el almacén general", 
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
        	listener.setSelectedIndex(1);

            if (listener != null) {
                listener.onDataChanges(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al registrar el equipo: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        txtSerie.setText("");
        txtModelo.setText("");
        txtMarca.setText("");
        txtMarbete.setText("");
        txtDescripcion.setText("");
        txtAccesorios.setText("");
    }
    
    public void setModoAeropuerto(boolean modoAeropuerto, Aeropuerto aeropuerto) {
        this.modoAeropuerto = modoAeropuerto;
        this.aeropuerto = aeropuerto;
        actualizarInterfaz();
    }
    
    private void actualizarInterfaz() {
        String titulo = "AGREGAR EQUIPO " + (modoAeropuerto ? "AEROPUERTO" : "LOCAL");
        lblTitulo.setText(titulo);
        
        if (modoAeropuerto && aeropuerto != null) {
            lblAeropuerto.setText("Aeropuerto: " + aeropuerto.getNombre());
            lblAeropuerto.setVisible(true);
        } else {
            lblAeropuerto.setVisible(false);
        }
        
        comboEstado.setVisible(modoAeropuerto);
        
        revalidate();
        repaint();
    }

    public void setModoAeropuerto(boolean b) {
        this.modoAeropuerto = b;
        actualizarInterfaz();
    }

    public void setAeropuerto(Aeropuerto aeropuertoActual) {
        this.aeropuerto = aeropuertoActual;
        actualizarInterfaz();
    }
}