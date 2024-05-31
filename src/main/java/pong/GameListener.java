package pong;

public interface GameListener {
	
	void stateChanged(int totalGoals, int scoreLeft, int scoreRight);
	
	void gameOver();
}
