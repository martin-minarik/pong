package pong;

import javafx.geometry.Point2D;

public abstract class Entity implements DrawableSimulable
{
    protected final Game game;
    protected Point2D position;
    protected final Point2D dimension;


    public Entity(Game game, Point2D position, Point2D dimension) {
        this.game = game;
        this.position = position;
        this.dimension = dimension;
    }
}
