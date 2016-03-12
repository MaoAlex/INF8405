package worktest.filou.flowfreev1;

import android.graphics.Canvas;

//élément graphique pour le dessin
public abstract class AbsGridDrawable {
    public abstract void draw(Canvas canvas);
    public abstract int getColor();
}
