package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import modelo.Equipo;
import modelo.EquipoAeropuerto;
import modelo.EquipoLocal;
import modelo.Aeropuerto;
import modelo.AlmacenGeneral;


public class TablaAlmacenG extends JTable {
    // Colores consistentes
    private static final Color COLOR_ENCABEZADO = new Color(70, 130, 180);
    private static final Color COLOR_TEXTO_ENCABEZADO = Color.WHITE;
    private static final Color COLOR_BORDE = new Color(230, 230, 230);
    private static final Color COLOR_FONDO_FILA = new Color(248, 249, 250);
    private static final Color COLOR_FONDO_FILA_SELECCIONADA = new Color(220, 240, 255);
    
    private AlmacenGeneral almacen;
    private DefaultTableModel model;
    private PanelAlmacenGeneral panelPadre;
    private IMenuListener menuListener;
    private ITablaListener tablaListener;
    private Equipo EquipoElegido;

    public PanelAlmacenGeneral getPanelPadre() {
        return panelPadre;
    }

    public void setPanelPadre(PanelAlmacenGeneral panelPadre) {
        this.panelPadre = panelPadre;
    }

    public IMenuListener getMenuListener() {
        return menuListener;
    }

    public ITablaListener getTablaListener() {
        return tablaListener;
    }

    public void setTablaListener(ITablaListener tablaListener) {
        this.tablaListener = tablaListener;
    }

    public void setMenuListener(IMenuListener menuListener) {
        this.menuListener = menuListener;
    }

    public TablaAlmacenG(AlmacenGeneral almacen, PanelAlmacenGeneral panelPadre) {
        this.almacen = almacen;
        this.model = crearModelo();
        this.panelPadre = panelPadre;
        setModel(model);
        
        configurarApariencia();
        configurarAcciones();
        this.setTablaListener(panelPadre);
    }

    private DefaultTableModel crearModelo() {
        String[] columnas = {"Serie", "Modelo", "Marca", "Marbete", "Acciones"};
        
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

    private void setEquipoElegido(Equipo equipo) {
        EquipoElegido = equipo;
    }

    public Equipo getEquipoElegido() {
        return EquipoElegido;
    }

    private void configurarApariencia() {
        setRowHeight(80); // Altura adecuada para los botones
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0, 0));
        setFillsViewportHeight(true);
        
