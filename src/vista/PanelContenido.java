package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;



public class PanelContenido extends JPanel  implements IMenuListener{

	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	
	private TabbedPanel panel;
	public PanelContenido(TabbedPanel pnl) {
		panel=pnl;
    setBackground(Color.blue);
	SwingStyle.redondear(this, 25,Color.white);
	setLayout(new BorderLayout());
	this.add(panel);
	}
	@Override
	public void onMenuButtonClicked( MouseEvent e) {
		JButton btn = (JButton) e.getSource();
		
		String name = btn.getName();
       cambiarPaneles(name);	
	}

	
	public void actualizarTamanos(boolean isExpanded ,int anchoTotal, int anchoMenu, int altura) {
        setPreferredSize(new Dimension(anchoTotal-anchoMenu, altura));

	}
	public void cambiarPaneles (String nombreBoton) {
	 switch (nombreBoton) {
	
	case "btn0": 
		panel.setSelectedIndex(0);
		break;
	case "btn1": 
		panel.setSelectedIndex(1);
		break;
	case "btn2": 
		panel.setSelectedIndex(2);
		break;
	case "btn3": 
		panel.setSelectedIndex(4);
		break;
	case "btn4": 
		panel.setSelectedIndex(5);
		break;
	case "btn5": 
		panel.setSelectedIndex(6);
		break;
	default:
		throw new IllegalArgumentException();
	}
		
	}
	
	
	@Override
	public void onMenuToggle(boolean menuExpandido) {
	}
	
}
