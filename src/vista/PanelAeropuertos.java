package vista;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import modelo.Aeropuerto;
import modelo.AlmacenGeneral;

public class PanelAeropuertos extends JPanel {


    private static final long serialVersionUID = 1L;
    private JPanel contenedorTarjetas;
    private final Color COLOR_FONDO = new Color(248, 249, 250);
    private final Color COLOR_PRIMARIO = new Color(13, 110, 253);
    private final Color COLOR_SECUNDARIO = new Color(108, 117, 125);
    private AlmacenGeneral almacenG ;
    private TabbedPanel tabbed;
    public PanelAeropuertos(AlmacenGeneral almacenG, TabbedPanel tabbed) {
    	this.almacenG=almacenG;
    	this.tabbed=tabbed;
        setLayout(new BorderLayout());
        setBackground(COLOR_FONDO);

        // Panel superior con título y botón
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(null);
        panelSuperior.setPreferredSize(new Dimension(10, 100));
        panelSuperior.setBackground(COLOR_FONDO);
        panelSuperior.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(222, 226, 230)));
        
        JLabel lblTitulo = new JLabel("Aeropuertos");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(new Color(33, 37, 41));
        lblTitulo.setBounds(30, 20, 200, 30);
        panelSuperior.add(lblTitulo);
        
        JButton btnAgregar = new JButton("Agregar aeropuerto");
        btnAgregar.setBounds(30, 60, 180, 32);
        btnAgregar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnAgregar.setBackground(COLOR_PRIMARIO);
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        panelSuperior.add(btnAgregar);
        btnAgregar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                tabbed.setSelectedIndex(10);				
			}
		});
        add(panelSuperior, BorderLayout.NORTH);

        // Panel contenedor con scroll
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        contenedorTarjetas = new JPanel();
        contenedorTarjetas.setLayout(new FlowLayout(FlowLayout.LEFT, 60, 60));
        contenedorTarjetas.setBackground(COLOR_FONDO);
        contenedorTarjetas.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 30));
        contenedorTarjetas.setPreferredSize(new Dimension(this.getWidth(),this.getHeight()));
        
        scrollPane.setViewportView(contenedorTarjetas);
        add(scrollPane, BorderLayout.CENTER);
        // Cargar datos de ejemplo
        cargarAeropuertos(almacenG.getLstAeropuertos());
    }

    private void cargarAeropuertos(List<Aeropuerto> nombresAeropuertos) {
        contenedorTarjetas.removeAll();
        
        for (Aeropuerto aeropuerto : nombresAeropuertos) {
            JPanel card = crearTarjetaAeropuerto(aeropuerto.getNombre());
            contenedorTarjetas.add(card);
        }
        
        contenedorTarjetas.revalidate();
        contenedorTarjetas.repaint();
    }

    private JPanel crearTarjetaAeropuerto(String nombre) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(230, 180));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(222, 226, 230)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Efecto hover
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(COLOR_PRIMARIO, 2),
                    BorderFactory.createEmptyBorder(18, 18, 18, 18)
                ));
                card.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(222, 226, 230)),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }
        });

        // Icono de avión
        JLabel icono = new JLabel(new ImageIcon("airport-icon.png")); // Reemplaza con tu ruta de imagen
        icono.setHorizontalAlignment(SwingConstants.CENTER);
        icono.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        card.add(icono, BorderLayout.NORTH);

        // Label con el nombre del aeropuerto
        JLabel lblNombre = new JLabel("<html><div style='text-align:center;'>" + nombre + "</div></html>");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombre.setForeground(new Color(33, 37, 41));
        lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(lblNombre, BorderLayout.CENTER);

        // Botón "Ir al almacén"
        JButton btnAlmacen = new JButton("Ir al almacén");
        btnAlmacen.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnAlmacen.setBackground(Color.WHITE);
        btnAlmacen.setForeground(COLOR_PRIMARIO);
        btnAlmacen.setFocusPainted(false);
        btnAlmacen.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_PRIMARIO),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        // Efecto hover para el botón
        btnAlmacen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAlmacen.setBackground(COLOR_PRIMARIO);
                btnAlmacen.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAlmacen.setBackground(Color.WHITE);
                btnAlmacen.setForeground(COLOR_PRIMARIO);
            }
        });
        
        btnAlmacen.addActionListener(e -> {
              Aeropuerto aero= almacenG.buscarAeropuerto(nombre);
         
              tabbed.getAlmacenAeropuerto().setAeropuerto(aero);
              tabbed.setSelectedIndex(3);
           // };
            
            		 
        });
        
        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnAlmacen);
        card.add(panelBoton, BorderLayout.SOUTH);

        return card;
    }
   
    
    public void actualizar() {
        // Limpiar el contenedor de tarjetas
        contenedorTarjetas.removeAll();
        
        // Volver a cargar todos los aeropuertos
        cargarAeropuertos(almacenG.getLstAeropuertos());
        
        // Revalidar y repintar el panel
        contenedorTarjetas.revalidate();
        contenedorTarjetas.repaint();
    }
}
