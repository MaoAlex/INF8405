package worktest.filou.flowfreev1;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.LinkedList;

//état du jeu
public class GameState implements Parcelable {
    //nombre total de cases
    private int nbSquares = 0;
    //nombre de cases parcourues oar un tube
    private int nbOccupiedSquares = 0;
    //nombre de doublon
    private int nbSquaresWithManyTubs = 0;
    //nombre de tubes
    private int nbTubs = 0;
    private int nbColors;
    //pile de position parcourue
    private LinkedList<Position> positionHistory = new LinkedList<>();

    public static final Parcelable.Creator<GameState> CREATOR = new Parcelable.Creator<GameState>() {
        @Override
        public GameState createFromParcel(Parcel source) {
            return new GameState(source);
        }

        @Override
        public GameState[] newArray(int size) {
            return new GameState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public int getNbColors() {
        return nbColors;
    }

    public void setNbColors(int nbColors) {
        this.nbColors = nbColors;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nbSquares);
        dest.writeInt(nbOccupiedSquares);
        dest.writeInt(nbSquaresWithManyTubs);
        dest.writeInt(nbTubs);
        dest.writeInt(nbColors);
    }

    public GameState(int nbSquares) {
        this.nbSquares = nbSquares;
        this.nbColors = nbColors;
    }

    public GameState(Parcel in) {
        nbSquares = in.readInt();
        nbOccupiedSquares = in.readInt();
        nbSquaresWithManyTubs = in.readInt();
        nbTubs = in.readInt();
        nbColors = in.readInt();
    }

    public int getNbTubs() {
        return nbTubs;
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

    public void incnbTubs() {
        nbTubs++;
    }

    public void decnbTubs() {
        nbTubs--;
    }

    public boolean isOver() {
        return nbOccupiedSquares == nbSquares;
    }

    public boolean playerHasWon() {
        //on gagne si on a parcouru toutes les cases sans doublon
        boolean hasWon = nbSquaresWithManyTubs == 0;
        hasWon = hasWon && nbSquares == nbOccupiedSquares;
        hasWon = hasWon && nbColors == nbTubs;
        return hasWon;
    }

    public void pushPositionToHistory(int i, int j) {
        positionHistory.add(new Position(i, j));
    }

    public Position pullPositionFromHistory() {
        return positionHistory.pollLast();
    }

    public Position peekPositionFromHistory() {
        return positionHistory.peekLast();
    }

    public boolean canUndo() {
        return !positionHistory.isEmpty();
    }
}
