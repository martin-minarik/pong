package pong;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CenterLine extends Entity {
    public CenterLine(Game game, Point2D position, Point2D dimension) {
        super(game, position, dimension);
    }

    @Override
    public void simulate(double deltaT) {

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.setStroke(Color.GAINSBORO);
        gc.setLineWidth(dimension.getX());
        gc.setLineDashes(0, dimension.getX() * 2);
        gc.strokeLine(position.getX(), position.getY(), position.getX(), dimension.getY());
        gc.restore();
    }
}
