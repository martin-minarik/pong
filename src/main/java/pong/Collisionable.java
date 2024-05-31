package pong;

import javafx.geometry.Rectangle2D;

public interface Collisionable {
    default boolean overlaps(Collisionable other) {
        return getBoundingBox().intersects(other.getBoundingBox());
    }

    void hitBy(Collisionable other);

    Rectangle2D getBoundingBox();
}
