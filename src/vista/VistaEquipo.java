package vista;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import modelo.Aeropuerto;
import modelo.AlmacenGeneral;
import modelo.Equipo;
import modelo.EquipoLocal;
import modelo.EquipoAeropuerto;

public class VistaEquipo extends JPanel {

    private static final long serialVersionUID = 1L;
    private Equipo eqElegido;
    private TabbedPanel tabbedPanel;
    
    // Paleta de colores azules profesionales
    private final Color COLOR_PRIMARIO = new Color(0, 78, 152);      // Azul oscuro
    private final Color COLOR_SECUNDARIO = new Color(220, 235, 245); // Azul claro
    private final Color COLOR_ACENTO = new Color(0, 120, 215);       // Azul medio
    private final Color COLOR_TEXTO = new Color(50, 50, 50);         // Gris oscuro
    private final Color COLOR_TEXTO_SECUNDARIO = new Color(100, 100, 100);
    private final Color COLOR_BORDE = new Color(180, 200, 220);
    private final Color COLOR_FONDO = Color.WHITE;
    
    // Componentes UI
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblIcono;
    private JPanel panelDatos;
    private JButton btnEditar;
    private JButton btnAtras;
	private Aeropuerto aeropuertoActual;
    
    public VistaEquipo(TabbedPanel tabbedPanel,AlmacenGeneral almacenG) {
        this.tabbedPanel = tabbedPanel;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(COLOR_SECUNDARIO);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Panel de cabecera
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(COLOR_PRIMARIO);
        headerPanel.setBorder(new EmptyBorder(10, 15, 10, 15));
        
        lblTitulo = new JLabel("DETALLES DEL EQUIPO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        lblSubtitulo = new JLabel("Seleccione un equipo para ver sus detalles");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitulo.setForeground(new Color(200, 230, 255));
        lblSubtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSubtitulo.setBorder(new EmptyBorder(2, 0, 0, 0));
        
        headerPanel.add(lblTitulo);
        headerPanel.add(lblSubtitulo);
        
        // Panel de datos principal
        panelDatos = new JPanel();
        panelDatos.setLayout(new BoxLayout(panelDatos, BoxLayout.Y_AXIS));
        panelDatos.setBackground(COLOR_FONDO);
        panelDatos.setBorder(new CompoundBorder(
            new LineBorder(COLOR_BORDE, 1),
            new EmptyBorder(15, 20, 15, 20)
        ));
        
        JScrollPane scrollPane = new JScrollPane(panelDatos);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(COLOR_SECUNDARIO);
        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        
        btnAtras = crearBoton("Atrás", false);
        btnAtras.addActionListener(e -> 
        {
        if (eqElegido instanceof EquipoAeropuerto) {
        	tabbedPanel.setSelectedIndex(3);
        }
        else {
        	tabbedPanel.setSelectedIndex(1);
	
        }
        });
        
        btnEditar = crearBoton("Editar", true);
        btnEditar.addActionListener(e -> {
            if(eqElegido != null) {
                tabbedPanel.getEditarEq().setEquipo(eqElegido);
                tabbedPanel.setSelectedIndex(9);
            }
        });
        
        buttonPanel.add(btnAtras);
        buttonPanel.add(btnEditar);
        
        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        mostrarPanelVacio();
    }
    
    public void setEqElegido(Equipo eqElegido) {
        this.eqElegido = eqElegido;
        if(eqElegido != null) {
            mostrarInfo(eqElegido);
        } else {
            mostrarPanelVacio();
        }
    }
    
    private void mostrarPanelVacio() {
        panelDatos.removeAll();
        
        JLabel lblVacio = new JLabel("No se ha seleccionado ningún equipo");
        lblVacio.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblVacio.setForeground(COLOR_TEXTO_SECUNDARIO);
        lblVacio.setHorizontalAlignment(SwingConstants.CENTER);
        
        lblSubtitulo.setText("Seleccione un equipo para ver sus detalles");
        
        panelDatos.add(Box.createVerticalGlue());
        panelDatos.add(lblVacio);
        panelDatos.add(Box.createVerticalGlue());
        
        btnEditar.setEnabled(false);
        panelDatos.revalidate();
        panelDatos.repaint();
    }
    
    public void mostrarInfo(Equipo equipo) {
        if(equipo == null) {
            mostrarPanelVacio();
            return;
        }
        
        panelDatos.removeAll();
        this.eqElegido = equipo;
        
        try {
            if(equipo instanceof EquipoLocal) {
                mostrarEquipoLocal((EquipoLocal)equipo);
                lblSubtitulo.setText("EQUIPO LOCAL - " + equipo.getSerie());
            } 
            else if(equipo instanceof EquipoAeropuerto) {
                mostrarEquipoAeropuerto((EquipoAeropuerto)equipo);
                lblSubtitulo.setText("EQUIPO AEROPUERTO - " + equipo.getSerie());
            }
            
            btnEditar.setEnabled(true);
            panelDatos.revalidate();
            panelDatos.repaint();
        } catch(Exception e) {
            e.printStackTrace();
            mostrarErrorCarga();
        }
    }
    
    private void mostrarErrorCarga() {
        panelDatos.removeAll();
        
        JLabel lblError = new JLabel("Error al cargar la información del equipo");
        lblError.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblError.setForeground(Color.RED);
        lblError.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelDatos.add(Box.createVerticalGlue());
        panelDatos.add(lblError);
        panelDatos.add(Box.createVerticalGlue());
        
        btnEditar.setEnabled(false);
        panelDatos.revalidate();
        panelDatos.repaint();
    }
    
    private void mostrarEquipoLocal(EquipoLocal equipo) {
        // Sección de información básica
        panelDatos.add(crearSeccion("Información Básica"));
        panelDatos.add(Box.createRigidArea(new Dimension(0, 5)));
        
        panelDatos.add(crearFilaDato("Tipo", "Equipo Local", true));
        panelDatos.add(crearFilaDato("Número de Serie", equipo.getSerie(), false));
        panelDatos.add(crearFilaDato("Modelo", equipo.getModelo(), false));
        panelDatos.add(crearFilaDato("Marca", equipo.getMarca(), false));
        panelDatos.add(crearFilaDato("Marbete", equipo.getMarbete(), false));
        
        // Sección de descripción
        panelDatos.add(Box.createRigidArea(new Dimension(0, 15)));
        panelDatos.add(crearSeccion("Descripción"));
        panelDatos.add(Box.createRigidArea(new Dimension(0, 5)));
        
        panelDatos.add(crearFilaDatoMultilinea(equipo.getDescripcion()));
        
        // Sección de accesorios
        panelDatos.add(Box.createRigidArea(new Dimension(0, 15)));
        panelDatos.add(crearSeccion("Accesorios"));
        panelDatos.add(Box.createRigidArea(new Dimension(0, 5)));
        
        panelDatos.add(crearFilaDatoLista(equipo.getAccesorios()));
        
        // Sección de ubicación
        panelDatos.add(Box.createRigidArea(new Dimension(0, 15)));
        panelDatos.add(crearSeccion("Ubicación"));
        panelDatos.add(Box.createRigidArea(new Dimension(0, 5)));
        
        panelDatos.add(crearFilaDato("Localidad", "Almacén General", false));
    }
    
    private void mostrarEquipoAeropuerto(EquipoAeropuerto equipo) {
        // Sección de información básica
        panelDatos.add(crearSeccion("Información Básica"));
        panelDatos.add(Box.createRigidArea(new Dimension(0, 5)));
        
        panelDatos.add(crearFilaDato("Tipo", "Equipo Aeropuerto", true));
        panelDatos.add(crearFilaDato("Número de Serie", equipo.getSerie(), false));
        panelDatos.add(crearFilaDato("Modelo", equipo.getModelo(), false));
        panelDatos.add(crearFilaDato("Marca", equipo.getMarca(), false));
        panelDatos.add(crearFilaDato("Marbete", equipo.getMarbete(), false));
        panelDatos.add(crearFilaDato("Estado", equipo.getEstado(), false));
        
        // Sección de descripción
        panelDatos.add(Box.createRigidArea(new Dimension(0, 15)));
        panelDatos.add(crearSeccion("Descripción"));
        panelDatos.add(Box.createRigidArea(new Dimension(0, 5)));
        
        panelDatos.add(crearFilaDatoMultilinea(equipo.getDescripcion()));
        
        // Sección de accesorios
        panelDatos.add(Box.createRigidArea(new Dimension(0, 15)));
        panelDatos.add(crearSeccion("Accesorios"));
        panelDatos.add(Box.createRigidArea(new Dimension(0, 5)));
        
        panelDatos.add(crearFilaDatoLista(equipo.getAccesorios()));
        
        // Sección de ubicación
        panelDatos.add(Box.createRigidArea(new Dimension(0, 15)));
        panelDatos.add(crearSeccion("Ubicación"));
        panelDatos.add(Box.createRigidArea(new Dimension(0, 5)));
        
        panelDatos.add(crearFilaDato("Aeropuerto",aeropuertoActual!= null ? 
        	aeropuertoActual.getNombre() : "No asignado", false));

    }
    
  
	public void setAeropuertoActual(Aeropuerto aeropuerto) {
		this.aeropuertoActual = aeropuerto;
	}

	private JPanel crearSeccion(String titulo) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(COLOR_FONDO);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lbl = new JLabel(titulo);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(COLOR_PRIMARIO);
        lbl.setBorder(new EmptyBorder(0, 0, 0, 0));
        
        panel.add(lbl);
        return panel;
    }
    
