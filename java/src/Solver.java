import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;


public class Solver {

	private final String baseUrl;
	private final String id;
	private final int height = 16;
	private final int width = 30;
	private final int mines = 99;
	private int remainingCells;

	public Solver(String baseUrl, String id) {
		this.baseUrl = baseUrl;
		this.id = id;
		this.remainingCells = height*width - mines;
	}

	private void solve() throws IOException {
		while (remainingCells > 0) {
			System.out.println(readUrl(new URL(baseUrl + "/hint?id=" + id)));
			remainingCells--;
		}
	}

	public static int parseResult(String response) {
		return 0;
	}

	private String readUrl(URL url) throws IOException {
		URLConnection conn = url.openConnection();
		Reader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		try {
			StringBuilder result = new StringBuilder();
			int c;
			while ((c = reader.read()) != -1) {
				result.append((char)c);
			}
			return result.toString();
		} finally {
			reader.close();
		}
	}


	public static void main(String[] args) throws IOException {
		new Solver("http://localhost:1337", "456111").solve();
	}


}
