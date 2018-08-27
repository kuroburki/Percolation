
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {	

	private final int N; // no. of rows in grid
	private WeightedQuickUnionUF grid; // holds entire grid
	private boolean[] gridState;  // 1d representation of grid state
	private int numOpenSites;  // count of open sites
	
	public Percolation(int n) {
		// TODO Create NxN grid, with all cells blocked
		N=n;
		if (N <= 0) {
			throw new IllegalArgumentException();
		}
		
		grid=new WeightedQuickUnionUF(N*N+2);
		gridState=new boolean[N*N+2];
		
		for (int i=1; i<(N*N+1); i++ )
		{
			grid.union(i, i);
			gridState[i]=false;
		}
		
		gridState[0]=true;
		gridState[N*N+1]=true;
		numOpenSites=0;
	}
	
	private int gridCoordsToIndex(int row, int col) {
		// return 2D grid coordinate as index into the 1d grid array
		// grid co-ords start with (1,1) and not (0,0)
		CheckCoordsValid(row, col);
		return N*(row-1)+col;
	}
	
	public void open(int row, int col) {
		// TODO open site (row, col) if it is not open already
		
		int idx = gridCoordsToIndex(row, col);
		
		if (isOpen(row, col))
			return;
		
		// Check and connect to open neighboring sites (Left, Right, Up, Down, not diagonal)
		// Boundaries : r=1 or r=N, c=1 or c=N
		if (row > 1 && isOpen(row-1, col)) {
			grid.union(idx, gridCoordsToIndex(row-1, col));
		} 
		if (col > 1 && isOpen(row, col-1)) {
			grid.union(idx, gridCoordsToIndex(row, col-1));
		}
		if (col < N && isOpen(row, col+1)) {
			grid.union(idx, gridCoordsToIndex(row, col+1));
		}
		if (row < N && isOpen(row+1, col)) {
			grid.union(idx, gridCoordsToIndex(row+1, col));
		}
		
		if (row == 1) {
			grid.union(idx, 0);
		}
		
		if (row == N) {
			grid.union(idx, N*N+1);
		}
		
		gridState[idx]=true;
		numOpenSites++;
		
		return;
		
	}

	private void CheckCoordsValid(int row, int col) {
		if (row < 1 || row > N || col < 1 || col > N) {
			throw new IllegalArgumentException();
		}
	}
	
	public boolean isOpen(int row, int col) {
		//TODO is site (row, col) open?
		return gridState[gridCoordsToIndex(row, col)];
	}
	
	public boolean isFull(int row, int col) {
		// TODO is site (row, col) full?
		// Full site : A site is full if it is connected to the top row
		return grid.connected(0, gridCoordsToIndex(row, col));
	}

	public int numberOfOpenSites() {
		// TODO number of open sites
		return numOpenSites;
	}

	public boolean percolates() {
		// TODO does the system percolate?
		// Are top and bottom virtual sites connected?
		return grid.connected(0, N*N+1);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StdOut.println(1 + 5 + " Hello World!");
		Percolation grid = new Percolation(3);
		grid.open(1, 2);
		grid.open(2, 2);
		grid.open(2, 3);
		grid.open(3, 3);
		
		if (grid.percolates()) {
			StdOut.println("Grid percolates!");
		} else {
			StdOut.println("Grid does not percolate!");
		}
		
		int[] arr = new int[] {1, 2, 3 };
		for(int i:arr) {
			StdOut.println(i);
		}
	}
	
}
