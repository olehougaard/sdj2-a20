package dk.via.color.queue;

public class BallPit {
	private int greenBalls;
	private int redBalls;
	
	public BallPit(int size) {
		greenBalls = size;
		redBalls = 0;
	}
	
	public int getGreenBalls() {
		return greenBalls;
	}
	
	public int getRedBalls() {
		return redBalls;
	}
	
	public int getTotal() {
		return greenBalls + redBalls;
	}
	
	public void paintBallGreen() {
		if (redBalls > 0) {
			greenBalls++;
			redBalls--;
		}
	}
	
	public void paintBallRed() {
		if (greenBalls > 0) {
			greenBalls--;
			redBalls++;
		}
	}
}
