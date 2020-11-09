package dk.via.color;

public class BallpitAccessManager {
	private BallPit pit;
	
	public BallpitAccessManager(BallPit pit) {
		this.pit = pit;
	}

	public void read(BallPitReader reader) {
		reader.read(pit);
	}
	
	public void write(BallpitWriter writer) {
		writer.write(pit);
	}
}
