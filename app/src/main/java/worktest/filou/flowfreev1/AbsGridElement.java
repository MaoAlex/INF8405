package worktest.filou.flowfreev1;

import android.graphics.Canvas;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by filou on 07/02/16.
 */
public abstract class AbsGridElement {
   private int i, j;
    private float centerX, centerY;
    protected LinkedHashMap<Integer,TubePart> tubParts= new LinkedHashMap<>();
    protected LinkedList<Integer> colorsInOrder = new LinkedList<>();

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public AbsGridElement(int i, int j, float centerX, float centerY) {
        this.i = i;
        this.j = j;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public float getCenterX() {
        return centerX;
    }

    public void setCurrentColor(int color) {
        colorsInOrder.push(color);
    }

    public float getCenterY() {
        return centerY;
    }

    public void addTubePart(int color, TubePart tubePart) {
        tubParts.put(color, tubePart);
    }

    public void removeColor(int color) {
        tubParts.remove(color);
    }

    public TubePart getTubePart(int color) {
        return tubParts.get(color);
    }

    public boolean isEmpty() {
        return tubParts.isEmpty();
    }

    public abstract int getColor();

    public abstract boolean isColored();

    public void undo() {
        int oldColor = colorsInOrder.pollLast();
        tubParts.remove(oldColor);
    }

    public void draw(Canvas canvas) {
        for (TubePart tubePart : tubParts.values()) {
            tubePart.draw(canvas);
        }
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }

    public void updateSize(DrawState drawState, AbsGridElement[][] grid) {
        for (TubePart tubePart : tubParts.values()) {
            int color = tubePart.getColor();
            TubePart tubePartPrevious = tubePart.getPrevious();
            TubePart tubePartNext = tubePart.getNext();
            if (tubePartPrevious != null) {
                tubePart.setGraphicalTubPrevious(drawState.createTubToBorder(tubePart.getI(), tubePart.getJ(),
                        tubePartPrevious.getI(), tubePartPrevious.getJ(), color, grid));
            }

            if (tubePartNext != null) {
                tubePart.setGraphicalTubNext(drawState.createTubToBorder(tubePart.getI(), tubePart.getJ(),
                        tubePartNext.getI(), tubePartNext.getJ(), color, grid));
            }
        }
    }
}
