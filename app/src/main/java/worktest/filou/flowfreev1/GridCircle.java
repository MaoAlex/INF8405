package worktest.filou.flowfreev1;

import android.graphics.Canvas;
import android.graphics.Paint;

//Encapsule un cercle pour les délimiteurs
public class GridCircle extends AbsGridDrawable {
    float x, y, radius;
    Paint paint;

    public GridCircle(float x, float y, float radius, Paint paint) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.paint = paint;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, radius, paint);
    }

    @Override
    public int getColor() {
        return paint.getColor();
    }
}
