package no.anderska.minesolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class MineSolverApi {
	
	private static final String ADDRESS="http://localhost:1337/"; // Replace this
	private final static int MY_ID = 1; // Replace this
	
	public static class UnexpectedResultException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		public UnexpectedResultException(String message) {
			super(message);
		}
		
	}
	
	public static class OpenResult {
		private final int x;
		private final int y;
		private final int count;

		public OpenResult(String requestReult) {
			super();
			String[] parts = requestReult.split(",");
			if (parts.length != 3) {
				throw new UnexpectedResultException(requestReult);
			}
			y = Integer.parseInt(parts[0].substring("Y=".length()));
			x = Integer.parseInt(parts[1].substring("X=".length()));
			count = Integer.parseInt(parts[2].substring("result=".length()));
		}
		
		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
		
		public int getCount() {
			return count;
		}

	}
	
	private String readUrlWithEx(String urladdr) throws IOException {
		URL url = new URL(urladdr);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {

		    
			StringBuilder result = new StringBuilder();
					
		    String str;
		    
		    while ((str = in.readLine()) != null) {
		    	result.append(str);
		    }
		    return result.toString();
		}
	}
	
	
	private String readStringFromUrl(String urladdr) {
		try {
			return readUrlWithEx(urladdr);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public OpenResult hint() {
		String result = readStringFromUrl(ADDRESS + "hint?id=" + MY_ID);
		return new OpenResult(result);
	}
	
	public OpenResult open(int x, int y) {
		String url = ADDRESS + "open?id=" + MY_ID + "&x=" + x + "&y=" + y;
		String result = readStringFromUrl(url);
		return new OpenResult(result);
	}
	
	

}
