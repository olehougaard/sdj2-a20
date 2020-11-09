package dk.via.color;

public class RedBallPrinter implements BallPitReader<Integer> {
	@Override
	public Integer read(BallPit pit) {
		System.out.println(pit.getRedBalls() + " red balls out of " + pit.getTotal());
		return pit.getRedBalls();
	}
}
