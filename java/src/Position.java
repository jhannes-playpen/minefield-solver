
public class Position {

	public final int y;
	public final int x;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Position<x=" + x + ",y=" + y + ">";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Position)) return false;
		Position other = (Position)obj;
		return other.x == x && other.y == y;
	}
	
	@Override
	public int hashCode() {
		return x ^ y;
	}

}
