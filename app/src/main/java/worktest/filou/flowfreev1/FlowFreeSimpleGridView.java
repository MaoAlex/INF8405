package worktest.filou.flowfreev1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//la grille de jeu
public class FlowFreeSimpleGridView extends View {
    private static final String TAG = "FlowFreeSimpleGridView";
    //stocke les lignes pour tracer la ligne
    private GridLine[] lines;
    private Level level;
    //représentation (la logique) de la grille sous forme matricielle
    private AbsGridElement[][] grid = null;
    private DrawState drawState;
    private GameState gameState;
    private int x_offset, y_offset;
    //un segment pour tracer un trait de la dernière case à la podition du doigt
    private GraphicalTubPart mvTub = null;
    //permet de savoir s'il faut recalculer les tailles (mode paysage/porttrait)
    private boolean isFirstUse = true;
    //fait la correpondance ente une couleur et son tube
    private HashMap<Integer, Tube> colorToTubes;
    //listener pour mettre à jour le nombre de tubes
    private OnTubEndedListener onTubEndedListener;
    private Victory.VictoryListener victoryListener;//declaration du listener

    public interface OnTubEndedListener {
        void onTubEnded(View view, int nbTubs);
    }

    public void setOnTubEndedListener(OnTubEndedListener onTubEndedListener) {
        this.onTubEndedListener = onTubEndedListener;
    }

    //méthode à appeler pour réinitialiser la grille
    public void restart() {
        //remet drawstate à l'état d'origine
        drawState = new DrawState();
        //fixe la grosseur du trait
        drawState.setStrokeW(x_offset, y_offset);
        //remet gamstate à l'état d'origine
        gameState = new GameState(level.getHeight()* level.getWidth());
        mvTub = null;
        isFirstUse = true;
        for (int i = 0; i < level.getWidth(); i++) {
            for (int j = 0; j < level.getHeight(); j++) {
                grid[i][j].reset();
            }
        }
        //reset les tubes
        for (Map.Entry<Integer, Tube> tub: colorToTubes.entrySet()) {
            tub.getValue().reset();
        }

        //remet le score à zéro
        if (onTubEndedListener != null)
            onTubEndedListener.onTubEnded(this, 0);

        invalidate();
    }

    public void undo() {
        //évite les exceotions type: liste vide
        if (!gameState.canUndo())
            return;

        //récupère la couleur courante
        int color = drawState.pullCurrentColor();
        Position position = gameState.pullPositionFromHistory();
        int i = position.getI();
        int j = position.getJ();
        //retire de la case et du tube le dernier segment
        grid[i][j].undo();
        colorToTubes.get(color).undo();

        //la case est maintenant vide, il faut décrémenter le compteur
        if (grid[i][j].isEmpty())
            gameState.descNbOccupied();
        else if (grid[i][j].nbTubPart() == 1)
            gameState.decTubSuperposed();

        //le tube était complet, il ne l'est plus, on décrémente le score
        if (colorToTubes.get(color).isComplete()) {
            gameState.decnbTubs();
            colorToTubes.get(color).setIsComplete(false);
            if (onTubEndedListener != null)
                onTubEndedListener.onTubEnded(this, gameState.getNbTubs());
        }

        //le tube est vide, on autorise l'utilisateur à changer de délimeur
        if (colorToTubes.get(color).isEmpty())
            colorToTubes.get(color).resetDelimiter();

        if (!gameState.canUndo()) {
            drawState.changeDrawState(InternalDrawState.DRAWOFF);
            invalidate();
            return;
        }

        //on met à jour la position courante
        position = gameState.peekPositionFromHistory();
        i = position.getI();
        j = position.getJ();
        drawState.setCurrent_x(i);
        drawState.setCurrent_y(j);
        invalidate();
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
        gameState.setNbColors(colorToTubes.size());
    }

    //dessinne tous les entérieurs des cases
    private void drawElementsOnGrid(Canvas canvas) {
        for (int i = 0; i < level.getWidth(); i++) {
            for (int j = 0; j < level.getHeight(); j++) {
                grid[i][j].draw(canvas, colorToTubes);
            }
        }
    }

    //donne le i au sens matriciel
    private int fromScreenToGridCoordX(float touchX) {
        int i = (int) touchX;
        //division entière
        i /= x_offset;

        //on vérifie la cohérence de la valeur (pour éviter quele trait sorte de la fenêtre
        if (i >= level.getWidth())
            return level.getWidth() - 1;

        if (i < 0)
            return 0;

        return i;
    }

