package worktest.filou.flowfreev1;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by filou on 07/02/16.
 */
public class FlowFreeSimpleGridView extends View {
    private static final String TAG = "FlowFreeSimpleGridView";
    private GridLine[] lines;
    private Level level;
    private AbsGridElement[][] grid = null;
    private DrawState drawState;
    private GameState gameState;
    private int x_offset, y_offset;
    private GraphicalTubPart mvTub = null;
    private boolean isFirstUse = true;
    private HashMap<Integer, Tube> colorToTubes;
    private OnTubEndedListener onTubEndedListener;

    public interface OnTubEndedListener {
        void onTubEnded(View view, int nbTubs);
    }

    public void setOnTubEndedListener(OnTubEndedListener onTubEndedListener) {
        this.onTubEndedListener = onTubEndedListener;
    }

    private void setupGrid() {
        float radius = Math.min(x_offset, y_offset)/3;
        Paint tmpPaint;

        for (int x_index = 0; x_index < level.getWidth(); x_index++) {
            for (int y_index = 0; y_index < level.getHeight(); y_index++) {
                float x_center = x_index*x_offset + x_offset/2;
                float y_center = y_index*y_offset + y_offset/2;
                grid[x_index][y_index] = new GridElement(x_index, y_index, x_center, y_center);
            }
        }

        ArrayList<Delimiter> delimiters = level.getDelimiters();
        for (int i = 0; i < delimiters.size(); i += 2) {
            Delimiter delimiter0 = delimiters.get(i);
            Delimiter delimiter1 = delimiters.get(i + 1);
            int i0 = delimiter0.getI();
            int j0 = delimiter0.getJ();
            float x_center0 = i0*x_offset + x_offset/2;
            float y_center0 = j0*y_offset + y_offset/2;
            int color = delimiter0.getColorCode();
            tmpPaint = drawState.setupPaint(color, true);
            int i1 = delimiter1.getI();
            int j1 = delimiter1.getJ();
            float x_center1 = i1*x_offset + x_offset/2;
            float y_center1 = j1*y_offset + y_offset/2;
            Tube tube = new Tube(i0, j0, i1, j1, color);
            colorToTubes.put(color, tube);
            grid[i0][j0] = new GridDelimiter(i0, j0, x_center0,
                    y_center0, color,
                    new GridCircle(x_center0,  y_center0, radius, tmpPaint));
            grid[i1][j1] = new GridDelimiter(i1, j1, x_center1,
                    y_center1, color,
                    new GridCircle(x_center1,  y_center1, radius, tmpPaint));
        }
    }

    private void drawElementsOnGrid(Canvas canvas) {
        for (int i = 0; i < level.getWidth(); i++) {
            for (int j = 0; j < level.getHeight(); j++) {
                grid[i][j].draw(canvas, colorToTubes);
            }
        }
    }

    private int fromScreenToGridCoordX(float touchX) {
        int i = (int) touchX;
        i /= x_offset;

        if (i >= level.getWidth())
            return level.getWidth() - 1;

        return i;
    }

