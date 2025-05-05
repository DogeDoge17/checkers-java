import java.util.*;

public class Computer extends Player {    
    public Computer(byte team) {
        super(team);
    }

    @Override
    public void makeMove() {
        Vector2 lastPiece = null;
        ArrayList<Vector2[]> moves = null;        
        boolean singleRound = false;
        Main.drawBoard();

        

        //System.out.println("Thinking...");
        //try{ Thread.sleep(100); } catch (InterruptedException e) { }

        int thunks = (int)(Math.random() * 5) + 1;

        while(thunks-- > 0) {
            for(int dots = 0; dots <= 3; dots++) {
                System.out.printf("Thinking%s   \r", new String(new char[dots]).replace("\0", "."));
                try{ Thread.sleep(100); } catch (InterruptedException e) { }
            }
        }
        System.out.println();
        do {
            moves = checkAttacks(lastPiece);
            if(moves.isEmpty()) {   
                if(lastPiece != null)
                    break;

                singleRound = true;
                moves = findMoves();                        
            } 

            Vector2[] move = moves.get((int)(Math.random() * moves.size()));

            movePiece(move[0], move[1]);
            System.out.printf("%s => %s%n", move[0], move[1]);              
            lastPiece = move[1];
        } while(!singleRound);
    }

    public String toString(){
        return String.format("Computer %s (%s)", getTeam(), super.toString());
    }
}
