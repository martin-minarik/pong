package pong;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class GameController
{

    private Game game;

    @FXML
    Canvas canvas;

    @FXML
    Text middleText;

    @FXML
    TextField leftPlayerNameField;

    @FXML
    TextField rightPlayerNameField;

    @FXML
    private ListView<ScoreListViewItem> highScores;

    private List<ScoreListViewItem> scores;

    private AnimationTimer animationTimer;

    private ScoreDAO scoreDAO;

    public GameController()
    {
    }

    public void startGame()
    {
        scores = new LinkedList<>();
        scoreDAO = new ScoreDAO();
        highScores.setItems(FXCollections.observableList(scores));
        this.game = new Game(canvas.getWidth(), canvas.getHeight());
        this.game.setGameListener(new GameListenerImpl());

        animationTimer = new AnimationTimerImpl();

        drawScene(0);

    }

    private final class AnimationTimerImpl extends AnimationTimer
    {
        private Long previous;
        private boolean restartScheduled = false;

        @Override
        public void stop()
        {
            this.restartScheduled = true;
            super.stop();
        }

        @Override
        public void handle(long now)
        {
            if (restartScheduled)
            {
                previous = now;
                restartScheduled = false;
            }
            if (previous == null)
            {
                previous = now;
            } else
            {
                drawScene((now - previous) / 1e9);
                previous = now;
            }
        }
    }


    public void stopGame()
    {
        animationTimer.stop();
        middleText.setDisable(false);
        middleText.setText("VICTORY!");
        middleText.setVisible(true);
    }

    private void drawScene(double deltaT)
    {
        game.draw(canvas);
        game.simulate(deltaT);
    }

    public void resetGame()
    {
        int scoreLeft = game.getScoreLeft().getValue();
        int scoreRight = game.getScoreRight().getValue();
        if (scoreLeft > 0)
            addPlayerScoreToListView(leftPlayerNameField.getText(), scoreLeft);
        if (scoreRight > 0)
            addPlayerScoreToListView(rightPlayerNameField.getText(), scoreRight);

        game.reset();
        middleText.setDisable(true);
        middleText.setVisible(false);
        animationTimer.start();
    }

    public void leftBatUp()
    {
        game.leftBatUp();
    }

    public void leftBatDown()
    {
        game.leftBatDown();
    }

    public void rightBatUp()
    {
        game.rightBatUp();
        animationTimer.start();
    }

    public void rightBatDown()
    {
        game.rightBatDown();
    }


    private class GameListenerImpl implements GameListener
    {

        @Override
        public void stateChanged(int totalGoals, int scoreLeft, int scoreRight)
        {
            if (totalGoals >= 3)
            {
                gameOver();
                if (scoreLeft == scoreRight)
                    middleText.setText("Draw!");
                else if (scoreLeft > scoreRight)
                {
                    middleText.setText(String.format("%s wins!", leftPlayerNameField.getText()));
                } else
                {
                    middleText.setText(String.format("%s wins!", rightPlayerNameField.getText()));
                }

                addPlayerScoreToListView(leftPlayerNameField.getText(), scoreLeft);
                addPlayerScoreToListView(rightPlayerNameField.getText(), scoreRight);


            }


        }

        @Override
        public void gameOver()
        {
            stopGame();
        }

    }


    @FXML
    private void addPlayerScoreToListView(String name, int scoreValue)
    {
        ScoreListViewItem score = new ScoreListViewItem(scoreValue, name);
        scores.add(score);
        Collections.sort(scores, (o1, o2) -> Integer.compare(o2.getScore(), o1.getScore()));
        highScores.setItems(FXCollections.observableList(scores));
    }

    @FXML
    private void highScore()
    {
        Set<ScoreListViewItem> tempSet = new HashSet<>(scores);
        scores.clear();
        scores.addAll(tempSet);
        highScores.setItems(FXCollections.observableList(scores));
    }

    @FXML
    private void load()
    {
        scores.clear();
        scores.addAll(scoreDAO.load());
        highScores.setItems(FXCollections.observableList(scores));
    }

    @FXML
    private void save()
    {
        scoreDAO.save(scores);
    }


    public static void saveTournamentAsJSON(Game game) {

        try (OutputStream os = new FileOutputStream("tournament.xml")) {
            JAXBContext context = JAXBContext.newInstance(Game.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(game, System.out);
            marshaller.marshal(game, os);

        } catch (JAXBException | IOException exc) {
            exc.printStackTrace();
        }
    }



    public static Game loadTournamentFromXML() {

        try (InputStream is = new FileInputStream("game.xml")) {
            JAXBContext context = JAXBContext.newInstance(Game.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Game result = (Game) unmarshaller.unmarshal(is);

        } catch (JAXBException | IOException exc) {
            exc.printStackTrace();
        }
        return null;
    }
}
