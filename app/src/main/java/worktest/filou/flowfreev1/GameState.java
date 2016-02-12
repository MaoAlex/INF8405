package worktest.filou.flowfreev1;

/**
 * Created by filou on 10/02/16.
 */
public class GameState {
    private int nbSquares = 0;
    private int nbOccupiedSquares = 0;
    private int nbSquaresWithManyTubs = 0;

    public GameState(int nbSquares) {
        this.nbSquares = nbSquares;
    }

    public void incNbOccupied() {
        nbOccupiedSquares++;
    }

    public void descNbOccupied() {
        nbOccupiedSquares--;
    }

    public void incTubSuperposed() {
        nbSquaresWithManyTubs++;
    }

    public void decTubSuperposed() {
        nbSquaresWithManyTubs--;
    }

    public boolean isOver() {
        return nbOccupiedSquares == nbSquares;
    }

    public boolean playerHasWon() {
        boolean hasWon = nbSquaresWithManyTubs == 0;
        hasWon = hasWon && nbSquares == nbOccupiedSquares;
        return hasWon;
    }
}
