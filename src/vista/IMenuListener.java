package vista;


import java.awt.event.MouseEvent;

public interface IMenuListener {
    void onMenuButtonClicked(MouseEvent e);

	void onMenuToggle(boolean menuExpandido);
}