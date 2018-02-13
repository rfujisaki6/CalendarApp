
public class Position {
	private int yStart;
	private int yEnd;
	private int row;
	
	Position(int x, int y, int z)
	{
		yStart = x;
		yEnd = y;
		row = z;
	}
	
	public int getStart() { return yStart; }
	public int getEnd() { return yEnd; }
	public int getRow() { return row; }
}
