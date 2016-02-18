package worktest.filou.flowfreev1;

import android.graphics.Canvas;
import android.graphics.Paint;

//encapsule une ligne graphique
public class GridLine extends AbsGridDrawable {
    Canvas canvas;
    Paint paint;
    float x0, y0, x1, y1;

    public GridLine(float x0, float y0, float x1, float y1, Paint paint) {
        this.x1 = x1;
        this.y0 = y0;
        this.x0 = x0;
        this.y1 = y1;
        this.paint = paint;
    }

    public void draw(Canvas canvas) {
        canvas.drawLine(x0, y0, x1, y1, paint);
    }

    public int getColor() {
        return paint.getColor();
    }
}
