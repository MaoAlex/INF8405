package worktest.filou.flowfreev1;

import android.graphics.Canvas;

/**
 * Created by filou on 09/02/16.
 */
public class TubePart {
    private TubePart[] previousAndNext = null;
    private Tube tube = null;
    private GraphicalTubPart[] graphicalTubParts = null;
    private int i, j, color;

    public TubePart(int i, int j, int color, TubePart previous, Tube tube) {
        previousAndNext = new TubePart[2];
        previousAndNext[0] = previous;
        previousAndNext[1] = null;
        graphicalTubParts = new GraphicalTubPart[2];
        graphicalTubParts[0] = null;
        graphicalTubParts[1] = null;
        this.tube = tube;
        this.i = i;
        this.j = j;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public TubePart getNext() {
        return previousAndNext[1];
    }

    public Tube getTube() {
        return tube;
    }

    public GraphicalTubPart[] getGraphicalTubParts() {
        return graphicalTubParts;
    }

    public TubePart getPrevious() {
        return previousAndNext[0];
    }

    public void setNext(TubePart next) {
        previousAndNext[1] = next;
    }

    public void setPrevious(TubePart previous) {
        previousAndNext[0] = previous;
    }

    public void setTube(Tube tube) {
        this.tube = tube;
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

    public void switchEntryAndExit() {
        GraphicalTubPart tmp = graphicalTubParts[0];
        graphicalTubParts[0] = graphicalTubParts[1];
        graphicalTubParts[1] = tmp;
    }

    public void switchPreviousAndNext() {
        TubePart tmp = previousAndNext[0];
        previousAndNext[0] = previousAndNext[1];
        previousAndNext[1] = tmp;
    }

    public Boolean isTail() {
        return previousAndNext[1] == null;
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
