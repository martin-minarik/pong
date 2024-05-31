package pong;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GoalDetectionBox extends Entity implements Collisionable {
    private HitListener hitListener = new EmptyHitListener();

    public GoalDetectionBox(Game game, Point2D position, Point2D dimension) {
        super(game, position, dimension);

    }

    @Override
    public void simulate(double deltaT) {

    }

    @Override
    public void draw(GraphicsContext gc) {
    }

    @Override
    public void hitBy(Collisionable other) {
        if (other instanceof Ball) {
            this.hitListener.hit();
        }
    }


    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), dimension.getX(), dimension.getY());
    }

    public void setHitListener(HitListener hitListener) {
        this.hitListener = hitListener;
    }
}
