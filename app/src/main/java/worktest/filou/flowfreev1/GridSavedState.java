package worktest.filou.flowfreev1;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by filou on 12/02/16.
 */
public class GridSavedState extends View.BaseSavedState {
        private GridWrapper gridWrapper;
        private GameState gameState;
        private  DrawState drawState;
        private HashIntTubeWrapper hashIntTubeWrapper;
    public static final Parcelable.Creator<GridSavedState> CREATOR = new Parcelable.Creator<GridSavedState>() {
        @Override
        public GridSavedState createFromParcel(Parcel source) {
            return new GridSavedState(source);
        }

        @Override
        public GridSavedState[] newArray(int size) {
            return new GridSavedState[size];
        }
    };

    public GridSavedState(Parcelable superState,
                          GridWrapper gridWrapper, GameState gameState,
                          DrawState drawState, HashIntTubeWrapper colorToTubes) {
        super(superState);
        this.gridWrapper = gridWrapper;
        this.gameState = gameState;
        this.drawState = drawState;
        this.hashIntTubeWrapper = colorToTubes;
    }

    public GridSavedState(Parcel in) {
        super(in);
        gridWrapper = in.readParcelable(GridWrapper.class.getClassLoader());
        gameState = in.readParcelable(GameState.class.getClassLoader());
        drawState = in.readParcelable(DrawState.class.getClassLoader());
        hashIntTubeWrapper = in.readParcelable(HashIntTubeWrapper.class.getClassLoader());
    }

    public GridWrapper getGridWrapper() {
        return gridWrapper;
    }

    public GameState getGameState() {
        return gameState;
    }

    public DrawState getDrawState() {
        return drawState;
    }

    public HashIntTubeWrapper getHashIntTubeWrapper() {
        return hashIntTubeWrapper;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeParcelable(gridWrapper, 0);
        out.writeParcelable(gameState, 0);
        out.writeParcelable(drawState, 0);
        out.writeParcelable(hashIntTubeWrapper, 0);
    }

}
