package worktest.filou.flowfreev1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

//segment pour relié des cases entre elles
public class GraphicalTubPart extends AbsGridDrawable {
    private Paint paint;
    private Path path;

    public GraphicalTubPart(Paint paint, Path path) {
        this.paint = paint;
        this.path = path;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public int getColor() {
        return paint.getColor();
    }
}
