package dk.via.color;

public class RedBallPainter implements BallpitWriter {
	@Override
	public void write(BallPit pit) {
		pit.paintBallRed();
	}
}
