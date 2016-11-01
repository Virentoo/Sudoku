package pl.Barszczewski.Kacper;

import java.security.SecureRandom;

public class SudokuAlgorithm {

    int[][] tab = new int[9][9];
    int[][] curr = new int[9][9];
    SecureRandom rand = new SecureRandom();
    boolean czyUdaloSie = false;
    
    int level;
    
    Thread run;

    public SudokuAlgorithm(int level) {
    	this.level = level;
    }
    
    public void createSudoku() {
    	createStandardSudoku();
    	
		run = new Thread(new Runnable() {
			@Override
			public void run() {
				
				for(int n2 = 0; n2 < 3; n2++) {
					
					//Zmien male kolumny
					for(int n = 0; n < 3; n++) exchangeSmall(-1, n);
					//Zmien duze kolumny
					for(int n = 0; n < 3; n++) exchangeBig(0);
					//Zmien male linie
					for(int n = 0; n < 3; n++) exchangeSmall(n, -1);
					//Zmien duze linie
					for(int n = 0; n < 3; n++) exchangeBig(1);
					
					for(int y = 0; y < 9; y++) {
						for(int x = 0; x < 9; x++) {
							curr[y][x] = tab[y][x];
					    }
					}
					exchangeGrid(rand.nextInt(4));
					
				}
				
				for(int n = 0; n < 9; n++) {
					for(int n2 = 0; n2 < 9; n2++) {
						curr[n][n2] = tab[n][n2];
				    }
				}
				
				finishSudoku();
				
				czyUdaloSie = true;
			}
		});
		
		run.start();
		        
    }
    

	protected void exchangeGrid(int i) {
		int nx = 0 , ny = 0;
		if(i == 0) return;
		else if(i == 1){ nx = 8; ny = 0; }
		else if(i == 2){ nx = 8; ny = 8; }
		else { nx = 0; ny = 8;}
			for(int y = 0; y < 9; y++) {
				for(int x = 0; x < 9; x++) {
					tab[y][x] = curr[Math.abs(nx - x)][Math.abs(ny - y)];
				}
			}
		
	}

	protected void exchangeBig(int i) {
		int p1, p2;
		do {
			p1 = rand.nextInt(3);
			p2 = rand.nextInt(3);
		}while (p1 == p2);
		if(i == 0) changBigColumn(p1,p2);
		else changeBigLine(p1,p2);
	}

	private void changeBigLine(int y1, int y2) {
		int p;
		y1 *= 3;
		y2 *= 3;
		for(int n = 0; n < 3; n++) {
			for(int x = 0; x < 9; x++) {
				p = tab[y1 + n][x];
				tab[y1 + n][x] = tab[y2 + n][x];
				tab[y2 + n][x] = p;
			}
		}
	}

	private void changBigColumn(int x1, int x2) {
		int lTemp;
		x1 *= 3;
		x2 *= 3;
		for(int n = 0; n < 3; n++) {
			for(int y = 0; y < 9; y++) {
				lTemp = tab[y][x1 + n];
				tab[y][x1 + n] = tab[y][x2 + n];
				tab[y][x2 + n] = lTemp;
			}
		}
	}
	
	protected void exchangeSmall(int y, int x) {
		int x1;
		int x2;
		do{
			x1 = rand.nextInt(3);
			x2 = rand.nextInt(3);
		}while(x1 == x2);
		if(y == -1) changeSmallColumn(x, x1, x2);
		else changeSmallLine(y, x1, x2);
	}

	private void changeSmallLine(int y, int y1, int y2) {
		int p;
		y1 += 3 * y;
		y2 += 3 * y;
		for(int n = 0; n < 9; n++) {
			p = tab[y2][n];
			tab[y2][n] = tab[y1][n];
			tab[y1][n] = p;
		}
	}

	private void changeSmallColumn(int x, int x1, int x2) {
		int p;
		x1 += 3 * x;
		x2 += 3 * x;
		for(int n = 0; n < 9; n++) {
			p = tab[n][x1];
			tab[n][x1] = tab[n][x2];
			tab[n][x2] = p;
		}
	}
    
    public int[][] getSudoku(){
    	return tab;
    }
    
    public boolean good(){
    	return czyUdaloSie;
    }
    
    public int[][] getAnswer() {
    	return curr;
    }

    private void finishSudoku() {
        int[] uzyteX;
        int i, x;
        for(int y = 0; y < 9; y++){
            i = rand.nextInt(4) + 3 + level;
            uzyteX = fell(-1, i);

            for(int n = 0; n < i; n++) {
                x = rand.nextInt(9);
                if(czyLiczbaPowtorzona(uzyteX,x)) {
                    n--;
                } else {
                    tab[y][x] = 0;
                    uzyteX[n] = x;
                }
            }
        }
    }

    private int[] fell(int l, int i) {
		int[] a = new int[i];
		for(int n = 0; n < i; n++) a[n] = l;
		return a;
	}

	private void createStandardSudoku() {
		fillSmallCube(0,1);
		fillSmallCube(1,4);
		fillSmallCube(2,7);
		fillSmallCube(3,2);
		fillSmallCube(4,5);
		fillSmallCube(5,8);
		fillSmallCube(6,3);
		fillSmallCube(7,6);
		fillSmallCube(8,9);
    }

    private void fillSmallCube(int y, int liczba) {
		for(int x = 0; x < 9; x++) {
			tab[y][x] = liczba;
			if(liczba == 9) liczba = 0;
			liczba++;
		}
	}

    private boolean czyLiczbaPowtorzona(int[] tabL, int l) {
        for (int aTabL : tabL) {
            if (aTabL == l) return true;
        }
        return false;
    }

    public void wyswietl() {
        for(int n = 0; n < 9; n++) {
            for(int n2 = 0; n2 < 9; n2++) {
                System.out.print(tab[n][n2] + " : ");
            }
            System.out.println();
        }
    }

	public void wyswietlRoz() {
        for(int n = 0; n < 9; n++) {
            for(int n2 = 0; n2 < 9; n2++) {
                System.out.print(curr[n][n2] + " : ");
            }
            System.out.println();
        }
	}
}
