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

    private Vector2[] pollInput() {
        Scanner s = new Scanner(System.in);
    	Vector2[] movement = new Vector2[2];
    	boolean valid = true;
        do{
            if(!valid){
                System.out.println(getError());
                valid = true;
            }

            System.out.print("Make move: ");
            String[] moves;
            String raw = s.nextLine().trim().toLowerCase();
            if(raw.length() == 4) {
                moves = new String[2];
                moves[0] = raw.substring(0,2);
                moves[1] = raw.substring(2,4);
            }
            else
                moves = raw.split("[,.\\s]");

            if(moves.length < 2) {
                setError("Enter two coords.");
                valid = false;
                continue;
            }

            if((movement[0] = stringToPoint(moves[0])) == null){
                setError("Invalid move syntax (" + moves[0] + ")");
                valid = false;
                continue;
            }

            if((movement[1] = stringToPoint(moves[1])) == null) {
                setError("Invalid move syntax (" + moves[1] + ")");
                valid = false;
                continue;
            }

        }while(!(valid && (valid = validateMove(movement[0], movement[1]))));
        return movement;
    }
    
    private ArrayList<Vector2[]> promptAttacks(Vector2 lastPiece) {
        ArrayList<Vector2[]> attacks = null;
        if(!(attacks = checkAttacks(lastPiece)).isEmpty()) {
            System.out.printf("You have %d attack%s you must make.%n", attacks.size(), attacks.size() == 1 ? "" : "s");
            
            for(int i = 0; i < attacks.size(); i++) {                
                System.out.printf("%s => %s%s", attacks.get(i)[0], attacks.get(i)[1], i == attacks.size()-1 ? ""  : ", ");              
            }            
            System.out.println();
        }   
        return attacks;
    }

    @Override
    public void makeMove() {
             
        Vector2 from;
        Vector2 to;
        Vector2[] results;
        ArrayList<Vector2[]> attacks;
        boolean singleRound = false;
        Vector2 lastPiece;
        boolean firstTime = true;
        Main.drawBoard();
        attacks = promptAttacks(null);
        do{
            if(!firstTime){
                Main.drawBoard();
                System.out.print(getError() + (!getError().isEmpty() ? "\n" : ""));
            }

            results = pollInput();

            if(!attacks.isEmpty()) {
                boolean isAttack = false;
                for(Vector2[] attack : attacks) {
                    if((isAttack = (attack[0].equals(results[0]) && attack[1].equals(results[1])))) {                    
                        break;            
                    }
                }
                if(!isAttack) {
                    setError("You MUST make an attack.");
                    firstTime = false;                
                    continue;
                }                 
            }else{
                singleRound = true;            
            }

            from = results[0];
            to = results[1];    
            movePiece(from, to);
            lastPiece = to;

            if(!singleRound)
                attacks = promptAttacks(lastPiece);
            if(attacks.isEmpty())
                break;

            firstTime = false;
        } while(!singleRound);            
    }    

    public String toString(){
        return String.format("Player %s (%s)", getTeam(), super.toString());
    }
}