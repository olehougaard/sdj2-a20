package binserver;

import java.io.Serializable;

public class Response implements Serializable {
	private static final long serialVersionUID = 1;
	private String original;
	private String result;
	
	public Response(String original, String result) {
		this.original = original;
		this.result = result;
	}

	public String getOriginal() {
		return original;
	}

	public String getResult() {
		return result;
	}
	
	@Override
	public String toString() {
		return String.format("orginal = %s, result = %s", original, result);
	}
}
