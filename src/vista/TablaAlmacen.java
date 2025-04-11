package vista;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.*;
import modelo.Equipo;
import modelo.EquipoAeropuerto;
import modelo.Aeropuerto;

public class TablaAlmacen extends JTable {
    // Colores mejorados con gradientes sutiles
    private static final Color COLOR_TEXTO_ENCABEZADO = new Color(240, 248, 255);
    private static final Color COLOR_FONDO_FILA = new Color(248, 249, 252);
    private static final Color COLOR_FONDO_FILA_SELECCIONADA = new Color(210, 230, 255);
    
    // Sombras para profundidad
    private static final DropShadowBorder BORDE_SOMBRA = 
        new DropShadowBorder(Color.BLACK, 5, 0.3f, 12, false, true, true, true);
    
    private Aeropuerto aeropuerto;
    private DefaultTableModel model;
    private PanelAlmacen panelPadre;
    
    public TablaAlmacen(Aeropuerto aeropuerto, PanelAlmacen panelPadre) {
        this.aeropuerto = aeropuerto;
        this.panelPadre = panelPadre;
        this.model = crearModelo();
        setModel(model);
        
        configurarApariencia();
        configurarAcciones();
        setShowHorizontalLines(false);
        setShowVerticalLines(false);
    }

    private DefaultTableModel crearModelo() {
        String[] columnas = {"Serie", "Modelo", "Marca", "Estado", "Acciones"};
        
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 4 ? JPanel.class : Object.class;
            }
        };
    }

    private void configurarApariencia() {
        setRowHeight(80); // Más espacio para mejor legibilidad
        setIntercellSpacing(new Dimension(0, 0));
        setFillsViewportHeight(true);
        
        // Renderizador mejorado con efectos hover
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    setBackground(row % 2 == 0 ? Color.WHITE : COLOR_FONDO_FILA);
                } else {
                    setBackground(COLOR_FONDO_FILA_SELECCIONADA);
                }
                
                setForeground(new Color(50, 50, 50));
                setFont(new Font("Segoe UI", Font.PLAIN, 14));
                setHorizontalAlignment(CENTER);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                
                return this;
            }
        });
        
        // Encabezado con gradiente y sombra
        JTableHeader header = new JTableHeader(getColumnModel()) ;
         header.setBackground(new Color(70, 130, 180));
        header.setForeground(COLOR_TEXTO_ENCABEZADO);
        header.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        header.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, new Color(180, 190, 200)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        header.setReorderingAllowed(false);
        setTableHeader(header);
        
        // Borde con esquinas redondeadas
        setBorder(BorderFactory.createCompoundBorder(
            BORDE_SOMBRA,
            new EmptyBorder(5, 5, 5, 5)
        ));
        setOpaque(false);
    }

    private void configurarAcciones() {
        getColumn("Acciones").setCellRenderer(new AccionesRenderer());
        getColumn("Acciones").setCellEditor(new AccionesEditor(this));
        getColumn("Acciones").setPreferredWidth(200); // Más espacio para botones
    }

    public void refrescar() {
        model.setRowCount(0);
        
        if (aeropuerto != null && aeropuerto.getEquipos() != null) {
            for (Equipo equipo : aeropuerto.getEquipos()) {
                if (equipo != null && equipo.getSerie() != null) {
                    model.addRow(new Object[]{
                        equipo.getSerie(),
                        equipo.getModelo(),
                        equipo.getMarca(),
                        
                        ""
                    });
                }
            }
        }
    }

    // Clase interna mejorada para renderizar botones
    private class AccionesRenderer extends DefaultTableCellRenderer {
        private JPanel panel;
        private JButton btnVer, btnEdit, btnDel;
        
        public AccionesRenderer() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8)) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            panel.setOpaque(false);
            panel.setBorder(new EmptyBorder(10, 5, 10, 5));
            
            // Botones con alta calidad de imagen y efectos hover
            btnVer = crearBotonPremium("/RecursosGraficos/imagenes/ojo.png", "Ver detalles");
            btnEdit = crearBotonPremium("/RecursosGraficos/imagenes/editar.png", "Editar");
            btnDel = crearBotonPremium("/RecursosGraficos/imagenes/eliminar.png", "Eliminar");
            
            panel.add(btnVer);
            panel.add(btnEdit);
            panel.add(btnDel);
        }
        
        private JButton crearBotonPremium(String iconPath, String tooltip) {
            JButton btn = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    
                    if (getModel().isRollover() || getModel().isPressed()) {
                        g2.setColor(new Color(230, 240, 255, 120));
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    }
                    
                    super.paintComponent(g);
                }
            };
            
            // Carga de imagen en alta calidad
            try {
                ImageIcon originalIcon = new ImageIcon(getClass().getResource(iconPath));
                if (originalIcon.getImage() != null) {
                    Image scaled = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                    Image highQuality = scaled.getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING);
                    btn.setIcon(new ImageIcon(highQuality));
                }
            } catch (Exception e) {
                btn.setText(tooltip.substring(0, 1));
            }
            
            btn.setPreferredSize(new Dimension(36, 36));
            btn.setToolTipText(tooltip);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(1, 1, 1, 1, new Color(220, 220, 220, 80)),
                new EmptyBorder(5, 5, 5, 5)
            ));
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setRolloverEnabled(true);
            
            return btn;
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            boolean filaValida = row >= 0 && row < table.getRowCount() && 
                               table.getValueAt(row, 0) != null;
            
            btnVer.setVisible(filaValida);
            btnEdit.setVisible(filaValida);
            btnDel.setVisible(filaValida);
            
            panel.setBackground(isSelected ? COLOR_FONDO_FILA_SELECCIONADA : 
                (row % 2 == 0 ? Color.WHITE : COLOR_FONDO_FILA));
            return panel;
        }
    }

    // Editor mejorado con animaciones
    private class AccionesEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JTable table;
        private JButton btnVer, btnEdit, btnDel;
        private int editingRow;
        
        public AccionesEditor(JTable table) {
            this.table = table;
            
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8)) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            panel.setOpaque(false);
            panel.setBorder(new EmptyBorder(10, 5, 10, 5));
            
            // Botones con efectos de hover mejorados
            btnVer = crearBotonAnimado("/RecursosGraficos/imagenes/ojo.png", "Ver detalles", e -> {
                String serie = (String) table.getValueAt(editingRow, 0);
                String modelo = (String) table.getValueAt(editingRow, 1);
                String marca = (String) table.getValueAt(editingRow, 2);

                panelPadre.getTabbed().getVistaEquipo().setAeropuertoActual(panelPadre.getAeropuertoActual());
                panelPadre.getTabbed().getVistaEquipo().setEqElegido(panelPadre.getAeropuertoActual().buscarEquipo(serie,modelo,marca));
                panelPadre.getTabbed().setSelectedIndex(8);
                fireEditingStopped();
            });
            
            btnEdit = crearBotonAnimado("/RecursosGraficos/imagenes/editar.png", "Editar", e -> {
                String serie = (String) table.getValueAt(editingRow, 0);
                String modelo = (String) table.getValueAt(editingRow, 1);
                String marca = (String) table.getValueAt(editingRow, 2);
                if (panelPadre != null) {
     //               panelPadre.editarEquipo(serie);
                	Aeropuerto aero = panelPadre.getAeropuertoActual();
                    Equipo equipo = aero.buscarEquipo(serie, modelo, marca);
                    panelPadre.getTabbed().getEditarEq().setEquipo(equipo);
                    panelPadre.getTabbed().getEditarEq().setAeropuerto(aero);
                    panelPadre.getTabbed().setSelectedIndex(9);
                }
                fireEditingStopped();
            });
            
            btnDel = crearBotonAnimado("/RecursosGraficos/imagenes/eliminar.png", "Eliminar", e -> {
                mostrarConfirmacionEliminacion();
            });
            
            panel.add(btnVer);
            panel.add(btnEdit);
            panel.add(btnDel);
        }
        
        private JButton crearBotonAnimado(String iconPath, String tooltip, ActionListener action) {
            JButton btn = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                    
                    if (getModel().isRollover()) {
                        g2.setColor(new Color(13, 110, 253, 30));
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    }
                    if (getModel().isPressed()) {
                        g2.setColor(new Color(13, 110, 253, 60));
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    }
                    
                    super.paintComponent(g);
                }
            };
            
            // Carga de imagen con máxima calidad
            try {
                ImageIcon originalIcon = new ImageIcon(getClass().getResource(iconPath));
                if (originalIcon.getImage() != null) {
                    Image highQuality = originalIcon.getImage()
                        .getScaledInstance(24, 24, Image.SCALE_SMOOTH)
                        .getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING);
                    btn.setIcon(new ImageIcon(highQuality));
                }
            } catch (Exception e) {
                btn.setText(tooltip.substring(0, 1));
            }
            
            btn.setPreferredSize(new Dimension(36, 36));
            btn.setToolTipText(tooltip);
            btn.addActionListener(action);
            btn.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(1, 1, 1, 1, new Color(220, 220, 220, 80)),
                new EmptyBorder(5, 5, 5, 5)
            ));
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.setRolloverEnabled(true);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            return btn;
        }
        
        private void mostrarConfirmacionEliminacion() {
            DialogoConfirmacion dialogo = new DialogoConfirmacion(
                table, 
                "¿Eliminar este equipo del aeropuerto?", DialogoConfirmacion.TipoMensaje.INFORMACION
            );
            
            if (dialogo.mostrar()) {
                String serie = (String) table.getValueAt(editingRow, 0);
                aeropuerto.getEquipos().removeIf(eq -> eq.getSerie().equals(serie));
                panelPadre.getAeropuertoActual().getDaoEquiposAero().eliminarEquipo(aeropuerto, serie);
                panelPadre.onDataChanges(true);
            
            }
        }
        private JButton crearBotonDialogo(String texto, Color color, ActionListener action) {
            JButton btn = new JButton(texto) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    
                    if (getModel().isRollover()) {
                        g2.setColor(color.brighter());
                    } else {
                        g2.setColor(color);
                    }
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    
                    super.paintComponent(g);
                }
            };
            
            btn.setPreferredSize(new Dimension(80, 30));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            btn.setContentAreaFilled(false);
            btn.setFocusPainted(false);
            btn.addActionListener(action);
            
            return btn;
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            editingRow = row;
            panel.setBackground(isSelected ? COLOR_FONDO_FILA_SELECCIONADA : 
                (row % 2 == 0 ? Color.WHITE : COLOR_FONDO_FILA));
            return panel;
        }
        
        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
    
    public void setAeropuerto(Aeropuerto aeropuerto) {
        this.aeropuerto = aeropuerto;
        refrescar();
    }
}

class DropShadowBorder extends AbstractBorder {
    private Color color;
    private int thickness;
    private Insets insets;
    private RenderingHints hints;

    public DropShadowBorder(Color color, int thickness, float opacity, int offset, 
                          boolean showTop, boolean showLeft, boolean showBottom, boolean showRight) {
        this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(opacity * 255));
        this.thickness = thickness;
        this.insets = new Insets(
            showTop ? offset : 0, 
            showLeft ? offset : 0, 
            showBottom ? offset : 0, 
            showRight ? offset : 0
        );
        
        hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHints(hints);
        
        int shadowSize = thickness;
        
        for (int i = 0; i < shadowSize; i++) {
            float ratio = (float)i / (float)shadowSize;
            int alpha = (int)(ratio * 255);
            g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
            g2.drawRoundRect(
                x - i, 
                y - i, 
                width + i + i - 1, 
                height + i + i - 1,
                10, 10
            );
        }
        
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return insets;
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        return getBorderInsets(c);
    }
}