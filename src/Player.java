import java.util.*;

public abstract class Player {
	private final byte team;
	private String error;

	public static Player playerFactory(int team) {
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		while (true){
			System.out.print(String.format("Player %d mode ([%c]uman, [%c]omputer): ", team, team == 1 ? 'H' : 'h', team == 2 ? 'C' : 'c'));
			String modeSel = s.nextLine().trim();
			if(modeSel.equalsIgnoreCase("h") || modeSel.equalsIgnoreCase("human") || (team ==1 && modeSel.isEmpty()) ){
				return new Human((byte)team);
			} else if(modeSel.equalsIgnoreCase("c") || modeSel.equalsIgnoreCase("computer") || (team ==2 && modeSel.isEmpty())){
				return new Computer((byte)team);
			}
			System.out.println("Try again.");
		}		
	}

	public Player(byte team) {
		this.team = team;			
	}

	public byte getTeam() {
		return team;
	}

	public void setError(String msg){
		error = msg;
	}

	public String getError(){
		return error;
	}

	public abstract void makeMove();	

	public ArrayList<Vector2[]> checkAttacks() {
		return findMoves(true);
	}

	public boolean chainable(Vector2 to) {
		return false;
	}

	public boolean isAttack(Vector2 from, Vector2 to) {
		int xDist = Math.max(from.getX(), to.getX()) - Math.min(from.getX(), to.getX());
		int yDist = Math.max(from.getY(), to.getY()) - Math.min(from.getY(), to.getY());

		return xDist == yDist && xDist == 2;
	}

	public void movePiece(Vector2 from, Vector2 to) {
		Main.getBoard()[to.getY()][to.getX()] = Main.getBoard()[from.getY()][from.getX()];
		Main.getBoard()[from.getY()][from.getX()] = 0;

		if (isAttack(from, to))
			Main.getBoard()[(from.getY() + to.getY())/2][(from.getX() + to.getX())/2] = 0;

		System.out.println(to.valueOf(Main.getBoard()));
		if(atEdge(to) && to.valueOf(Main.getBoard()) > 0) {
			Main.getBoard()[to.getY()][to.getX()] *= -1;
			// System.out.println("Upgrading piece " + to);
		}
	}

	public boolean atEdge(Vector2 piece) {
		return piece.getY() <= 0 || piece.getY() >= 7;
	}

	public ArrayList<Vector2[]> findAnyMoves(boolean onlyAttacks) {
		return findMoves(false);
	}

	public ArrayList<Vector2[]> findMoves(boolean onlyAttacks) {
		ArrayList<Vector2[]> found = new ArrayList<>();

		int otherTeam = team == 1 ? 2 : 1;

		for(int i = 0; i < Main.getBoard().length; i++){
			for(int j = 0; j < Main.getBoard()[i].length; j++) {
				if(Math.abs(Main.getBoard()[i][j]) != team)
					continue;

				for(int k = -1; k <= 1; k += 2) {					
					for(int l = -1; l <= 1; l += 2)  {							
						if((i + k > 0 && i + k < Main.getBoard().length)
							&& (j + l > 0 && j + l < Main.getBoard()[i + k].length)) {
							int step;
							if(Math.abs(Main.getBoard()[i+k][j+l]) == otherTeam){
								step = 2;
							} else if (!onlyAttacks && Main.getBoard()[i+k][j+l] == 0){
								step = 1;
							}else
								continue;

							Vector2 from = new Vector2(j,i);
							Vector2 to = new Vector2(j + l*step, i + k*step);
	
							if(validateMove(from, to))
								found.add(new Vector2[] {from, to});
						}
					}
				}
				// if(i - 2 > 0 && j - 2 > 0 && Main.getBoard()[i-2][j-2] == otherTeam)					
			}		
		}

		return found;
	}

	public boolean validateMove(Vector2 from, Vector2 to) {

		if(from.valueOf(Main.getBoard()) == 0) {
			setError("You can't move an empty space.");
			return false;
		}

		if(Math.abs(from.valueOf(Main.getBoard())) != team) {
			setError("You can't move that piece.");
			return false;
		}

		if(from.valueOf(Main.getBoard()) > 0 && ((team == 1 && to.getY() - from.getY() > 0) || (team == 2 && to.getY() - from.getY() < 0))) {
			setError("Only kings can move backwards.");
			return false;
		}		
		
		if(to.getX() == from.getX() || to.getY() == from.getY()){
			setError("You have to move diagonally.");
			return false;			
		}
		
		if(to.valueOf(Main.getBoard()) != 0) {
			setError("There is a piece obstructing that spot.");
			return false;		
		}

		int xDist = Math.max(from.getX(), to.getX()) - Math.min(from.getX(), to.getX());
		int yDist = Math.max(from.getY(), to.getY()) - Math.min(from.getY(), to.getY());

		if (xDist != yDist) {
			setError("Move not a square.");
			return false;
		}
		if(xDist > 2) {
			setError("Move too far away.");
			return false;
		}
		
		int midX = (from.getX() + to.getX())/2, midY = (from.getY() + to.getY())/2;

		// check if there is a killable piece when is attack
		if (xDist == 2 && (Main.getBoard()[midY][midX] == 0 || Math.abs(Main.getBoard()[midY][midX]) == team)) {
			setError("There is noone to kill.");
			return false;
		}

		return true;
	}
}
