package dk.via.color.queue;

public class RedBallPrinter implements BallPitReader {
	@Override
	public void read(BallPit pit) {
		System.out.println(pit.getRedBalls() + " red balls out of " + pit.getTotal());
	}
}
