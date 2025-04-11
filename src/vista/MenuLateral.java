package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class MenuLateral extends JPanel {
    private JPanel pnlBarra, contIcon, contBotonesMenu;
    private JLabel logo, icon, espacioLabel;
    private JButton[] botonesMenu;
    private boolean menuExpandido = true;
    private final Color FONDO_BARRA;
    private final int ANCHO_EXPANDIDO = 250;
    private final int ANCHO_CONTRAIDO = 130;
    private IMenuListener listener;
    private JLabel titulo;
    private String[]nombres = {"Inicio", "Almacen", "Aeropuertos", "Auditoria", "Perfil", "Ayuda"};
    private MouseEvent seleccionado=null;

    public MenuLateral(Color fondoBarra) {
        this.FONDO_BARRA = fondoBarra;
        configurarPanel();
        inicializarComponentes();
        actualizarTamanos();
    }

    private void configurarPanel() {
        setLayout(new BorderLayout());
        SwingStyle.redondear(this, 15, FONDO_BARRA);
    }

    private void inicializarComponentes() {
        pnlBarra = new JPanel(new BorderLayout());
        SwingStyle.redondear(pnlBarra, 30, FONDO_BARRA);

        // Configuración del área del logo/icono
        contIcon = new JPanel(new BorderLayout());
        contIcon.setBorder(new EmptyBorder(10, 10, 10, 10));
        contIcon.setBackground(FONDO_BARRA);

        icon = new JLabel(new ImageIcon(getClass().getResource("/recursosGraficos/imagenes/menu.png")));
        
        logo = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/recursosGraficos/imagenes/idacLogo.png"))
                .getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH)));

        // Label de espacio con altura fija de 40px
        espacioLabel = new JLabel(" ");
        espacioLabel.setPreferredSize(new Dimension(0, 40));
        espacioLabel.setOpaque(false);

        // Panel para logo y espacio
        JPanel panelLogoEspacio = new JPanel(new BorderLayout());
        panelLogoEspacio.add(logo, BorderLayout.NORTH);
        panelLogoEspacio.add(espacioLabel, BorderLayout.SOUTH);
        panelLogoEspacio.setOpaque(false);

        contIcon.add(icon, BorderLayout.EAST);
        contIcon.add(panelLogoEspacio, BorderLayout.SOUTH);

        // Listener para alternar menú
        icon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                alternarMenu();
            }
        });

        // Configuración de botones del menú
        contBotonesMenu = new JPanel();
        contBotonesMenu.setLayout(new BoxLayout(contBotonesMenu, BoxLayout.Y_AXIS));
        contBotonesMenu.setBackground(FONDO_BARRA);
        contBotonesMenu.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] iconos = {
                "/recursosGraficos/imagenes/inicio.png",
                "/recursosGraficos/imagenes/comercio.png",
                "/recursosGraficos/imagenes/aeropuerto.png",
                "/recursosGraficos/imagenes/auditoria.png",
                "/recursosGraficos/imagenes/busqueda.png",
                "/recursosGraficos/imagenes/ayuda.png"
        };

        botonesMenu = new JButton[nombres.length];
        for (int i = 0; i < nombres.length; i++) {
        	JButton btn = crearBotonMenu(nombres[i], iconos[i]);
        	btn.setName("btn"+i);
            botonesMenu[i] =btn;
            contBotonesMenu.add(botonesMenu[i]);
            contBotonesMenu.add(Box.createRigidArea(new Dimension(0, 5)));
            ((JLabel) botonesMenu[i].getComponent(0)).setFont(new Font("Segou UI", Font.BOLD, 16));

        }

        // Panel para título (opcional)
         titulo = new JLabel("Administrador de dispositivos");
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(new EmptyBorder(0, 0, 10, 0));

        // Panel contenedor del título y botones
        JPanel panelContenido = new JPanel(new BorderLayout());
        panelContenido.add(titulo, BorderLayout.NORTH);
        panelContenido.add(contBotonesMenu, BorderLayout.CENTER);
        panelContenido.setOpaque(false);

        pnlBarra.add(contIcon, BorderLayout.NORTH);
        pnlBarra.add(panelContenido, BorderLayout.CENTER);
        add(pnlBarra, BorderLayout.CENTER);
    }

    private JButton crearBotonMenu(String texto, String iconoPath) {
        JButton btn = new JButton();
        
        // Configuración básica
        btn.setLayout(new BorderLayout());
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setBackground(FONDO_BARRA);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        SwingStyle.redondearBoton(btn, 35, FONDO_BARRA);

        // Tamaño fijo para ambos estados
        btn.setPreferredSize(new Dimension(220, 60));
        btn.setMinimumSize(new Dimension(220, 60));
        btn.setMaximumSize(new Dimension(220, 60));
        
        // Icono y texto (¡AQUÍ ESTÁ EL CAMBIO PRINCIPAL!)
        JLabel contenido = new JLabel(texto, new ImageIcon(getClass().getResource(iconoPath)), SwingConstants.LEFT);
        contenido.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        contenido.setForeground(Color.WHITE); // ← TEXTO BLANCO
        contenido.setIconTextGap(10);
        btn.add(contenido, BorderLayout.CENTER);
        
        // Efecto hover (asegurar que el texto siga blanco)
        btn.addMouseListener(new MouseAdapter() {
            JLabel opcion=((JLabel)btn.getComponent(0));

            
            @Override
            public void mouseClicked(MouseEvent e) {
               seleccionado=e;
               if(listener != null) {
                   listener.onMenuButtonClicked(e);
               }
                
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(71, 149, 208));
                opcion.setFont(new Font("Segou UI", Font.BOLD, 18));
                opcion.setForeground(Color.WHITE); // 
                
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(FONDO_BARRA);
                opcion.setFont(new Font("Segou UI", Font.BOLD, 16));
                opcion.setForeground(Color.WHITE); //
            }
        });
        
        return btn;
    }

    public void alternarMenu() {
        menuExpandido = !menuExpandido;
        actualizarTamanos();
        
        if (listener != null) {
       
            listener.onMenuToggle(menuExpandido);
        }
    }
    void actualizarTamanos() {
        int ancho = menuExpandido ? ANCHO_EXPANDIDO : ANCHO_CONTRAIDO;
        setPreferredSize(new Dimension(ancho, getHeight()));
        
        for (int i = 0; i < botonesMenu.length; i++) {
            JButton btn = botonesMenu[i];
            JLabel contenido = (JLabel) btn.getComponent(0);
            
            contenido.setText(menuExpandido ? nombres[i] : "");
            contenido.setHorizontalAlignment(menuExpandido ? SwingConstants.LEFT : SwingConstants.CENTER);
            contenido.setForeground(Color.WHITE);
            logo.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/recursosGraficos/imagenes/idacLogo.png")) 
                    .getImage().getScaledInstance(100, 90, Image.SCALE_SMOOTH)));
            contIcon.remove(icon);
            contIcon.add(icon, BorderLayout.EAST);
            titulo.setText("Administrador de equipos");

            if (!menuExpandido) {
                ImageIcon icono = (ImageIcon) contenido.getIcon();
                logo.setIcon(null);
                titulo.setText("");
                contIcon.remove(icon);
                contIcon.add(icon, BorderLayout.NORTH);

               contenido.setIcon(new ImageIcon(icono.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
            }
        }
        
        revalidate();
        repaint();
    }
    public void setMenuListener(IMenuListener listener) {
        this.listener = listener;
    }

    public boolean isMenuExpandido() {
        return menuExpandido;
    }

    public int getANCHO_EXPANDIDO() {
        return ANCHO_EXPANDIDO;
    }

    public int getANCHO_CONTRAIDO() {
        return ANCHO_CONTRAIDO;
    }

	public MouseEvent getSeleccionado() {
		return seleccionado;
	}
    
}