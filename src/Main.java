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
					System.out.print("ð�•�");
				else if (board[i][j] == -2)
					System.out.print('0');
				else
					//System.out.print(swap ? "" : ' ');
					 System.out.print(swap ? " " : "█");
				swap = !swap;
			}
			swap = !swap;

			System.out.println(" " + i);
	  }
	  System.out.print("  ");

      for(int i = 1; i <= board.length; i++) System.out.print(i);
		
	  System.out.println();
	}

	private static byte checkWinner(Player[] players) {
		int p1Moves = players[0].findMoves().size();
		int p2Moves = players[1].findMoves().size();
		if(p1Moves > 0 && p2Moves > 0)
			return 0;
		else if(p1Moves > 0 && p2Moves <= 0)
			return 1;
		else if (p1Moves <= 0 && p2Moves > 0)
			return 2;
		else
			return -1;		
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
      byte winner = 0;
		while((winner = checkWinner(players)) == 0)
		{
         players[turn].makeMove();

         turn++;
			if(turn > 1)
            turn = 0;
		}

		switch (winner) {		
		case 1:
		case 2:
			System.out.printf("Player %d won the game with %d pieces left.%n", --winner, 100);
			break;
		default:
			System.out.println("The game ended in a stalemate.");
			break;
		}
	}
}