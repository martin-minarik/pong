package pong;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wall extends Entity implements Collisionable {
    public Wall(Game game, Point2D position, Point2D dimension) {
        super(game, position, dimension);
    }

    @Override
    public void simulate(double deltaT) {

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.GAINSBORO);
        gc.fillRect(position.getX(), position.getY(), dimension.getX(), dimension.getY());
        gc.restore();
    }

    @Override
    public void hitBy(Collisionable other) {

    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), dimension.getX(), dimension.getY());
    }


}
