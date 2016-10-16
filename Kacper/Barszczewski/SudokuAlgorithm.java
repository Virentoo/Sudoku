package pl.Barszczewski.Kacper;

import java.security.SecureRandom;

public class SudokuAlgorithm {

    int[][] tab = new int[9][9];
    int[][] curr = new int[9][9];
    SecureRandom rand = new SecureRandom();
    boolean czyUdaloSie = false;

    public SudokuAlgorithm() {
    	
    }
    
    public void createSudoku() {
	        createRandomSudoku();
	
	        do{
		        for(int n = 0; n < 9; n++) {
		        	for(int n2 = 0; n2 < 9; n2++) {
		        		curr[n][n2] = tab[n][n2];
		        	}
		        }
		        
		        if (!solve(0, 0)) {
		            czyUdaloSie = false;
		        } else czyUdaloSie = true;
	
		        for(int n = 0; n < 9; n++) {
		        	for(int n2 = 0; n2 < 9; n2++) {
		        		tab[n][n2] = curr[n][n2];
		        	}
		        }
		        
		        finishSudoku();
	        }while(!czyUdaloSie);
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
            i = rand.nextInt(5) + 4;
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

	private void createRandomSudoku() {
        int i;
        int x;
        int l = rand.nextInt(9) + 1;
        int[] tabL;


        //Wypelnij randomowo tablice
        for(int n = 0; n < 9; n++) {
            i = 9;
            x = rand.nextInt(9);
            tabL = new int[9];

            for(int i2 = 0; i2 < i; i2++) {
                while(czyLiczbaPowtorzona(tabL, l)){
                    l = rand.nextInt(9) + 1;
                }

                tab[n][x] = l;
                tabL[i2] = l;
            }
        }

        //Usuwanie liczb powtorzonych!
        for(x = 0; x < 9; x++) {
            tabL = new int[9];

            for(int y = 0; y < 9; y++) {
                if(tab[y][x] != 0) {
                    if(czyLiczbaPowtorzona(tabL, tab[y][x])) {
                        tab[y][x] = 0;
                    } else {
                        tabL[y] = tab[y][x];
                    }
                }
            }
        }

        //Poprawianie kwadratów
        for(int n = 0; n < 3; n++) {
            for(int n2 = 0; n2 < 3; n2++){
                poprawKwadrat(n, n2);
            }
        }
    }

    private void poprawKwadrat(int y, int x) {
        int[] tabL = new int[9];
        int i = 0;
        int px;
        int py;
        for(py = y*3; py < y * 3 + 3; py++) {
            for(px = x * 3; px < x * 3 + 3; px++) {
                if(tab[py][px] != 0) {
                    if(czyLiczbaPowtorzona(tabL, tab[py][px])) {
                        tab[py][px] = 0;
                    } else {
                        tabL[i] = tab[py][px];
                        i++;
                    }
                }
            }
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


    private boolean can_insert(int x, int y, int value) {
        for(int i = 0; i < 9; i++) {
            if (value == curr[x][i] || value == curr[i][y] ||
                    value == curr[x/3*3+i%3][y/3*3+i/3]) return false;
        } return true;
    }

    private boolean next(int x, int y) {
        if (x == 8 && y == 8) return true;
        else if (x == 8) return solve(0, y + 1);
        else return solve(x + 1, y);
    }

    private boolean solve(int x, int y) {
        if (tab[x][y] == 0) {
            for(int i = 1; i <= 9; i++) {
                if (can_insert(x, y, i)) {
                    curr[x][y] = i;
                    if (next(x, y)) return true;
                }
            } curr[x][y] = 0; return false;
        } return next(x, y);
    }
}
