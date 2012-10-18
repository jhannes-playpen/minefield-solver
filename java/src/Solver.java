import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class Solver {

	public static void main(String[] args) throws IOException {
		new Solver("http://localhost:1337", "223615").solve();
	}

	private final String baseUrl;
	private final String id;
	private final int height = 16;
	private final int width = 30;
	private final int mines = 99;
	private int remainingFreeCells;
	private final CellStatus[][] cellStatus = new CellStatus[width][height];
	private final int[][] hints = new int[width][height];

	public Solver(String baseUrl, String id) {
		this.baseUrl = baseUrl;
		this.id = id;
		this.remainingFreeCells = height*width - mines;
	}

	private void solve() throws IOException {
		while (remainingFreeCells > 0) {
			String response = readUrl(new URL(baseUrl + "/hint?id=" + id));
            System.out.println(response);
            openedWithHint(response);

            List<Position> safePositions;
            while (!(safePositions = getSafeCells()).isEmpty()) {
                for (Position pos : safePositions) {
                    if (getStatus(pos) == CellStatus.opened) continue;
                    System.out.println("Safe position " + pos);
                    response = readUrl(new URL(baseUrl + "/open?id=" + id + "&x=" + pos.x + "&y=" + pos.y));
                    System.out.println(response);
                    openedWithHint(response);
                }
            }
		}
	}

	void openedWithHint(String response) {
	    int x = Integer.parseInt(response.split(",")[1].split("=")[1]);
	    int y = Integer.parseInt(response.split(",")[0].split("=")[1]);
	    int hint = Integer.parseInt(response.split(",")[2].split("=")[1]);
	    opened(new Position(x, y), hint);
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

	public void opened(Position position, int hint) {
		this.cellStatus[position.x][position.y] = CellStatus.opened;
		this.hints[position.x][position.y] = hint;
		remainingFreeCells--;
	}

	public List<Position> getSafeCells() {
		ArrayList<Position> result = new ArrayList<>();
		for (Position position : getWholeBoard()) {
            if (getStatus(position) == CellStatus.opened && getHint(position) == 0) {
                result.addAll(getNeighboursWithStatus(position, CellStatus.unknown));
            }
        }
		return result;
	}

    private ArrayList<Position> getWholeBoard() {
        ArrayList<Position> wholeBoard = new ArrayList<>();
		for (int cellX=0; cellX<width; cellX++) {
			for (int cellY=0; cellY<height; cellY++) {
			    wholeBoard.add(new Position(cellX, cellY));
			}
		}
        return wholeBoard;
    }

    private List<Position> getNeighboursWithStatus(Position pos, CellStatus cellStatus) {
        List<Position> neighbours = new ArrayList<>();
        for (int x=pos.x-1;x<=pos.x+1; x++) {
        	for (int y=pos.y-1;y<=pos.y+1; y++) {
        		if (x==pos.x && y==pos.y) continue;
        		if (x<0 || y<0) continue;
        		if (x>=width || y>=height) continue;
        		Position neighbour = new Position(x, y);
        		if (getStatus(neighbour) != cellStatus) continue;
                neighbours.add(neighbour);
        	}
        }
        return neighbours;
    }

    public int getRemainingFreeCells() {
        return remainingFreeCells;
    }

    public CellStatus getStatus(Position position) {
        CellStatus status = cellStatus[position.x][position.y];
        return status != null ? status : CellStatus.unknown;
    }

    public int getHint(Position position) {
        return hints[position.x][position.y];
    }

}
