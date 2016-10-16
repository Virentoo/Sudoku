package pl.Barszczewski.Kacper;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MyJMenuBar extends JMenuBar {

	public MyJMenuBar() {
		add(autorMenu());
	}

	private JMenu autorMenu() {
		JMenu menu = new JMenu("Autor: Kacper Barszczewski");
		menu.setEnabled(false);
		return menu;
	}
}