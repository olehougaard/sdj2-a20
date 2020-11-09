package dk.via.color;

public class Main {
	public static void main(String[] args) {
		BallPit pit = new BallPit(10000);
		Thread writeThread = new Thread(() -> {
			try {
				Thread.sleep(1);
				while (pit.getGreenBalls() > 0) {
					pit.paintBallRed();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		Thread readThread = new Thread(() -> {
			int redBalls = pit.getRedBalls();
			while (redBalls < 10000) {
				System.out.println(pit.getRedBalls() + " red balls out of " + pit.getTotal());
				redBalls = pit.getRedBalls();
			}
		});
		readThread.start();
		writeThread.start();
	}
}
