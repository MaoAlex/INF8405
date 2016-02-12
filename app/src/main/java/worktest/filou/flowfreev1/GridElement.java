package worktest.filou.flowfreev1;

/**
 * Created by filou on 07/02/16.
 */
public class GridElement extends AbsGridElement {

    @Override
    public boolean isColored() {
        return !colorsInOrder.isEmpty();
    }

    public GridElement(int i, int j, float centerX, float centerY) {
        super(i, j, centerX, centerY);
    }

    @Override
    public int getColor() {
        return colorsInOrder.getFirst();
    }
}
