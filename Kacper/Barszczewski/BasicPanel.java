package pl.Barszczewski.Kacper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BasicPanel extends JPanel {

	SudokuBox[] sudokuBoxes = new SudokuBox[9];
	int[][] sudokuPlansza = new int[9][9];
	int[][] sudokuRoz = new int[9][9];
	
	public int seconds, minutes;

	boolean isSudokuNeedCheck = false;
	
	Insets ins;
	
	int level = 1;
	
	SudokuAlgorithm sudoku;
	
	MyTimerTask timerTask = new MyTimerTask();
	Timer timer = new Timer();
	
	MyJButton ssb, rozB ;
	
	public BasicPanel() {
		this.setLayout();
		
		add(sudokuPanel());
		add(nextSudokuBoxPanel());
		add(rozwiazanieButton());
		add(sprawdzenieButton());
		add(stopStartTime());
		add(resetButton());
		add(levelSelecting());
		add(levelLabel());
		
		
		timer.scheduleAtFixedRate(timerTask, 0, 100);
		timer.scheduleAtFixedRate(cisirTask(), 1, 100);
		startRepaintingWindow();
		
		
		createNewSudoku();
	}


	private JLabel levelLabel() {
		JLabel label = new JLabel("Poziom: ");
		label.setFont(new Font(null, Font.ROMAN_BASELINE, 12));
		label.setBounds(40, 510, 50, 30);
		return label;
	}


	private JComboBox<String> levelSelecting() {
		String[] levels = new String[]{"Niski", "Średni", "Wysoki"};
		JComboBox<String> cbox = new JComboBox<String>(levels);
		cbox.addActionListener(new ActionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cbox = (JComboBox<String>) e.getSource();
				level = cbox.getSelectedIndex();
			}
			
		});
		cbox.setSelectedIndex(1);
		cbox.setBounds(100, 510, 240, 27);
		return cbox;
	}
	
	private TimerTask cisirTask() {
		return new TimerTask(){
			@Override
			public void run() {
				if(isSudokuNeedCheck && sudoku.good()) {
					sudokuPlansza = sudoku.getSudoku();
					sudokuRoz = sudoku.getAnswer();
					uzupelnijGUI(sudokuPlansza);
					isSudokuNeedCheck = false;
				}
			}
		};
	}


	private MyJButton resetButton() {
		MyJButton resetButton = new MyJButton("Reset");
		resetButton.setBounds(50, 5, 80, 25);
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timerTask.reset();
			}
		});
		return resetButton;
	}

	private MyJButton stopStartTime() {
		ssb = new MyJButton("Start");
		ssb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MyJButton b = (MyJButton) e.getSource();
				if(b.getText().equals("Start")){
					b.setText("Stop");
					timerTask.start();
				}
				else{
					b.setText("Start");
					timerTask.stop();
				}
			}
		});
		ssb.setBounds(250, 5, 80, 25);
		return ssb;
	}

	private void startRepaintingWindow() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				repaint();
			}
			
		}, 0, 100);
	}

	private MyJButton sprawdzenieButton() {
		MyJButton sprB = new MyJButton("Sprawdz");
		sprB.setBounds(40, 460, 300, 40);
		sprB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				if(czySudokuRozwiazanePoprawnie(sudokuRoz, false)) {
					timerTask.stop();
					ssb.setText("Start");
					JOptionPane.showMessageDialog(null, "Sudoku rozwiązne poprawnie :) Czas: " + timerTask.getTime());
				}
			}
		});
		return sprB;
	}
	
	private MyJButton rozwiazanieButton() {
		rozB = new MyJButton("Rozwiązanie");
		rozB.setBounds(40, 410, 300, 40);
		
		rozB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((MyJButton) e.getSource()).setEnabled(false);
				czySudokuRozwiazanePoprawnie(sudokuRoz, true);
			}
		});
		return rozB;
	}

	private MyJButton nextSudokuBoxPanel() {
		MyJButton nextB = new MyJButton("Następna plansza");
		nextB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				rozB.setEnabled(true);
				createNewSudoku();
				
				timerTask.reset();
				timerTask.sleep(1000);
				timerTask.start();
				
				
				ssb.setText("Stop");
			}
		});
		nextB.setBounds(40, 360, 300, 40);
		return nextB;
	}
	
	private boolean czySudokuRozwiazanePoprawnie(int[][] sudoku, boolean czyWyspisac) {
		int i = 0;
		boolean czyPoprawnie = true;
		
		for(int n = 0; n < 9; n++) {
			if(!sudokuBoxes[n].czyPoprawnie((int)(n/3), i, sudoku, czyWyspisac)){
				czyPoprawnie = false;
			}
			i++;
			if(i == 3) i = 0;
		}
		
		if(czyPoprawnie){
			return true;
		} else {
			return false;
		}
	}
	
	private void createNewSudoku() {
		sudoku = new SudokuAlgorithm(level);
		sudoku.createSudoku();
		isSudokuNeedCheck = true;
	}
	
	private void uzupelnijGUI(int[][] sudoku) {
		int i = 0;
		for(int n = 0; n < 9; n++) {
			sudokuBoxes[n].uzupelnij((int)(n/3), i, sudoku);
			i++;
			if(i == 3) i = 0;
		}
	}

	private void setLayout() {
		this.setLayout(null);
		ins = this.getInsets();
	}

	private JPanel sudokuPanel() {
		JSudokuPanel sudokuP = new JSudokuPanel();

		
		sudokuP.setBounds(40, 40, 300, 300);
		sudokuP.setLayout(new GridLayout(3,3));
		
		for(int n = 0; n < 9; n++){
				sudokuBoxes[n] = new SudokuBox();
				sudokuP.add(sudokuBoxes[n]);
		}
		
		return sudokuP;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(245, 245, 245));
		g.fillRect(0, 0, getWidth() - 1, getHeight()- 1);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		g2.setFont(new Font(null,Font.BOLD,14));
		g2.drawString("Czas: " + timerTask.getTime() , 145, 23);
	}
}

