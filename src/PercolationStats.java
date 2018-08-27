import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	
	private double[] percolation_res;
	private int nTrials;
	private int gSize;

	public PercolationStats(int n, int nTrials) {
		
		if (n <= 0 || nTrials <= 0) {
			throw new IllegalArgumentException("Provide valid input for grid size and/or num trials!");
		}
		
		this.nTrials=nTrials;
		this.gSize=n;
		this.percolation_res = new double[nTrials];
		
		for (int i=0; i<nTrials;i++) {
			Percolation grid = new Percolation(gSize);
			int row = StdRandom.uniform(1, gSize+1);
			int col = StdRandom.uniform(1, gSize+1);
			
			int j=0;
			while(!grid.percolates()) {				
				if (!grid.isOpen(row, col)) {
					grid.open(row, col);
					j=j+1;
				}
				row = StdRandom.uniform(1, gSize+1);
				col = StdRandom.uniform(1, gSize+1);
			}
			double percent = j/(double)(gSize*gSize);
			//StdOut.println("Percolates after : " + j + " runs. Percentage=" + percent);
			percolation_res[i]=percent;	
		}
		
	}
	
	public double mean() {
		return StdStats.mean(percolation_res);
	}
	
	public double stddev() {
		return StdStats.stddev(percolation_res);
	}
	
	public double confidenceLo() {
		// mean - 1.96s/sqrt(T)
		return mean() - (1.96*stddev())/(double)Math.sqrt(nTrials);
	}
	
	public double confidenceHi() {
		// mean + 1.96s/sqrt(T)
		return mean() + (1.96*stddev())/(double)Math.sqrt(nTrials);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int gridSize = 3;
		int numExperiments = 3;
		
		if (args.length !=2 ) {
			StdOut.println("Usage: PercolationStats <n:grid size> <T:# of experiments>");
			return;
		} else {
			try {
				gridSize = Integer.parseInt(args[0]);
				numExperiments = Integer.parseInt(args[1]);
			} catch (NumberFormatException nfe) {
				StdOut.println("Usage: PercolationStats <n:grid size> <T:# of experiments>");
				return;
			}
			//StdOut.println("Grid Size : " + args[0] + "\nTotal number of experiments : " + args[1]);			
		}
		
		//gridSize=50;
		//numExperiments=10000;
		PercolationStats exp = new PercolationStats(gridSize, numExperiments);
		StdOut.println("mean                    = " + exp.mean());
		StdOut.println("stddev                  = " + exp.stddev());
		StdOut.println("95% confidence interval = [" + exp.confidenceLo() + ", " + exp.confidenceHi() + "]");
	}
}
