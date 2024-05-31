package pong;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Score implements DrawableSimulable {
    private final Game game;
    private int value;
    private final double font_size;
    private Point2D position;

    public Score(Game game, Point2D position, double font_size) {
        this.game = game;
        this.position = position;
        this.font_size = font_size;
        this.value = 0;
    }

    public void increment() {
        ++value;
    }

    @Override
    public void simulate(double deltaT) {

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.GAINSBORO);
        gc.setFont(Font.font("San Serif", font_size));
        gc.fillText(String.valueOf(this.value), position.getX(), position.getY());
        gc.restore();
    }

    public int getValue() {
        return value;
    }

    public void reset() {
        this.value = 0;
    }
}
