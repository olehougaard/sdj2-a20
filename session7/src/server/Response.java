package server;

public class Response {
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
