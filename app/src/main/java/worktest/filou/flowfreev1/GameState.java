package worktest.filou.flowfreev1;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by filou on 10/02/16.
 */
public class GameState implements Parcelable {
    private int nbSquares = 0;
    private int nbOccupiedSquares = 0;
    private int nbSquaresWithManyTubs = 0;

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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(nbSquares);
        dest.writeInt(nbOccupiedSquares);
        dest.writeInt(nbSquaresWithManyTubs);
    }

    public GameState(int nbSquares) {
        this.nbSquares = nbSquares;
    }

    public GameState(Parcel in) {
        nbSquares = in.readInt();
        nbOccupiedSquares = in.readInt();
        nbSquaresWithManyTubs = in.readInt();
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
