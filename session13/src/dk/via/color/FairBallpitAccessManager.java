package dk.via.color;

public class FairBallpitAccessManager {
	private BallPit pit;
	private int readers;
	private int writers;
	private int current;
	private int nextInLine;
	
	public FairBallpitAccessManager(int count) {
		this.pit = new BallPit(count);
		this.readers = 0;
		this.writers = 0;
		this.current = 0;
		this.nextInLine = 0;
	}

	public<T> T read(BallPitReader<T> reader) {
		requestRead();
		T returnValue = reader.read(pit);
		releaseRead();
		return returnValue;
	}

	private void releaseRead() {
		synchronized(this) {
			readers--;
			if (readers == 0) notifyAll();
		}
	}

	private void requestRead() {
		synchronized(this) {
			int myNumber = nextInLine;
			nextInLine++;
			while(myNumber != current) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			while(writers > 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			readers++;
			current++;
		}
	}
	
	public void write(BallpitWriter writer) {
		requestWrite();
		writer.write(pit);
		releaseWrite();
	}

	private void releaseWrite() {
		synchronized(this) {
			writers--;
			if (writers == 0) notifyAll();
		}
	}

	private void requestWrite() {
		synchronized(this) {
			int myNumber = nextInLine;
			nextInLine++;
			while(myNumber != current) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			while(writers > 0 || readers > 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			writers++;
			current++;
		}
	}
}
