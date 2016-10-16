package pl.Barszczewski.Kacper;

import java.awt.Graphics;
import javax.swing.JPanel;

public class JSudokuPanel extends JPanel {

	JSudokuPanel() {
		
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawRect(0, 0, 299, 299);
		g.drawRect(1, 1, 297, 297);
	}
}
