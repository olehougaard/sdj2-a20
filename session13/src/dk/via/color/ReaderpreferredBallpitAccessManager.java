package dk.via.color;

public class ReaderpreferredBallpitAccessManager {
	private BallPit pit;
	private int readers;
	private int writers;
	
	public ReaderpreferredBallpitAccessManager(int count) {
		this.pit = new BallPit(count);
		this.readers = 0;
		this.writers = 0;
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
			if (readers == 0) notify();
		}
	}

	private void requestRead() {
		synchronized(this) {
			while(writers > 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			readers++;
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
			while(writers > 0 || readers > 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			writers++;
		}
	}
}
