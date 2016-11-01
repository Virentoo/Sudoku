package pl.Barszczewski.Kacper;

import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MyFrame();
			}
		});
	}
}



class MyFrame extends JFrame {
	
	Insets ins;
	
	final int WIDTH = 400,
			HEIGHT = 610;
	
	
	
	public MyFrame() {
		setDefaultSettings();
		
		MyJMenuBar menuBar = new MyJMenuBar();
		this.setJMenuBar(menuBar);
		
		JPanel basicPanel = new BasicPanel();
		basicPanel.setBounds(0, 0, WIDTH, HEIGHT);
		add(basicPanel);
		
		this.setFocusable(true);
		setVisible(true);
	}

	private void setDefaultSettings() {
		this.setLayout();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		this.setTitle("Sudoku");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setLayout() {
		this.setLayout(null);
		ins = this.getInsets();
	}
}