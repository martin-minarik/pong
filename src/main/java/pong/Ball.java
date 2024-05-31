package pong;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Ball extends Entity implements Collisionable {
    private final Point2D startPosition;
    private final Point2D initialVelocity;
    private Point2D velocity;

    public Ball(Game game, double size, Point2D position, Point2D velocity) {
        super(game, position, new Point2D(size, size));
        this.startPosition = position;
        this.initialVelocity = velocity;
        this.velocity = velocity;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.GAINSBORO);
        gc.fillOval(position.getX(), position.getY(), dimension.getX(), dimension.getY());
        gc.restore();
    }

    @Override
    public void simulate(double deltaT) {
        position = position.add(velocity.multiply(deltaT));
    }

    private void hitBat(Rectangle2D otherBoundingBox) {
        double distanceFromTop = Math.abs(otherBoundingBox.getMinY() - position.getY());
        double angle = (Math.PI / 4) * (distanceFromTop / otherBoundingBox.getHeight()) - (Math.PI / 8);
        double speed = velocity.magnitude();
        velocity = new Point2D(Math.signum(this.getBoundingBox().getMinX() - otherBoundingBox.getMinX()) * Math.cos(angle), Math.sin(angle));
        velocity = velocity.multiply(speed);
    }

    public void reset() {
        this.position = startPosition;
        this.velocity = initialVelocity;
    }

    @Override
    public void hitBy(Collisionable other) {
        if (other instanceof Bat) {
            hitBat(other.getBoundingBox());
        } else if (other instanceof Wall) {
            velocity = new Point2D(velocity.getX(),
                    Math.abs(velocity.getY()) * Math.signum(this.getBoundingBox().getMinY() - other.getBoundingBox().getMinY()));
        } else if (other instanceof GoalDetectionBox) {
            this.reset();
        }
    }

    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), dimension.getX(), dimension.getY());
    }

}
