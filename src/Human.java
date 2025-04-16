import java.util.*;

public class Human extends Player {
    public Human(int team) {
        super((byte)team);
    }

    private Vector2 stringToPoint(String raw) {
        if(raw.length() < 2)
            return null; 

    	if(raw.charAt(0) >= 'a' && raw.charAt(0) <= 'h' && raw.charAt(1) >= '1' && raw.charAt(1) <= '8') 
            return new Vector2(raw.charAt(1) - '0'-1, Main.letterVal(raw.charAt(0)));
         else if (raw.charAt(1) >= 'a' && raw.charAt(1) <= 'h' && raw.charAt(0) >= '1' && raw.charAt(0) <= '8')
            return new Vector2(raw.charAt(0) - '0'-1, Main.letterVal(raw.charAt(1)));
        else  {
            setError("Bad coord. (" + raw + ")");
            return null;
        }
    }

    @Override
    public void makeMove() {
        Scanner s = new Scanner(System.in);
        ArrayList<Vector2[]> attacks = null;
        if((attacks = checkAttacks()).size() > 0) {
            System.out.println(String.format("You have %d attacks you must make.", attacks.size()));
        }        
        Vector2 to = null;
        Vector2 from = null;
        boolean valid = true;
        do{
            if(!valid){
                System.out.println(getError());
                valid = true;
            }

            System.out.print("Make move: ");
            String[] moves;
            String raw = s.nextLine().trim();
            if(raw.length() == 4) {
                moves = new String[2];
                moves[0] = raw.substring(0,2);
                moves[1] = raw.substring(2,4);
            }
            else
                moves = raw.split("[,\\.\\s]");


            if(moves.length < 2) {
                setError("Enter two coords.");
                valid = false;
                continue;
            }

            if((from = stringToPoint(moves[0])) == null){
                setError("Invalid move syntax (" + moves[0] + ")");
                valid = false;
                continue;
            }

            if((to = stringToPoint(moves[1])) == null) {
                setError("Invalid move syntax (" + moves[1] + ")");
                valid = false;
                continue;
            }


            /* if(Math.abs(from.valueOf(Main.getBoard())) != getTeam() && from.valueOf(Main.getBoard()) != 0)  {
                System.out.println("You may not move an enemy piece.");
                continue;
            }else if (Math.abs(from.valueOf(Main.getBoard())) != getTeam()){
                System.out.println("Your selected piece is empty.");
            }

            if () {
                System.out.println("You may not move an enemy piece.");
            } */

            /* while(true) {
                System.out.print("Select piece: ");
                from = stringToPoint(s.nextLine());
                if (from != null)
                    break;
                System.out.println("Invalid move.");
            }

            while(true) {
                System.out.print("Move to: ");
                to = stringToPoint(s.nextLine());
                if (to != null)
                    break;
                System.out.println("Invalid move.");
            } */
        }while(!(valid && (valid = validateMove(from, to))));
        movePiece(from, to);

    }    
}
