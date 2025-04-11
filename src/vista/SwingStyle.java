package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;


public class SwingStyle {
	public static void redondear(JComponent componente, int radio, Color fondo) {
	    componente.setOpaque(false); // Importante para que se vea el redondeo
	    componente.setBackground(fondo);
	    
	    componente.setBorder(new javax.swing.border.Border() {
	        @Override
	        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	            Graphics2D g2 = (Graphics2D) g.create();
	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            g2.setColor(fondo);
	            g2.fillRoundRect(x, y, width - 1, height - 1, radio, radio);
	            g2.dispose();
	        }
	        
	        @Override
	        public Insets getBorderInsets(Component c) {
	            return new Insets(radio / 2, radio / 2, radio / 2, radio / 2); // Margen interno
	        }
	        
	        @Override
	        public boolean isBorderOpaque() {
	            return false;
	        }
	    });
	}
	
	public static void estilizarTxt(JTextField txt) {
		txt.setColumns(10);

		txt.setBorder(BorderFactory.createCompoundBorder(
			    BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
			    BorderFactory.createEmptyBorder(8, 10, 8, 10)
			));
	}
	
	  public static void redondearFrame(JFrame frame, int radioEsquinas) {
	        // 1. Eliminar la decoración nativa (barra de título, bordes)
	        frame.setUndecorated(true);
	        
	        // 2. Aplicar forma redondeada
	        frame.setShape(new RoundRectangle2D.Double(
	            0, 0, 
	            frame.getWidth(), frame.getHeight(), 
	            radioEsquinas, radioEsquinas
	        ));
}
	  
	  
	  public static void redondearBoton(JButton boton, int radius, Color fondo) {
		    // Configuración básica
		    boton.setOpaque(false);
		    boton.setBorderPainted(false);
		    boton.setContentAreaFilled(false);
		    boton.setFocusPainted(false);
		    boton.setBackground(fondo);
		    boton.setForeground(Color.WHITE);
		    
		    // Establecer un borde vacío para mantener el margen interno
		    boton.setBorder(new EmptyBorder(5, 15, 5, 15));
		    
		    // Implementación personalizada del pintado
		    boton.setUI(new BasicButtonUI() {
		        @Override
		        public void paint(Graphics g, JComponent c) {
		            AbstractButton b = (AbstractButton) c;
		            ButtonModel model = b.getModel();
		            Graphics2D g2 = (Graphics2D) g.create();
		            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		            
		            // Pintar fondo
		            if (model.isPressed()) {
		                g2.setColor(fondo.darker());
		            } else if (model.isRollover()) {
		                g2.setColor(new Color(71, 149, 208));
		            } else {
		                g2.setColor(fondo);
		            }
		            
		            g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), radius, radius);
		            
		            // Pintar borde
		            g2.setColor(fondo.brighter());
		            
		            // Pintar texto e icono
		            super.paint(g2, c);
		            g2.dispose();
		        }
		    });
		}
	  
	  public static void ocultarBarraPestanas(JTabbedPane tabbedPane, int radio, Color fondo) {
		  tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
		        @Override
		        protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
		            return 0;
		        }
		        
		        @Override
		        protected int calculateTabAreaWidth(int tabPlacement, int vertRunCount, int maxTabWidth) {
		            return 0;
		        }
		        
		        @Override
		        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
		            // No pintar ningún borde
		        }
		        
		        @Override
		        protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
		            // No pintar área de pestañas
		        }
		    });
		 
		    // Eliminar márgenes innecesarios
		}
}