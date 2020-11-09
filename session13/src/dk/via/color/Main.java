package dk.via.color;

public class Main {
	public static void main(String[] args) {
		ReaderpreferredBallpitAccessManager pit = new ReaderpreferredBallpitAccessManager(10000);
		RedBallPainter writer = new RedBallPainter();
		Thread writeThread = new Thread(() -> {
			try {
				Thread.sleep(1);
				while (true) {
					pit.write(writer);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		RedBallPrinter reader = new RedBallPrinter();
		Thread readThread = new Thread(() -> {
			int redBalls = pit.read(reader);
			while (redBalls < 10000) {
				redBalls = pit.read(reader);
			}
		});
		readThread.start();
		writeThread.start();
	}
}
