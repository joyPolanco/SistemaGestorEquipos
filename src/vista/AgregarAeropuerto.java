package vista;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import modelo.Aeropuerto;
import modelo.AlmacenGeneral;


public class AgregarAeropuerto extends JPanel {

    private static final long serialVersionUID = 1L;
    private AlmacenGeneral almacenG;
    
    private JTextField txtNombre;
    private JTextField txtCodIATA;
    private JButton btnGuardar;
    private JLabel lblMensaje;
    private PanelAeropuertos panelAero;
    
    // Colores del tema futurista
    private final Color COLOR_FONDO = new Color(240, 245, 255);
    private final Color COLOR_PRIMARIO = new Color(0, 100, 255);
    private final Color COLOR_SECUNDARIO = new Color(200, 220, 255);
    private final Color COLOR_TEXTO = new Color(50, 50, 80);
    private final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    private final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FUENTE_BOTON = new Font("Segoe UI Semibold", Font.PLAIN, 14);

    public AgregarAeropuerto(AlmacenGeneral almacenG, PanelAeropuertos panelAero) {
        this.almacenG = almacenG;
        this.panelAero = panelAero;
        setBackground(COLOR_FONDO);
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Panel principal con sombra
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new GridBagLayout());
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        // Título con estilo moderno
        JLabel lblTitulo = new JLabel("AGREGAR AEROPUERTO");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(COLOR_PRIMARIO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(lblTitulo, gbc);
        
        // Campo Nombre con estilo mejorado
        JLabel lblNombre = new JLabel("Nombre del Aeropuerto:");
        lblNombre.setFont(FUENTE_NORMAL);
        lblNombre.setForeground(COLOR_TEXTO);
        
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        panelPrincipal.add(lblNombre, gbc);
        
        txtNombre = new JTextField(20);
        txtNombre.setFont(FUENTE_NORMAL);
        txtNombre.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_SECUNDARIO),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelPrincipal.add(txtNombre, gbc);
        
        // Campo Código IATA con estilo mejorado
        JLabel lblCodIATA = new JLabel("Código IATA:");
        lblCodIATA.setFont(FUENTE_NORMAL);
        lblCodIATA.setForeground(COLOR_TEXTO);
        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.LINE_END;
        panelPrincipal.add(lblCodIATA, gbc);
        
        txtCodIATA = new JTextField(5);
        txtCodIATA.setFont(FUENTE_NORMAL);
        txtCodIATA.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_SECUNDARIO),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panelPrincipal.add(txtCodIATA, gbc);
        
        // Botón Guardar con estilo moderno
        btnGuardar = new JButton("GUARDAR AEROPUERTO");
        btnGuardar.setFont(FUENTE_BOTON);
        btnGuardar.setBackground(COLOR_PRIMARIO);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover para el botón
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardar.setBackground(new Color(0, 80, 220));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardar.setBackground(COLOR_PRIMARIO);
            }
        });
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarAeropuerto();
            }
        });
        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(25, 0, 0, 0);
        panelPrincipal.add(btnGuardar, gbc);
        
        // Mensaje de estado con estilo mejorado
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(FUENTE_NORMAL);
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridy++;
        gbc.insets = new Insets(15, 0, 0, 0);
        panelPrincipal.add(lblMensaje, gbc);
        
        // Añadir panel principal al contenedor
        setLayout(new GridBagLayout());
        add(panelPrincipal);
    }

    private void guardarAeropuerto() {
        String nombre = txtNombre.getText().trim();
        String codIATA = txtCodIATA.getText().trim().toUpperCase();
        
        // Validación de campos vacíos
        if (nombre.isEmpty() || codIATA.isEmpty()) {
      	  DialogoConfirmacion diag = new DialogoConfirmacion(this, "Complete todos los campos", DialogoConfirmacion.TipoMensaje.ERROR);
            return;
        }
        
        // Validación de código IATA
        if (codIATA.length() != 3) {
        	  DialogoConfirmacion diag = new DialogoConfirmacion(this, "El codigo IATA debe tener una longutud de 3 caracteres", DialogoConfirmacion.TipoMensaje.ERROR);
              diag.mostrar();
            return;
        }
        

            // Generar ID único
            int nuevoId = almacenG.getLstAeropuertos().size() + 1;
            
            // Crear y guardar aeropuerto
            Aeropuerto nuevoAeropuerto = new Aeropuerto(nuevoId, nombre, codIATA);
            almacenG.getLstAeropuertos().add(nuevoAeropuerto);
            almacenG.getAeroDao().insertar(nuevoAeropuerto);
            
            // Actualizar panel de aeropuertos
            panelAero.actualizar();
            
            // Limpiar campos y mostrar éxito
            txtNombre.setText("");
            txtCodIATA.setText("");
            DialogoConfirmacion diag = new DialogoConfirmacion(this, "Se ha añadido el aeropuerto",DialogoConfirmacion.TipoMensaje.INFORMACION);
            diag.mostrar();
        
    }
    
   
}