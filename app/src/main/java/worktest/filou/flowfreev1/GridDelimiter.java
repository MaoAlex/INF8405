package worktest.filou.flowfreev1;

import android.graphics.Canvas;

/**
 * Created by filou on 07/02/16.
 */
public class GridDelimiter extends AbsGridElement {

    private int color;
    private AbsGridDrawable gridDrawable;
    private Tube tube;

    @Override
    public boolean isColored() {
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        gridDrawable.draw(canvas);
    }

    public GridDelimiter(int i, int j, float centerX, float centerY, int color,Tube tube, AbsGridDrawable gridDrawable) {
        super(i, j, centerX, centerY);
        this.color = color;
        this.gridDrawable = gridDrawable;
        this.tube = tube;
    }

    @Override
    public void updateSize(DrawState drawState, AbsGridElement[][] grid) {
        super.updateSize(drawState, grid);
        gridDrawable = new GridCircle(getCenterX(), getCenterY(),
                Math.min(drawState.getX_offset(), drawState.getY_offset())/3,
                drawState.setupPaint(color, true));
    }

    @Override
    public int getColor() {
        return color;
    }

    public Tube getTube() {
        return tube;
    }
}
