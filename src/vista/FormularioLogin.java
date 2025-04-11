package vista;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import modelo.AlmacenGeneral;
import modelo.Usuario;
import vista.DialogoConfirmacion.TipoMensaje;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Font;
import java.awt.JobAttributes;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormularioLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int anchoPanelGeneral;
	private int altoPanelGeneral;

	private final double PORCENTAJE_TAMANO_IZQ=0.30;
	private final double PORCENTAJE_TAMANO_DER=0.70;
	private final JPanel panelContenedor;
	private  JPanel  panelIzquierdo ;
	private  JPanel  panelDerecho;
	private int width;
	private int height;
	  private int borderForm=5;
	  private JPanel panel;
	  private JLabel lblNewLabel;
	  private JTextField textField;
	  private JPasswordField  textFieldPs;
	  private JLabel titulo1;
	  private JLabel titulo2;
	  private JLabel titulo3;
	  private JButton btnNewButton;
	  private final Color FONDO_GENERAL = new Color (0, 2, 73);
	  private final Color FONDO_BARRA = new Color(0, 64, 128);
	  private final int altoBtn=40;
	  private final int anchoBtn=300;
	  private AlmacenGeneral almacen;

	private JComponent es;

	/**
	 * Launch the application.
	 */
	

    public FormularioLogin(AlmacenGeneral almacen) {
    	this.almacen=almacen;
    	estilizarFrame();
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(borderForm, borderForm, borderForm, borderForm));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        panelContenedor = new JPanel();
        contentPane.add(panelContenedor);
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.X_AXIS));
        
        panelIzquierdo = new JPanel();
        panelDerecho = new JPanel();
        
       
        configurarPaneles();
        
        panelContenedor.add(panelIzquierdo);
        panelContenedor.add(panelDerecho);
        
  
        setVisible(true);
    }
    
    private void estilizarFrame () {
    	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         width=800;
         height= 488;
        borderForm=5;
        setSize(width, height); // 
        setLocationRelativeTo(null); 
        setResizable (false);
        SwingStyle.redondearFrame(this, 20);

    }

void configurarPaneles() {
	   
    int totalWidth = width - borderForm*2; 
    configurarPanelDerecha(totalWidth);
    configurarPanelIzquierda(totalWidth);

}

private void configurarPanelDerecha(int width) {
	 panelDerecho.setPreferredSize(new Dimension(
		        (int)(width * PORCENTAJE_TAMANO_DER), 
		        height - 10));
	      panelDerecho.setBackground(Color.white);
	      panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.X_AXIS));
	      panelDerecho.setBorder(new EmptyBorder(40,40,40,40));
	      panel = new JPanel();

	        panelDerecho.add(panel);
	        panel.setBackground(Color.white);
	        panel.setPreferredSize(new Dimension (100,200));
	        panel.setLayout(new FlowLayout ());
	        
	        crearComponentesDerecha();
	        establecerTamaño();
	
	        panel.add(titulo1);
	        panel.add(titulo2);
	        panel.add(textField);
	        panel.add(titulo3);
	        panel.add(textFieldPs);
	        panel.add(es);
	        panel.add(btnNewButton);



}
private void establecerTamaño() {
    setTamañoComponente(titulo1,50);
    setTamañoComponente(titulo2,50);
    setTamañoComponente(textField,40);
    setTamañoComponente(titulo3,40);
    setTamañoComponente(textFieldPs,40);
    setTamañoComponente(es,10);
    setTamañoComponente(btnNewButton,altoBtn);	
}

private void crearComponentesDerecha() {
	titulo1 = new JLabel("Bienvenido");
    titulo1.setFont(new Font("Sitka Text", Font.BOLD, 19));
    titulo2 = new JLabel("Correo Institucional");
    titulo2.setFont(new Font("Sitka Small", Font.PLAIN, 12));
    textField = new JTextField();
    titulo3 = new JLabel("Contraseña");
    titulo3.setFont(new Font("Sitka Small", Font.PLAIN, 12));
    textFieldPs = new JPasswordField();
    btnNewButton = new JButton("Iniciar Sesion");
    btnNewButton.addMouseListener(new MouseAdapter() {
    	@Override
    	public void mouseEntered(MouseEvent e) {
    		btnNewButton.setBackground(new Color (71, 149, 208));	
    		btnNewButton.setPreferredSize(new Dimension (anchoBtn+5,altoBtn+5) );
    	}
    	@Override
    	public void mouseExited(MouseEvent e) {
    		btnNewButton.setBackground(FONDO_BARRA);	
    		btnNewButton.setPreferredSize(new Dimension (anchoBtn,altoBtn) );

    	}
    });
    btnNewButton.addActionListener(new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
    		iniciarSesion () ;
    	}
    });
    btnNewButton.setFocusPainted(false);
    btnNewButton.setBackground(FONDO_BARRA);
    btnNewButton.setForeground(Color.white);
     es=new JLabel ();
}

private void setTamañoComponente(JComponent componente, int altura) {
	componente.setPreferredSize(new Dimension (300, altura));
}

private void configurarPanelIzquierda(int width) {
    panelIzquierdo.setPreferredSize(new Dimension(
            (int)(width * PORCENTAJE_TAMANO_IZQ), 
            height - 10)); 
SwingStyle.redondear(panelIzquierdo, 20, FONDO_GENERAL);
        
}


void iniciarSesion () {

	String user = textField.getText();
	String contraseña= new String(textFieldPs.getPassword()).trim();
	Usuario usuario= almacen.buscarUsuario(user);

	if (user.isEmpty() || contraseña.isEmpty()) {
		 DialogoConfirmacion dialog = new DialogoConfirmacion(this, "Debe ingresar un usuario y contraseña", TipoMensaje.ERROR);
	}
	else {
	
	 if (usuario==null|| !contraseña.equals(usuario.getContraseña())) {
		 DialogoConfirmacion dialog = new DialogoConfirmacion(this, "El usuario o la contraseña son incorrectos", TipoMensaje.ERROR);
		 dialog.mostrar();
	 }
	 
	 else {
		 
	dispose();
	new VentanaPrincipal(almacen, usuario);
	
		 }	 
	

	 }
	 }

}


