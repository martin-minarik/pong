package pong;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Game implements Serializable
{
    private final double width;
    private final double height;

    transient private GameListener gameListener = new EmptyGameListener();
    private int totalGoals = 0;

    private Score scoreLeft;
    private Score scoreRight;
    private GoalDetectionBox goalDetectionBoxLeft;
    private GoalDetectionBox goalDetectionBoxRight;
    private Bat leftBat;
    private Bat rightBat;
    private Ball ball;
    private DrawableSimulable[] objects;


    public Game(double width, double height) {
        this.width = width;
        this.height = height;


        this.scoreLeft = new Score(this, new Point2D(width / 2 - 60, 60), 40);
        this.scoreRight = new Score(this, new Point2D(width / 2 + 40, 60), 40);
        this.goalDetectionBoxLeft = new GoalDetectionBox(this, new Point2D(-9999, 0), new Point2D(9999, height));
        this.goalDetectionBoxRight = new GoalDetectionBox(this, new Point2D(width, 0), new Point2D(Double.POSITIVE_INFINITY, height));
        this.leftBat = new Bat(this, new Point2D(40, height / 2), new Point2D(20, 150), 20, height - 27);
        this.rightBat = new Bat(this, new Point2D(width - 40, height / 2), new Point2D(20, 150), 20, height - 27);
        this.ball = new Ball(this, 30, new Point2D(width / 2, height / 2), new Point2D(640, 0));



        goalDetectionBoxLeft.setHitListener(() -> {
            scoreRight.increment();
            goal();
        });

        goalDetectionBoxRight.setHitListener(() -> {
            scoreLeft.increment();
            goal();
        });

        this.objects = new DrawableSimulable[]{
                this.goalDetectionBoxLeft,
                this.goalDetectionBoxRight,
                this.ball,
                this.leftBat,
                this.rightBat,
                new CenterLine(this, new Point2D(width / 2, 0), new Point2D(20, height)),
                new Wall(this, new Point2D(0, 0), new Point2D(width, 20)),
                new Wall(this, new Point2D(0, height - 27), new Point2D(width, 27)),
                this.scoreLeft,
                this.scoreRight
        };

    }


    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void simulate(double deltaT) {
        for (DrawableSimulable obj : objects) {
            obj.simulate(deltaT);
        }

        for (DrawableSimulable obj_a : objects) {
            if (obj_a instanceof Collisionable collision_obj_a) {
                for (DrawableSimulable obj_b : objects) {
                    if ((obj_a != obj_b) && (obj_b instanceof Collisionable collision_obj_b)) {
                        if (collision_obj_a.overlaps(collision_obj_b)) {
                            collision_obj_a.hitBy(collision_obj_b);
                        }
                    }
                }
            }
        }
    }

    public void draw(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        clearCanvas(canvas);
        for (DrawableSimulable obj : objects) {
            obj.draw(gc);
        }

    }

    public void clearCanvas(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.save();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.restore();
    }

    public void leftBatUp() {
        this.leftBat.move(-10);
    }

    public void leftBatDown() {
        this.leftBat.move(10);
    }

    public void rightBatUp() {
        this.rightBat.move(-10);
    }

    public void rightBatDown() {
        this.rightBat.move(10);
    }

    public void reset() {
        this.ball.reset();
        this.leftBat.reset();
        this.rightBat.reset();

        this.scoreLeft.reset();
        this.scoreRight.reset();

        this.totalGoals = 0;
    }

    public void setGameListener(GameListener gameListener) {
        this.gameListener = gameListener;
    }

    private void goal() {
        ++totalGoals;
        gameListener.stateChanged(totalGoals, scoreLeft.getValue(), scoreRight.getValue());
    }

    public Score getScoreLeft()
    {
        return scoreLeft;
    }

    public Score getScoreRight()
    {
        return scoreRight;
    }
}
