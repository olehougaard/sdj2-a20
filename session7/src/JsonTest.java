import com.google.gson.Gson;

import server.Request;

public class JsonTest {
	public static void main(String[] args) {
		Gson gson = new Gson();
		Request request = new Request("Hallo", true);
		String json = gson.toJson(request);
		System.out.println(json);
		Request result = gson.fromJson(json, Request.class);
		System.out.println(result);
	}
}
