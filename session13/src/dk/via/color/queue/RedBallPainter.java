package dk.via.color.queue;

public class RedBallPainter implements BallpitWriter {
	@Override
	public void write(BallPit pit) {
		pit.paintBallRed();
	}
}
