package worktest.filou.flowfreev1;

import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

//une case avec un délimiteur
public class GridDelimiter extends AbsGridElement {

    //la couleur du rond
    private int color;
    //le rond
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

    //On renvoie toujours la couleur du délimiteur
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

    //Recalcule le cercle
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
