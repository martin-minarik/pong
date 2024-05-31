package pong;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;

public class Bat extends Entity implements Collisionable {
    private final double minY;
    private final double maxY;
    private final Point2D startPosition;

    public Bat(Game game, Point2D position, Point2D dimension, double minY, double maxY) {
        super(game, position, dimension);
        this.startPosition = position;
        this.minY = minY;
        this.maxY = maxY;
    }

    public Point2D getPosition() {
        return position;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.GAINSBORO);
        gc.fillRect(position.getX() - (dimension.getX() / 2), position.getY() - (dimension.getY() / 2), dimension.getX(), dimension.getY());
        gc.restore();
    }

    @Override
    public void simulate(double deltaT) {
//        position = position.add(0, 100 * deltaT * 1);

    }

    public void move(double direction)
    {
        position = position.add(0, direction);
        clampPosition();
    }

    private void clampPosition(){
        double clampedY = Math.max(this.minY + (dimension.getY() / 2), Math.min(this.maxY - (dimension.getY() / 2), position.getY()));
        position = new Point2D(position.getX(), clampedY);
    }

    public Rectangle2D getBoundingBoxRectangle2D() {
        return new Rectangle2D(position.getX() - (dimension.getX() / 2), position.getY() - (dimension.getY() / 2), dimension.getX(), dimension.getY());
    }

    @Override
    public void hitBy(Collisionable other) {
//        if (other instanceof Wall)
//        {
//        }
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX() - (dimension.getX() / 2), position.getY() - (dimension.getY() / 2), dimension.getX(), dimension.getY());
    }

    public void reset()
    {
        this.position = this.startPosition;
    }

}
