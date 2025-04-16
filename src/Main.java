public class Main {

	private static byte[][] board;

	private static final char[] letterMap = {'A', 'B', 'C', 'D','E','F','G','H'};


	public static byte[][] getBoard() {
		return board;
	}

	public static char coordToLetter(int coord){
		if(coord < letterMap.length)
			return letterMap[coord];

		return ' ';
	}

   public static int letterVal(char c) {
      c = ("" + c).toUpperCase().charAt(0);
      for(int i = 0; i < letterMap.length; i++) {
      	if(letterMap[i] == c)
      		return i;
      }
      return -1;
   }

	public static void drawBoard(){
		boolean swap = false;
		System.out.print("X ");
		for(int i = 0; i < board.length; i++) System.out.print(i);
		System.out.print(" Y\n");

		for(int i = 0; i < board.length && i < letterMap.length; i++){
			System.out.print(letterMap[i] + " ");
			for(int j = 0; j < board[i].length; j++)
			{
				if(board[i][j] == 2)
					System.out.print('O');
				else if (board[i][j] == 1)
					System.out.print('X');
				else if (board[i][j] == -1)
					System.out.print("𝕏");
				else if (board[i][j] == -2)
					System.out.print('0');
				else
					System.out.print(swap ? '█' : '░');
				swap = !swap;
			}
			swap = !swap;

			System.out.println(" " + i);
		}
		System.out.print("  ");

      for(int i = 1; i <= board.length; i++) System.out.print(i);
		
	  System.out.println();
	}

	public static void main(String[] args) {        
		board = new byte[8][8];
		boolean swap = true;

		Player[]  players = new Player[2];

		for(int i = 0; i < players.length; i++)
		{
			players[i] = Player.playerFactory(i+1);
		}

		for(int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (swap) {                                       
					swap = false;
					continue;
				}

				if (i < 3)
					board[i][j] = 2;
				else if (i > 4)
					board[i][j] = 1;            
				swap = true;
			}
			swap = !swap;
		}		
		
		// board[3][2] = 1;

        int turn = 0;
		while(true /*canPlay()*/)
		{
			drawBoard();
         	players[turn].makeMove();

         	turn++;
			if(turn > 1)
            	turn = 0;
		}
	}
}