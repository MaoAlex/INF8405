package worktest.filou.flowfreev1;

import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

/**
 * Created by filou on 07/02/16.
 */
public class GridDelimiter extends AbsGridElement {

    private int color;
    private AbsGridDrawable gridDrawable;


    public static final Parcelable.Creator<GridDelimiter> CREATOR = new Parcelable.Creator<GridDelimiter>() {
        @Override
        public GridDelimiter createFromParcel(Parcel source) {
            return new GridDelimiter(source);
        }

        @Override
        public GridDelimiter[] newArray(int size) {
            return new GridDelimiter[size];
        }
    };

    public GridDelimiter(Parcel in) {
        super(in);
        color = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(color);
    }

    @Override
    public boolean isColored() {
        return true;
    }

    @Override
    public void draw(Canvas canvas, HashMap<Integer, Tube> colorToTubes) {
        super.draw(canvas, colorToTubes);
        gridDrawable.draw(canvas);
    }

    public GridDelimiter(int i, int j, float centerX, float centerY, int color, AbsGridDrawable gridDrawable) {
        super(i, j, centerX, centerY);
        this.color = color;
        this.gridDrawable = gridDrawable;
    }

    @Override
    public void updateSize(DrawState drawState, AbsGridElement[][] grid, HashMap<Integer, Tube> colorToTubes) {
        super.updateSize(drawState, grid, colorToTubes);
        gridDrawable = new GridCircle(getCenterX(), getCenterY(),
                Math.min(drawState.getX_offset(), drawState.getY_offset())/3,
                drawState.setupPaint(color, true));
    }

    @Override
    public int getColor() {
        return color;
    }

}
