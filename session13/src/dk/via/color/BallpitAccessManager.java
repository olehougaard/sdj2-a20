package dk.via.color;

public class BallpitAccessManager {
	private BallPit pit;
	
	public BallpitAccessManager(int count) {
		this.pit = new BallPit(count);
	}

	public Integer read(RedBallPrinter reader) {
		return reader.read(pit);
	}
	
	public void write(RedBallPainter writer) {
		writer.write(pit);
	}
}