        // Renderizador mejorado con efectos hover
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : COLOR_FONDO_FILA);
                } else {
                    c.setBackground(COLOR_FONDO_FILA_SELECCIONADA);
                }
                
                c.setForeground(new Color(60, 60, 60));
                setFont(new Font("Segoe UI", Font.PLAIN, 14));
                setHorizontalAlignment(CENTER);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                
                return c;
            }
        });
        
        // Configuración del encabezado mejorado
        JTableHeader header = getTableHeader();
        header.setBackground(COLOR_ENCABEZADO);
        header.setForeground(COLOR_TEXTO_ENCABEZADO);
        header.setFont(new Font("Segoe UI Semibold", Font.BOLD, 14));
        header.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, new Color(200, 200, 200)),
            new EmptyBorder(10, 15, 10, 15)
        ));
        header.setReorderingAllowed(false);
        
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_BORDE),
            new EmptyBorder(5, 5, 5, 5)
        ));
    }

    private void configurarAcciones() {
        getColumn("Acciones").setCellRenderer(new AccionesRenderer());
        getColumn("Acciones").setCellEditor(new AccionesEditor(this));
        getColumn("Acciones").setPreferredWidth(200); // Más espacio para botones
    }

    public void refrescar() {
        model.setRowCount(0);
        
        if (almacen != null && almacen.getLstEGeneral() != null && !almacen.getLstEGeneral().isEmpty()) {
            for (EquipoLocal equipo : almacen.getLstEGeneral()) {
                if (equipo != null && equipo.getSerie() != null) {
                    model.addRow(new Object[]{
                        equipo.getSerie(),
                        equipo.getModelo(),
                        equipo.getMarca(),
                        equipo.getMarbete(),
                        ""
                    });
                }
            }
        }
    }

    // Clase interna para renderizar botones con iconos de alta calidad
    private class AccionesRenderer extends DefaultTableCellRenderer {
        private JPanel panel;
        private JButton btnVer, btnEdit, btnDel, btnSend;
        
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
            
            // Botones con iconos de alta calidad
            btnVer = crearBotonPremium("/RecursosGraficos/imagenes/ojo.png", "Ver detalles");
            btnEdit = crearBotonPremium("/RecursosGraficos/imagenes/editar.png", "Editar");
            btnDel = crearBotonPremium("/RecursosGraficos/imagenes/eliminar.png", "Eliminar");
            btnSend = crearBotonPremium("/RecursosGraficos/imagenes/enviar.png", "Mover a aeropuerto");
            
            panel.add(btnVer);
            panel.add(btnEdit);
            panel.add(btnDel);
            panel.add(btnSend);
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
                    // Escalado de alta calidad
                    Image scaled = originalIcon.getImage()
                        .getScaledInstance(24, 24, Image.SCALE_SMOOTH)
                        .getScaledInstance(24, 24, Image.SCALE_AREA_AVERAGING);
                    btn.setIcon(new ImageIcon(scaled));
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
                               table.getValueAt(row, 0) != null &&
                               !table.getValueAt(row, 0).toString().isEmpty();
            
            btnVer.setVisible(filaValida);
            btnEdit.setVisible(filaValida);
            btnDel.setVisible(filaValida);
            btnSend.setVisible(filaValida);
            
            panel.setBackground(isSelected ? COLOR_FONDO_FILA_SELECCIONADA : 
                (row % 2 == 0 ? Color.WHITE : COLOR_FONDO_FILA));
            return panel;
        }
    }

    // Editor mejorado con animaciones y funcionalidad completa
    private class AccionesEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JTable table;
        private JButton btnVer, btnEdit, btnDel, btnSend;
        private JPopupMenu menuAeropuertos;
        private int editingRow;
        
        public AccionesEditor(JTable table) {
            this.table = table;
            this.menuAeropuertos = new JPopupMenu();
            
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
            
            // Botones con efectos de hover mejorados y funcionalidad original
            btnVer = crearBotonAnimado("/RecursosGraficos/imagenes/ojo.png", "Ver detalles", e -> {
                String serie = (String) table.getValueAt(editingRow, 0);
                String modelo = (String) table.getValueAt(editingRow, 1);
                String marca = (String) table.getValueAt(editingRow, 2);

                panelPadre.actualizarEstadisticas();
                panelPadre.setEquipoVer(almacen.buscarEquipo(serie, modelo, marca));
                panelPadre.getTabbed().setSelectedIndex(8);
                fireEditingStopped();
            });
            
            btnEdit = crearBotonAnimado("/RecursosGraficos/imagenes/editar.png", "Editar", e -> {
                String serie = (String) table.getValueAt(editingRow, 0);
                String modelo = (String) table.getValueAt(editingRow, 1);
                String marca = (String) table.getValueAt(editingRow, 2);
                String marbete = (String) table.getValueAt(editingRow, 3);

                Equipo equipo = almacen.buscarEquipo(serie, modelo, marca, marbete);
                panelPadre.getTabbed().getEditarEq().setEquipo(equipo);
                panelPadre.getTabbed().setSelectedIndex(9);
                fireEditingStopped();
            });
            
            btnDel = crearBotonAnimado("/RecursosGraficos/imagenes/eliminar.png", "Eliminar", e -> {
                mostrarConfirmacionEliminacion();
              panelPadre.onDataChanges(true);
              refrescar();
            });
            
            btnSend = crearBotonAnimado("/RecursosGraficos/imagenes/enviar.png", "Mover a aeropuerto", e -> {
                String serie = (String) table.getValueAt(editingRow, 0);
                if (serie == null || serie.isEmpty() || serie.equals("Sin asignar")) {
                    JOptionPane.showMessageDialog(null, "Debe asignar el número de serie para poder mover a un aeropuerto",
                            "Advertencia!!", JOptionPane.ERROR_MESSAGE);
                    
                    
                    
                } else {
                    mostrarMenuAeropuertos(btnSend, editingRow);
                   
                }
                fireEditingStopped();
            });
            
            panel.add(btnVer);
            panel.add(btnEdit);
            panel.add(btnDel);
            panel.add(btnSend);
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
                "¿Eliminar este equipo del almacén general?", DialogoConfirmacion.TipoMensaje.MODIFICACION
            );
            
            if (dialogo.mostrar()) {
                String serie = (String) table.getValueAt(editingRow, 0);
                almacen.getDao().eliminarEquipo(serie);
                panelPadre.onDataChanges(true);
                refrescar();
            }
        }
        
        private void mostrarMenuAeropuertos(Component invoker, int row) {
            if (almacen == null || almacen.getLstAeropuertos() == null || almacen.getLstAeropuertos().isEmpty()) {
                JOptionPane.showMessageDialog(table, 
                    "No hay aeropuertos disponibles", 
                    "Error", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            menuAeropuertos.removeAll();
            
            JLabel titulo = new JLabel("Mover a");
            menuAeropuertos.setBackground(COLOR_ENCABEZADO);
            menuAeropuertos.setPreferredSize(new Dimension(150, 200));
            
            titulo.setFont(new Font("Arial", Font.BOLD, 14));
            titulo.setForeground(Color.white);
            titulo.setBackground(COLOR_ENCABEZADO);
            titulo.setPreferredSize(new Dimension(100, 40));
            titulo.setOpaque(true);
            menuAeropuertos.add(titulo);

            menuAeropuertos.addSeparator();
           
            
            for (Aeropuerto aero : almacen.getLstAeropuertos()) {
                JMenuItem menuItem = new JMenuItem(aero.getNombre());
                menuItem.setFont(new Font("Arial", Font.PLAIN, 12));
                menuItem.setForeground(Color.black);
                menuItem.setBackground(Color.WHITE);
                menuItem.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                menuItem.setOpaque(true);
                menuItem.addActionListener(ev -> {
                    String serie = (String) table.getValueAt(row, 0);
                    String modelo = (String) table.getValueAt(row, 1);
                    String marca = (String) table.getValueAt(row, 2);
                    
                    Equipo eq =(Equipo)panelPadre.getAlmacenG().buscarEquipo(serie, modelo, marca);
                     
                     EquipoAeropuerto equipo= new EquipoAeropuerto(aero.getSizeListado()+1,eq.getSerie(), eq.getModelo(),
                    		 eq.getMarca(),
                    		 eq.getDescripcion(), 
                    		 eq.getMarbete(),
                    		 eq.getAccesorios(),
                    		 "SIN INSTALAR");
                
                    aero.getEquipos().add(equipo);
                    panelPadre.getAlmacenG().getLstEGeneral().remove(eq);
                    aero.getDaoEquiposAero().insertar(aero, (equipo));
                    panelPadre.getAlmacenG().getDao().eliminarEquipo(serie);
                    panelPadre.onDataChanges(true);
                    refrescar();
                
                    
                  
                    
                   
                        
                });
                menuAeropuertos.add(menuItem);
            }
            
            menuAeropuertos.show(invoker, 0, invoker.getHeight());
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
    
    public void setAlmacen(AlmacenGeneral almacen) {
        this.almacen = almacen;
        refrescar();
    }
}
