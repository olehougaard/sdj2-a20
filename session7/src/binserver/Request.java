package binserver;

import java.io.Serializable;

public class Request implements Serializable {
	private static final long serialVersionUID = -4536185512591452015L;
	private String original;
	private boolean reverse;

	public Request(String original, boolean reverse) {
		this.original = original;
		this.reverse = reverse;
	}

	public String getOriginal() {
		return original;
	}

	public boolean isReverse() {
		return reverse;
	}
	
	@Override
	public String toString() {
		if (reverse) {
			return String.format("reverse(%s)", original); 
		} else {
			return String.format("echo(%s)", original);
		}
	}
}
