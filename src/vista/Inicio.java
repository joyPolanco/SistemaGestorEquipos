package vista;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Inicio extends JFrame implements MouseListener{

    private static final long serialVersionUID = 1L;
    private JPanel panelIzquierdo;
    private JPanel panelDerecho;
    private JPanel contenedorDoble;
    private JPanel contIcon;
    private JPanel contBotonesMenu;
    private boolean menuActivado=true;

	private int anchoIzquierdo;
	private int anchoDerecho;
    private Icon logoIcon;
    private JButton btn1;
    private JButton btn2;
   private JButton btn3;
   private JButton btn4;
   private JButton btn5;
   private JButton btn6;
	private JPanel pnlBarra;
	private JPanel contTabs;
	private  JLabel logo ;
private final Color FONDO_GENERAL = new Color (0, 2, 73);
private final Color FONDO_BARRA = new Color(0, 64, 128);
private JLabel icon;


    public Inicio() {
        estilizarFrame();
        inicializarContenedorDoble();
        estilizarPnlIzquierdo();
        estilizarPnlDerecho();
        configurarContdoble();
        getContentPane().add(contenedorDoble, BorderLayout.CENTER);
        configuracionPanelDerecho();
        addComponentListener(new ComponentAdapter() {
        	@Override
        	public void componentResized (ComponentEvent e) {
        		actualizarTamaños(menuActivado);
        	}
		});
    }

    private void inicializarContenedorDoble() {
        contenedorDoble = new JPanel();
        contenedorDoble.setLayout(new BorderLayout()); 
        contenedorDoble.setBorder(new EmptyBorder(15, 15, 15, 15));
        contenedorDoble.setBackground(FONDO_GENERAL);
    }
 

    private void configurarContdoble() {
        contenedorDoble.add(panelIzquierdo, BorderLayout.WEST);
        contenedorDoble.add(panelDerecho, BorderLayout.EAST);
    }

    private void estilizarFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setLayout(new BorderLayout());
    }

    private void estilizarPnlDerecho() {
        panelDerecho = new JPanel();
        panelDerecho.setLayout(new BorderLayout(10,10));
        panelDerecho.setBackground(FONDO_GENERAL);
    }

    private void estilizarPnlIzquierdo() {
        panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BorderLayout());
        panelIzquierdo.setBackground(FONDO_GENERAL);
        panelIzquierdo.setBorder(new EmptyBorder(2, 2, 2, 2));
        añadirElementosIzquierdos();
    }
    private void actualizarTamaños(boolean menuActivado){
    	
    	int width =getWidth();
    	int height = getHeight();
    	if (menuActivado) {
    	anchoIzquierdo= (int)(width*0.16);
    	anchoDerecho= (int)(width*0.802);
    	}
    	else {
    		anchoIzquierdo= (int)(width*0.08);
        	anchoDerecho= (int)(width*0.884);
    	}
    	 panelIzquierdo.setPreferredSize(new Dimension(anchoIzquierdo, height));
         panelDerecho.setPreferredSize(new Dimension(anchoDerecho, height));
         contIcon.setPreferredSize(new Dimension (22,(int) (height*0.18)));
         contBotonesMenu.setPreferredSize(new Dimension (80,(int) (pnlBarra.getHeight()*0.70)));
         btn1.setPreferredSize(new Dimension(anchoIzquierdo-40, (int)((height-40)*0.09)));
         btn2.setPreferredSize(new Dimension(anchoIzquierdo-40, (int)((height-40)*0.09)));
         btn3.setPreferredSize(new Dimension(anchoIzquierdo-40, (int)((height-40)*0.09)));
         btn4.setPreferredSize(new Dimension(anchoIzquierdo-40, (int)((height-40)*0.09)));
         btn5.setPreferredSize(new Dimension(anchoIzquierdo-40, (int)((height-40)*0.09)));
         btn6.setPreferredSize(new Dimension(anchoIzquierdo-40, (int)((height-40)*0.09)));

         contenedorDoble.revalidate();
         contenedorDoble.repaint();
    }
    
   
    
    
    private void añadirElementosIzquierdos() {
    	
    	 contIcon= new JPanel ();
    	 contIcon.setBorder(new EmptyBorder(3,20,3,3));
    	 contIcon.setLayout(new BorderLayout());
    	 
    	  icon = new JLabel();
    	 logo = new JLabel("");
    	 logo.setLayout(new BoxLayout(logo, BoxLayout.Y_AXIS));
    	 ImageIcon iconLogo = new ImageIcon(Inicio.class.getResource("/recursosGraficos/imagenes/idacLogo.png"));
	     Image img = iconLogo.getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH);
    	 logoIcon= new ImageIcon(img);
	     logo.setIcon(logoIcon);
    	 icon.setIcon(new ImageIcon(Inicio.class.getResource("/recursosGraficos/menu.png")));
    	 contIcon.add(icon, BorderLayout.EAST);
    	 contIcon.add(logo, BorderLayout.SOUTH);
         contIcon.setBackground(FONDO_BARRA);
    	 icon.addMouseListener(this);
    	 
    	 contBotonesMenu = new JPanel ();
    	 contBotonesMenu.setBackground(FONDO_BARRA);
    	 
    	 JLabel texto = new JLabel("Devices administrator");
    	 texto.setForeground(Color.white);
    	 texto.setFont(new Font("Segoe UI Historic", Font.BOLD, 16));
    	 
    	 pnlBarra = new JPanel();
    	 pnlBarra.setLayout(new BorderLayout());
    	 SwingStyle.redondear(pnlBarra, 30, FONDO_BARRA);
    	 pnlBarra.add(contIcon,BorderLayout.NORTH);
    	 pnlBarra.add(texto);
    	 pnlBarra.add(contBotonesMenu,BorderLayout.SOUTH);
    	 pnlBarra.setBackground(FONDO_BARRA);
    	 panelIzquierdo.add(pnlBarra);
    	 configurarPanelMenu();
    }
    
    void configurarOcultarMenu() {
    	menuActivado=!menuActivado;
 		actualizarTamaños(menuActivado);
 		if (menuActivado) {
 			
 	    	 logo.setIcon(logoIcon);
 	    	 btn1.setText("Inicio");
 	    	 btn2.setText("Almacen");
 	    	 btn3.setText("Aeropuertos");
 	    	 btn4.setText("Auditoria");
 	    	 btn5.setText("Perfil");
 	    	 btn6.setText("Ayuda");
 	    	 contIcon.remove(icon);
 	    	 contIcon.add(icon, BorderLayout.EAST);
 	    	  btn1.setHorizontalAlignment(SwingConstants.LEFT);
 	    	  btn2.setHorizontalAlignment(SwingConstants.LEFT);
 	    	  btn3.setHorizontalAlignment(SwingConstants.LEFT);
 	    	  btn4.setHorizontalAlignment(SwingConstants.LEFT);
 	    	  btn5.setHorizontalAlignment(SwingConstants.LEFT);
 	    	  btn6.setHorizontalAlignment(SwingConstants.LEFT);

 		}
 	    	 else {
	 	    	 logo.setIcon(null);
	 	    	 btn1.setText("");
	 	    	 btn2.setText("");
	 	    	 btn3.setText("");
	 	    	 btn4.setText("");
	 	    	 btn5.setText("");
	 	    	 btn6.setText("");
	 	    	 contIcon.remove(icon);
	 	    	 contIcon.add(icon,BorderLayout.CENTER);
	 	    	  btn1.setHorizontalAlignment(SwingConstants.CENTER);
	 	    	  btn2.setHorizontalAlignment(SwingConstants.CENTER);
	 	    	  btn3.setHorizontalAlignment(SwingConstants.CENTER);
	 	    	  btn4.setHorizontalAlignment(SwingConstants.CENTER);
	 	    	  btn5.setHorizontalAlignment(SwingConstants.CENTER);
	 	    	  btn6.setHorizontalAlignment(SwingConstants.CENTER);

 	    	 }
    }
    
    void  configurarPanelMenu() {
    	contBotonesMenu.setLayout(new FlowLayout());
    	generarBotonesMenu();
    }

    void generarBotonesMenu () {
    	 btn1= new JButton ("Inicio");
    	 btn2= new JButton ("Almacen");
    	 btn3= new JButton ("Aeropuertos");
    	 btn4= new JButton ("Auditoria");
    	 btn5= new JButton ("Perfil");
    	 btn6= new JButton ("Ayuda");
    	 
    	 //establecer iconos
    	 btn1.setIcon(new ImageIcon(Inicio.class.getResource("/recursosGraficos/imagenes/inicio.png")));
    	 btn2.setIcon(new ImageIcon(Inicio.class.getResource("/recursosGraficos/imagenes/comercio.png")));
    	 btn3.setIcon(new ImageIcon(Inicio.class.getResource("/recursosGraficos/imagenes/aeropuerto.png")));
    	 btn4.setIcon(new ImageIcon(Inicio.class.getResource("/recursosGraficos/imagenes/auditoria.png")));
    	 btn5.setIcon(new ImageIcon(Inicio.class.getResource("/recursosGraficos/imagenes/busqueda.png")));
    	 btn6.setIcon(new ImageIcon(Inicio.class.getResource("/recursosGraficos/imagenes/ayuda.png")));

    	 //estilizar
    	 SwingStyle.redondearBoton(btn1,30,FONDO_BARRA);
    	 SwingStyle.redondearBoton(btn2,30,FONDO_BARRA);
    	 SwingStyle.redondearBoton(btn3,30,FONDO_BARRA);
    	 SwingStyle.redondearBoton(btn4,30,FONDO_BARRA);
    	 SwingStyle.redondearBoton(btn5,30,FONDO_BARRA);
    	 SwingStyle.redondearBoton(btn6,30,FONDO_BARRA);

    	 contBotonesMenu.add(btn1);
    	 contBotonesMenu.add(btn2);
    	 contBotonesMenu.add(btn3);
    	 contBotonesMenu.add(btn4);
    	 contBotonesMenu.add(btn5);
    	 contBotonesMenu.add(btn6);
    	 
    	 btn1.addMouseListener(this);
    	 btn2.addMouseListener(this);
    	 btn3.addMouseListener(this);
    	 btn4.addMouseListener(this);
    	 btn5.addMouseListener(this);
    	 btn6.addMouseListener(this);

    }
    
    private void configuracionPanelDerecho(){
    	panelDerecho.setBackground(getForeground());
    	configurarContTabs();
    }
    
    private void configurarContTabs () {
    	contTabs= new JPanel ();
    	SwingStyle.redondear(contTabs, 30, Color.white);
    	panelDerecho.add(contTabs);
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource().getClass().getTypeName().equals("javax.swing.JButton")) {

		}
		else if (e.getSource().getClass().getTypeName().equals("javax.swing.JLabel")) {
			configurarOcultarMenu();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource().getClass().getTypeName().equals("javax.swing.JButton")) {
		JButton btn = (JButton) e.getSource();
		btn.setBackground(new Color (71, 149, 208));
        btn.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
		}

	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource().getClass().getTypeName().equals("javax.swing.JButton")) {
			JButton btn = (JButton) e.getSource();
			btn.setBackground(FONDO_BARRA);
	        btn.setFont(new Font("Segoe UI Semibold", Font.BOLD, 17));

			}
		
	}
}