    private int fromScreenToGridCoordY(float touchY) {
        int j = (int) touchY;
        j /= y_offset;

        if (j >= level.getHeight())
            return level.getHeight() - 1;

        if (j < 0)
            return 0;

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
                //vérifie que l'utilisateur est sur une case colorée dont le tube n'est pas fini
                if (grid[i][j].isColored() &&
                        !colorToTubes.get(grid[i][j].getColor()).isComplete()) {
                    if (!colorToTubes.get(grid[i][j].getColor()).hasStart()) {
                        //le Tube n'a pas été initialisé, on doit donc le faire
                        colorToTubes.get(grid[i][j].getColor()).chooseADelimiter(i, j);
                        //on ajoute un élément au tube
                        colorToTubes.get(grid[i][j].getColor()).addTubePart(new TubePart(i, j, grid[i][j].getColor()));
                        grid[i][j].setCurrentColor(grid[i][j].getColor());
                        grid[i][j].addIndexTubePart(grid[i][j].getColor(), colorToTubes.get(grid[i][j].getColor()).getLastIndex());
                        gameState.incNbOccupied();
                        gameState.pushPositionToHistory(i,j);
                    } else if (colorToTubes.get(grid[i][j].getColor()).getEnd().getI() == i
                            && colorToTubes.get(grid[i][j].getColor()).getEnd().getJ() == j) {
                        //l'utilisateur à posé son doigt sur une mauvaise extrémité
                        drawState.setInternalState(InternalDrawState.DRAWOFF);
                        return true;
                    }
                    //On sauvegarde position et couleur
                    drawState.setCurrent_x(i);
                    drawState.setCurrent_y(j);
                    drawState.setCurrentColor(grid[i][j].getColor());
                    drawState.setInternalState(InternalDrawState.DRAWON);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (drawState.getInternalState() == InternalDrawState.DRAWOFF)
                    return true;
                int i0 = drawState.getCurrent_x(), j0 = drawState.getCurrent_y(),
                        color = drawState.getCurrentColor() ;
                if (drawState.validMove(i, j)
                        && colorToTubes.get(color).isTail(i0, j0)
                        && drawState.isColorFriendly(i, j, grid, colorToTubes)) {
                    TubePart tubePart = colorToTubes.get(color).getTubePart(grid[i0][j0].getIndexTubePart(color));
                    TubePart newTubePart = new TubePart(i, j, color);
                    GraphicalTubPart[] tubParts = drawState.createTubParts(i0, j0, i,
                            j, color, grid);
                    tubePart.setGraphicalTubNext(tubParts[0]);
                    newTubePart.setGraphicalTubPrevious(tubParts[1]);
                    colorToTubes.get(color).addTubePart(newTubePart);
                    //si la case n'est pas vide, alors on a un tube superposé en plus
                    if (!grid[i][j].isEmpty() && color != grid[i][j].getColor())
                        gameState.incTubSuperposed();
                    //si la case est vide, on la remplit
                    if (grid[i][j].isEmpty())
                        gameState.incNbOccupied();
                    //si on vient de finir un tube on le signal à l'utilisateur
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
                    gameState.pushPositionToHistory(i,j);
                    if (gameState.isOver()) {
                        if (gameState.playerHasWon()) {
                            if(victoryListener!=null){
                                victoryListener.showVictory();//Partie gagne, affichage de la fenetre
                            }

                        } else {
                            if(victoryListener!=null){
                                victoryListener.showDefeat();
                            }
                        }
                    }
                } else if (drawState.getInternalState() != InternalDrawState.DRAWOFF
                        && colorToTubes.get(color).isTail(i0, j0) && !colorToTubes.get(color).isComplete()) {
                    //trace le trait qui suit le doigt
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
            //on a besoin de recalculer les tailles de tous les éléments
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
        //la taille a changé: nouvelles lignes, nouveaux offset
        lines = new GridLine[level.getHeight() + level.getWidth() + 2];
        setupDrawing();
        drawState.setX_offset(x_offset);
        drawState.setY_offset(y_offset);
        drawState.updateStrikeSize();
        //met les coordonnées des centres à jour pour les prochains calculs
        for (int i = 0; i < level.getWidth(); i++) {
            for (int j = 0; j < level.getHeight(); j++) {
                grid[i][j].setCenterX(i * x_offset + x_offset / 2);
                grid[i][j].setCenterY(j*y_offset + y_offset/2);
            }
        }

        //modifie les tailles en se basant sur les centres calculés au-dessus
        for (int i = 0; i < level.getWidth(); i++) {
            for (int j = 0; j < level.getHeight(); j++) {
                grid[i][j].updateSize(drawState, grid, colorToTubes);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
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
        //-1 pour que la grille soit toujours visible dans le layout
        int w = getWidth() - 1;
        int h = getHeight() - 1;
        x_offset = w/level.getWidth();
        y_offset = h/level.getHeight();
        //crée les lignes verticales
        for (int i = 0; i <= level.getWidth(); i++) {
            lines[i] = new GridLine(i*x_offset, 0, i*x_offset, level.getWidth()*y_offset, paint);
        }

        //crée les lignes horizontales
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

    public void setVictoryListener(Victory.VictoryListener listener) {
        this.victoryListener = listener;
    }

}
