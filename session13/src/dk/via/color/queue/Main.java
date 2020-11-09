package dk.via.color.queue;

public class Main {
	public static void main(String[] args) {
		BallPit pit = new BallPit(10000);
		BallpitAccessManager manager = new BallpitAccessManager(pit);
		BallpitWriter writer = new RedBallPainter();
		Thread writeThread = new Thread(() -> {
			try {
				Thread.sleep(1);
				while (pit.getGreenBalls() > 0) {
					manager.write(writer);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		BallPitReader reader = new RedBallPrinter();
		Thread readThread = new Thread(() -> {
			while (pit.getGreenBalls() > 0) {
				manager.read(reader);
			}
		});
		readThread.start();
		writeThread.start();
	}
}