class SudokuBox extends JPanel {
	
	final int WIDTH = 1,
			HEIGHT = 1;
	
	MyJTextPanel[][] liters = new MyJTextPanel[3][3];
	
	SudokuBox() {
		setLayout(new GridLayout(3,3));
		
		for(int n = 0; n < 3; n++)
			for(int n2 = 0; n2 < 3; n2++){
				liters[n][n2] = new MyJTextPanel(WIDTH, HEIGHT);
				this.add(liters[n][n2]);
			}
		this.setPreferredSize(new Dimension(50,50));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
		this.setBackground(Color.BLACK);
	}

	public boolean czyPoprawnie(int y, int x, int[][] sudoku, boolean czyWypisac) {
		boolean czyP = true;
		x *= 3;
		y *= 3;
		for(int n = 0; n < 3; n++) {
			for(int n2 = 0; n2 < 3; n2++) {
				if(liters[n][n2].getText().equalsIgnoreCase("") || Integer.parseInt(liters[n][n2].getText()) != sudoku[y + n][n2 + x]){
					czyP = false;
					liters[n][n2].wrong();
				} else liters[n][n2].correct();
				if(czyWypisac){
					liters[n][n2].setText(String.valueOf(sudoku[y + n][n2 + x]));
				}
			}
		}
		return czyP;
	}

	public void uzupelnij(int y, int x, int[][] sudokuPlansza) {
		x *= 3;
		y *= 3;
		for(int n = 0; n < 3; n++) {
			for(int n2 = 0; n2 < 3; n2++) {
				liters[n][n2].setText(String.valueOf(sudokuPlansza[y + n][n2 + x]));
				if(sudokuPlansza[y + n][n2 + x] == 0 ) liters[n][n2].setEnabled(true);
				else liters[n][n2].setEnabled(false);
			}
		}
	}

	
	
}