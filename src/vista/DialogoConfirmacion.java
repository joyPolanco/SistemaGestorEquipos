package vista;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class DialogoConfirmacion extends JDialog {

    public enum TipoMensaje {
        MODIFICACION, ERROR, INFORMACION
    }

    private boolean confirmado = false;

    public DialogoConfirmacion(Component parent, String mensaje, TipoMensaje tipo) {
        super((Frame) SwingUtilities.getWindowAncestor(parent), true);
        configurarUI(mensaje, tipo);
    }

    private void configurarUI(String mensaje, TipoMensaje tipo) {
        setUndecorated(true);

        if (tipo == TipoMensaje.INFORMACION) {
            setSize(420, 200); // Más grande para información
        } else {
            setSize(350, 180);
        }

        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        JPanel content = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (tipo == TipoMensaje.ERROR) {
                    g2.setColor(new Color(255, 228, 225)); // fondo rosado claro para error
                } else {
                    g2.setColor(Color.WHITE);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.setColor(new Color(0, 0, 0, 30));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
            }
        };
        content.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        String titulo;
        switch (tipo) {
            case ERROR:
                titulo = "ERROR: ";
                break;
            case INFORMACION:
                titulo = "INFORMACIÓN: ";
                break;
            default:
                titulo = "";
        }
        JLabel lblMensaje = new JLabel("<html><div style='text-align: center;'>" + titulo + mensaje + "</div></html>", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblMensaje.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBotones.setOpaque(false);

        if (tipo == TipoMensaje.MODIFICACION) {
            JButton btnSi = crearBoton("Confirmar", new Color(46, 125, 50), e -> {
                confirmado = true;
                dispose();
            });

            JButton btnNo = crearBoton("Cancelar", new Color(211, 47, 47), e -> {
                confirmado = false;
                dispose();
            });

            panelBotones.add(btnSi);
            panelBotones.add(btnNo);

        } else if (tipo == TipoMensaje.ERROR) {
            JButton btnCerrar = crearBoton("Cerrar", new Color(211, 47, 47), e -> {
                confirmado = false;
                dispose();
            });
            panelBotones.add(btnCerrar);

        } else if (tipo == TipoMensaje.INFORMACION) {
            JButton btnAceptar = crearBoton("Aceptar", new Color(25, 118, 210), e -> {
                confirmado = true;
                dispose();
            });
            panelBotones.add(btnAceptar);
        }

        content.add(lblMensaje, BorderLayout.CENTER);
        content.add(panelBotones, BorderLayout.SOUTH);
        add(content);
    }

    private JButton crearBoton(String texto, Color color, ActionListener listener) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isRollover()) {
                    g2.setColor(color.brighter());
                } else if (getModel().isPressed()) {
                    g2.setColor(color.darker());
                } else {
                    g2.setColor(color);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                super.paintComponent(g);
            }
        };

        btn.setPreferredSize(new Dimension(100, 35));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.addActionListener(listener);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        return btn;
    }

    public boolean mostrar() {
        setVisible(true);
        return confirmado;
    }
}
