package worktest.filou.flowfreev1;

import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by filou on 09/02/16.
 */
public class TubePart implements Parcelable {
    private GraphicalTubPart[] graphicalTubParts = null;
    private int i, j, color;

    public static final Parcelable.Creator<TubePart> CREATOR = new Parcelable.Creator<TubePart>() {
        @Override
        public TubePart createFromParcel(Parcel source) {
            return new TubePart(source);
        }

        @Override
        public TubePart[] newArray(int size) {
            return new TubePart[size];
        }
    };

    public TubePart(Parcel in) {
        i = in.readInt();
        j = in.readInt();
        color = in.readInt();
        graphicalTubParts = new GraphicalTubPart[2];
    }

    public TubePart(int i, int j, int color) {
        graphicalTubParts = new GraphicalTubPart[2];
        graphicalTubParts[0] = null;
        graphicalTubParts[1] = null;
        this.i = i;
        this.j = j;
        this.color = color;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(i);
        dest.writeInt(j);
        dest.writeInt(color);
    }

    public int getColor() {
        return color;
    }


    public GraphicalTubPart[] getGraphicalTubParts() {
        return graphicalTubParts;
    }

    public void setGraphicalTubParts(GraphicalTubPart[] graphicalTubParts) {
        this.graphicalTubParts = graphicalTubParts;
    }

    public void setEntry(GraphicalTubPart entry) {
        graphicalTubParts[0] = entry;
    }

    public void setExit(GraphicalTubPart exit) {
        graphicalTubParts[1] = exit;
    }

    public void draw(Canvas canvas) {
        for (GraphicalTubPart graphicalTubPart: graphicalTubParts) {
            if (graphicalTubPart != null)
                graphicalTubPart.draw(canvas);
        }
    }

    public void setGraphicalTubNext(GraphicalTubPart graphicalTubNext) {
        graphicalTubParts[1] = graphicalTubNext;
    }

    public void setGraphicalTubPrevious(GraphicalTubPart graphicalTubPrevious) {
        graphicalTubParts[0] = graphicalTubPrevious;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
