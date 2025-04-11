package vista;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import modelo.AlmacenGeneral;
import modelo.Usuario;

public class PanelPerfil extends JPanel {
    private static final long serialVersionUID = 1L;
    
    // Colores corporativos
    private static final Color COLOR_PRIMARIO = new Color(0, 82, 155);
    private static final Color COLOR_SECUNDARIO = new Color(240, 248, 255);
    private static final Color COLOR_TEXTO = new Color(60, 60, 60);
    private static final Color COLOR_BORDE = new Color(210, 210, 210);
    
    // Componentes
    private JLabel lblNombre, lblRol, lblTitulo;
    private JButton btnEditar, btnAgregarUsuario;
    private JTable tablaUsuarios;
    private JScrollPane scrollUsuarios;
    private Usuario usuario;
    
    public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public PanelPerfil(Usuario usuario) {
		this.usuario=usuario;
	
    	if (usuario!=null) {
    		
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);
        
        initCabecera();
        initInfoUsuario(usuario);
        
        if (usuario.getRol().equals("admin")) {
            initPanelAdmin();
        }
        }
    }
    
    private void initCabecera() {
        JPanel panelCabecera = new JPanel(new BorderLayout());
        panelCabecera.setBackground(Color.WHITE);
        panelCabecera.setBorder(new EmptyBorder(0, 0, 20, 0));
        
        lblTitulo = new JLabel("PERFIL DE USUARIO");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(COLOR_PRIMARIO);
        
        panelCabecera.add(lblTitulo, BorderLayout.WEST);
        add(panelCabecera, BorderLayout.NORTH);
    }
    
    private void initInfoUsuario(Usuario usuario) {
    	if (usuario!=null) {
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(new CompoundBorder(
            new MatteBorder(1, 1, 1, 1, COLOR_BORDE),
            new EmptyBorder(20, 20, 20, 20)
        ));
        panelInfo.setBackground(COLOR_SECUNDARIO);
        panelInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Icono de usuario
        JLabel lblIcono = new JLabel();
        //lblIcono.setIcon(new ImageIcon(getClass().getResource("")));
        lblIcono.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInfo.add(lblIcono);
        panelInfo.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Nombre
        JPanel panelNombre = crearPanelDato("Nombre:", usuario.getCorreo());
        panelInfo.add(panelNombre);
        panelInfo.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Rol
        JPanel panelRol = crearPanelDato("Rol:", usuario.getRol());
        panelInfo.add(panelRol);
        panelInfo.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Botón editar
        btnEditar = new JButton("Editar Perfil");
        estilizarBoton(btnEditar);
        btnEditar.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInfo.add(btnEditar);
        
        add(panelInfo, BorderLayout.CENTER);
    	}
    }
    
    private JPanel crearPanelDato(String etiqueta, String valor) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setBackground(COLOR_SECUNDARIO);
        panel.setMaximumSize(new Dimension(400, 30));
        
        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblEtiqueta.setForeground(COLOR_PRIMARIO);
        lblEtiqueta.setPreferredSize(new Dimension(100, 20));
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblValor.setForeground(COLOR_TEXTO);
        
        panel.add(lblEtiqueta);
        panel.add(lblValor);
        
        return panel;
    }
    
    private void initPanelAdmin() {
        JPanel panelAdmin = new JPanel(new BorderLayout());
        panelAdmin.setBorder(new CompoundBorder(
            new EmptyBorder(20, 0, 0, 0),
            new TitledBorder(
                new LineBorder(COLOR_PRIMARIO, 1), 
                "Administración de Usuarios",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                COLOR_PRIMARIO
            )
        ));
        panelAdmin.setBackground(Color.WHITE);
        
        // Botón agregar usuario
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(Color.WHITE);
        
        btnAgregarUsuario = new JButton("Agregar Usuario");
        estilizarBoton(btnAgregarUsuario);
        btnAgregarUsuario.setIcon(new ImageIcon(getClass().getResource("/iconos/agregar.png")));
        panelBotones.add(btnAgregarUsuario);
        
        panelAdmin.add(panelBotones, BorderLayout.NORTH);
        
        // Tabla de usuarios
        String[] columnas = {"Nombre", "Rol", "Último Acceso", "Estado"};
        Object[][] datos = {}; // Aquí irían los datos reales
        
        tablaUsuarios = new JTable(datos, columnas);
        tablaUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaUsuarios.setRowHeight(35);
        tablaUsuarios.setShowGrid(false);
        tablaUsuarios.setIntercellSpacing(new Dimension(0, 0));
        tablaUsuarios.setSelectionBackground(COLOR_PRIMARIO.brighter());
        
        // Personalizar encabezado
        JTableHeader header = tablaUsuarios.getTableHeader();
        header.setBackground(COLOR_PRIMARIO);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        scrollUsuarios = new JScrollPane(tablaUsuarios);
        scrollUsuarios.setBorder(new LineBorder(COLOR_BORDE));
        
        panelAdmin.add(scrollUsuarios, BorderLayout.CENTER);
        add(panelAdmin, BorderLayout.SOUTH);
    }
    
    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setBackground(Color.WHITE);
        boton.setForeground(COLOR_PRIMARIO);
        boton.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_PRIMARIO, 1),
            new EmptyBorder(8, 15, 8, 15)
        ));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setFocusPainted(false);
        
        // Efecto hover
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(COLOR_PRIMARIO);
                boton.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(Color.WHITE);
                boton.setForeground(COLOR_PRIMARIO);
            }
        });
    }
    
    // Métodos para acceder a los componentes desde el controlador
    public JButton getBtnEditar() {
        return btnEditar;
    }
    
    public JButton getBtnAgregarUsuario() {
        return btnAgregarUsuario;
    }
    
    public JTable getTablaUsuarios() {
        return tablaUsuarios;
    }
}