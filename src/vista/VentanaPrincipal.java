package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

import javax.swing.border.EmptyBorder;

import modelo.AlmacenGeneral;
import modelo.Usuario;

public class VentanaPrincipal extends JFrame {
    private MenuLateral panelMenu;
    private PanelContenido panelContenido;
    private JPanel panelEspacio;
    private JPanel panelIzquierdo;
    private AlmacenGeneral almacenG;
    static Usuario usuario;
    public static class Config {
        public static final Color COLOR_FONDO = new Color(240, 245, 249, 1);
        public static final Color COLOR_BARRA = new Color(0, 64, 128);
        public static final int MARGEN = 10;
        public static final int ESPACIO = 10;
        public static final Dimension TAMANO_MINIMO = new Dimension(1050, 768);
    }

    public VentanaPrincipal( AlmacenGeneral almacenG, Usuario usuario) {
    	this.almacenG=almacenG;
    	this.usuario=usuario;
        configurarVentana();
        inicializarComponentes();
        configurarEventosRedimensionamiento();
        setVisible(true);
    }

    private void configurarVentana() {
        setTitle("Administrador de Dispositivos - IDAC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(Config.TAMANO_MINIMO);
        getContentPane().setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        // Panel contenedor principal
        JPanel contenedorPrincipal = new JPanel(new BorderLayout());
        contenedorPrincipal.setBorder(new EmptyBorder(
            Config.MARGEN, Config.MARGEN, 
            Config.MARGEN, Config.MARGEN
        ));
        contenedorPrincipal.setBackground(Config.COLOR_FONDO);

        // Panel izquierdo (contiene menú + espacio)
        panelIzquierdo = new JPanel(new BorderLayout());
        panelIzquierdo.setOpaque(false);

        // Crear componentes
        panelMenu = new MenuLateral(Config.COLOR_BARRA);
        panelContenido = new PanelContenido(new TabbedPanel(almacenG));
        panelEspacio = new JPanel();
        panelEspacio.setOpaque(false);
        panelEspacio.setPreferredSize(new Dimension(Config.ESPACIO, 0));

        // Configurar listeners
        panelMenu.setMenuListener(panelContenido); // PanelContenido escucha eventos del menú
        panelMenu.setMenuListener(new IMenuListener() {
        	
            @Override
            public void onMenuButtonClicked(MouseEvent e) {
           panelContenido.onMenuButtonClicked(e);
            }

            @Override
            public void onMenuToggle(boolean isExpanded) {
                panelContenido.onMenuToggle(panelMenu.isMenuExpandido());
                actualizarTamanos();
            }
        });

        // Organizar componentes
        panelIzquierdo.add(panelMenu, BorderLayout.WEST);
        panelIzquierdo.add(panelEspacio, BorderLayout.CENTER);
        
        contenedorPrincipal.add(panelIzquierdo, BorderLayout.WEST);
        contenedorPrincipal.add(panelContenido, BorderLayout.CENTER);
        
        getContentPane().add(contenedorPrincipal, BorderLayout.CENTER);
        actualizarTamanos();
    }

    private void configurarEventosRedimensionamiento() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                actualizarTamanos();
            }
        });
    }

    private void actualizarTamanos() {
        int anchoTotal = getWidth() - (2 * Config.MARGEN);
        int altoTotal = getHeight() - (2 * Config.MARGEN);

        int anchoMenu = panelMenu.isMenuExpandido() ? panelMenu.getANCHO_EXPANDIDO() : panelMenu.getANCHO_CONTRAIDO();
        int anchoContenido = anchoTotal - anchoMenu - Config.ESPACIO;

        // Asegurar que el ancho no sea negativo
        if (anchoContenido < 0) {
            anchoContenido = 0;
        }

        // Actualizar tamaños
        panelIzquierdo.setPreferredSize(new Dimension(anchoMenu + Config.ESPACIO, altoTotal));
        panelMenu.actualizarTamanos();
        panelContenido.actualizarTamanos(panelMenu.isMenuExpandido(), anchoTotal, anchoMenu, altoTotal);
        panelEspacio.setPreferredSize(new Dimension(Config.ESPACIO, altoTotal));

        revalidate();
        repaint();
    }

    public void alternarMenu() {
        panelMenu.alternarMenu();
    }

    
     
  
}