    private int fromScreenToGridCoordY(float touchY) {
        int j = (int) touchY;
        j /= y_offset;

        if (j >= level.getHeight())
            return level.getHeight() - 1;

        return j;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        int i = fromScreenToGridCoordX(touchX);
        int j = fromScreenToGridCoordY(touchY);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (grid[i][j].isColored() &&
                        !colorToTubes.get(grid[i][j].getColor()).isComplete()) {
                    if (!colorToTubes.get(grid[i][j].getColor()).hasStart()) {
                        colorToTubes.get(grid[i][j].getColor()).chooseADelimiter(i, j);
                        colorToTubes.get(grid[i][j].getColor()).addTubePart(new TubePart(i, j, grid[i][j].getColor()));
                        grid[i][j].setCurrentColor(grid[i][j].getColor());
                        grid[i][j].addIndexTubePart(grid[i][j].getColor(), colorToTubes.get(grid[i][j].getColor()).getLastIndex());
                        gameState.incNbOccupied();
                    } else if (colorToTubes.get(grid[i][j].getColor()).getEnd().getI() == i
                            && colorToTubes.get(grid[i][j].getColor()).getEnd().getJ() == j)
                        return true;
                    drawState.setCurrent_x(i);
                    drawState.setCurrent_y(j);
                    drawState.setCurrentColor(grid[i][j].getColor());
                    drawState.setInternalState(InternalDrawState.DRAWON);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int i0 = drawState.getCurrent_x(), j0 = drawState.getCurrent_y(),
                        color = drawState.getCurrentColor() ;
                if (drawState.validMove(i, j)
                        && drawState.getInternalState() != InternalDrawState.DRAWOFF
                        && colorToTubes.get(color).isTail(i0, j0)
                        && drawState.isColorFriendly(i, j, grid, colorToTubes)) {
                    TubePart tubePart = colorToTubes.get(color).getTubePart(grid[i0][j0].getIndexTubePart(color));
                    TubePart newTubePart = new TubePart(i, j, color);
                    GraphicalTubPart[] tubParts = drawState.createTubParts(i0, j0, i,
                            j, color, grid);
                    tubePart.setGraphicalTubNext(tubParts[0]);
                    newTubePart.setGraphicalTubPrevious(tubParts[1]);
                    colorToTubes.get(color).addTubePart(newTubePart);
                    if (!grid[i][j].isEmpty() && color != grid[i][j].getColor())
                        gameState.incTubSuperposed();
                    if (grid[i][j].isEmpty())
                        gameState.incNbOccupied();
                    if (colorToTubes.get(color).isComplete()) {
                        gameState.incnbTubs();
                        drawState.setInternalState(InternalDrawState.DRAWOFF);
                        if (onTubEndedListener != null)
                            onTubEndedListener.onTubEnded(this, gameState.getNbTubs());
                    }
                    grid[i][j].addIndexTubePart(color, colorToTubes.get(color).getLastIndex());
                    grid[i][j].setCurrentColor(color);
                    drawState.setCurrent_x(i);
                    drawState.setCurrent_y(j);
                    drawState.setCurrentColor(color);
                    if (gameState.isOver()) {
                        if (gameState.playerHasWon()) {
                            Log.d(TAG, "onTouchEvent: player has won");
                        } else {
                            Log.d(TAG, "onTouchEvent: player has lost");
                        }
                    }
                } else if (drawState.getInternalState() != InternalDrawState.DRAWOFF
                        && colorToTubes.get(color).isTail(i0, j0) && !colorToTubes.get(color).isComplete()) {
                    mvTub = drawState.createTubToPos(i0, j0, touchX, touchY, color, grid);
                }
                break;
            case MotionEvent.ACTION_UP:
                drawState.setInternalState(InternalDrawState.DRAWOFF);
                mvTub = null;
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public FlowFreeSimpleGridView(Context context) {
        super(context);
        init(context);
    }

    public FlowFreeSimpleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void handleChanges(int w, int h) {
        if (isFirstUse) {
            isFirstUse = false;
            Log.d(TAG, "handleChanges: first use");
            setupDrawing();
            setupGrid();
            drawState.setStrokeW(x_offset, y_offset);
        } else {
            Log.d(TAG, "handleChanges: screen turned");
            changeOrientation(w, h);
            isFirstUse = true;
        }
    }

    private void init(Context context) {
        setSaveEnabled(true);
        level = ((InGame)context).getLevel();
        colorToTubes = new HashMap<>();
        grid = new AbsGridElement[level.getWidth()][level.getHeight()];
        lines = new GridLine[level.getHeight() + level.getWidth() + 2];
        gameState = new GameState(level.getWidth()*level.getHeight());
        drawState = new DrawState();
    }

    private void changeOrientation(int w, int h) {
        lines = new GridLine[level.getHeight() + level.getWidth() + 2];
        setupDrawing();

        for (int i = 0; i < level.getWidth(); i++) {
            for (int j = 0; j < level.getHeight(); j++) {
                grid[i][j].setCenterX(i * x_offset + x_offset / 2);
                grid[i][j].setCenterY(j*y_offset + y_offset/2);
            }
        }

        for (int i = 0; i < level.getWidth(); i++) {
            for (int j = 0; j < level.getHeight(); j++) {
                grid[i][j].updateSize(drawState, grid, colorToTubes);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: start");
        handleChanges(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (GridLine line: lines) {
            line.draw(canvas);
        }
        drawElementsOnGrid(canvas);
        drawLineToFinger(canvas);
    }

    private void drawLineToFinger(Canvas canvas) {
        if (mvTub != null)
            mvTub.draw(canvas);
        mvTub = null;
    }

    private void setupDrawing(){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        //minus one to be sure lines don't conflict with layout border
        int w = getWidth() - 1;
        int h = getHeight() - 1;
        x_offset = w/level.getWidth();
        y_offset = h/level.getHeight();
        for (int i = 0; i <= level.getWidth(); i++) {
            lines[i] = new GridLine(i*x_offset, 0, i*x_offset, level.getWidth()*y_offset, paint);
        }

        for (int i = 0; i <= level.getHeight(); i++) {
            lines[i + 1 + level.getWidth()]= new GridLine(0, i*y_offset,
                    level.getHeight()*x_offset, i*y_offset, paint);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        GridWrapper gridWrapper = new GridWrapper(level.getWidth(), level.getHeight(), grid);
        GridSavedState gridSavedState = new GridSavedState(superState,
                                                            gridWrapper, gameState,
                                                            drawState,
                                                            new HashIntTubeWrapper(colorToTubes));

        return gridSavedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        GridSavedState gridSavedState = (GridSavedState) state;
        super.onRestoreInstanceState(gridSavedState.getSuperState());
        isFirstUse = false;
        grid = gridSavedState.getGridWrapper().getGrid();
        drawState = gridSavedState.getDrawState();
        gameState = gridSavedState.getGameState();
        colorToTubes = new HashMap<>();
        gridSavedState.getHashIntTubeWrapper().fillMap(colorToTubes);
    }
}