    private JPanel crearFilaDato(String titulo, String valor, boolean destacado) {
        JPanel fila = new JPanel(new BorderLayout(10, 0));
        fila.setBackground(COLOR_FONDO);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblTitulo = new JLabel(titulo + ":");
        lblTitulo.setFont(new Font("Segoe UI", destacado ? Font.BOLD : Font.PLAIN, 13));
        lblTitulo.setForeground(COLOR_TEXTO);
        lblTitulo.setPreferredSize(new Dimension(150, 20));
        
        JLabel lblValor = new JLabel(valor != null ? valor : "N/A");
        lblValor.setFont(new Font("Segoe UI", destacado ? Font.BOLD : Font.PLAIN, 13));
        lblValor.setForeground(destacado ? COLOR_PRIMARIO : COLOR_TEXTO_SECUNDARIO);
        
        fila.add(lblTitulo, BorderLayout.WEST);
        fila.add(lblValor, BorderLayout.CENTER);
        
        return fila;
    }
    
    private JPanel crearFilaDatoMultilinea(String valor) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(COLOR_FONDO);
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextArea txtValor = new JTextArea(valor != null ? valor : "N/A");
        txtValor.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtValor.setForeground(COLOR_TEXTO_SECUNDARIO);
        txtValor.setLineWrap(true);
        txtValor.setWrapStyleWord(true);
        txtValor.setEditable(false);
        txtValor.setBackground(COLOR_FONDO);
        txtValor.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        JScrollPane scroll = new JScrollPane(txtValor);
        scroll.setBorder(new LineBorder(COLOR_BORDE, 1));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        fila.add(scroll, BorderLayout.CENTER);
        return fila;
    }
    
    private JPanel crearFilaDatoLista(String accesorios) {
        JPanel fila = new JPanel(new BorderLayout());
        fila.setBackground(COLOR_FONDO);
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel panelLista = new JPanel();
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setBackground(COLOR_FONDO);
        panelLista.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        if(accesorios != null && !accesorios.isEmpty()) {
            String[] items = accesorios.split("[,]\\s*");
            
            for(String item : items) {
                if(!item.trim().isEmpty()) {
                    JLabel lblItem = new JLabel("• " + item.trim());
                    lblItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                    lblItem.setForeground(COLOR_TEXTO_SECUNDARIO);
                    lblItem.setBorder(new EmptyBorder(2, 5, 2, 0));
                    panelLista.add(lblItem);
                }
            }
        } else {
            JLabel lblEmpty = new JLabel("N/A");
            lblEmpty.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lblEmpty.setForeground(COLOR_TEXTO_SECUNDARIO);
            panelLista.add(lblEmpty);
        }
        
        JScrollPane scroll = new JScrollPane(panelLista);
        scroll.setBorder(new LineBorder(COLOR_BORDE, 1));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        fila.add(scroll, BorderLayout.CENTER);
        return fila;
    }
    
    private JButton crearBoton(String texto, boolean primario) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        if(primario) {
            btn.setBackground(COLOR_ACENTO);
            btn.setForeground(Color.WHITE);
            btn.setBorder(new CompoundBorder(
                new LineBorder(COLOR_PRIMARIO, 1),
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
}