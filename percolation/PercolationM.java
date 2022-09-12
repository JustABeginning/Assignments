public class PercolationM {
	private int n;
	private boolean[][] site;
	private int[] id;
	private int[] sz;

	public PercolationM(int n) {
		if (n <= 0)
			throw new IllegalArgumentException();
		this.n = n;
		int k = -1, nM = n - 1, nS = n * n, nSP = nS + 1, noN = nSP + 1;
		site = new boolean[n][n];
		id = new int[noN];
		sz = new int[noN];
		id[0] = 0;
		id[nSP] = nSP;
		sz[0] = n;
		sz[nSP] = n;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				site[i][j] = false;
				k = i * n + (j + 1);
				if (i == 0)
					id[k] = 0;
				else if (i == nM)
					id[k] = nSP;
				else
					id[k] = k;
				sz[k] = 1;
			}
		}
	}

	public void open(int row, int col) {
		if (isOpen(row, col))
			return;
		int rM = row - 1, cM = col - 1, rP = row + 1, cP = col + 1, x = rM * n + col, y = -1;
		site[rM][cM] = true;
		System.out.println("Opened : (" + row + "," + col + ")");
		if (isValid(rM, col) && isOpen(rM, col)) {
			y = (rM - 1) * n + col;
			if (!isConnected(x, y)) {
				union(x, y);
				System.out.println("(" + row + "," + col + ")" + " -> " + "(" + rM + "," + col + ")");
			}
		}
		if (isValid(rP, col) && isOpen(rP, col)) {
			y = row * n + col;
			if (!isConnected(x, y)) {
				union(x, y);
				System.out.println("(" + row + "," + col + ")" + " -> " + "(" + rP + "," + col + ")");
			}
		}
		if (isValid(row, cM) && isOpen(row, cM)) {
			y = rM * n + cM;
			if (!isConnected(x, y)) {
				union(x, y);
				System.out.println("(" + row + "," + col + ")" + " -> " + "(" + row + "," + cM + ")");
			}
		}
		if (isValid(row, cP) && isOpen(row, cP)) {
			y = rM * n + cP;
			if (!isConnected(x, y)) {
				union(x, y);
				System.out.println("(" + row + "," + col + ")" + " -> " + "(" + row + "," + cP + ")");
			}
		}
	}

	public boolean isOpen(int row, int col) {
		if (!isValid(row, col))
			throw new IllegalArgumentException();
		return site[row - 1][col - 1];
	}

	public boolean isFull(int row, int col) {
		return isOpen(row, col) && isConnected((row - 1) * n + col, 0);
	}

	public int numberOfOpenSites() {
		int c = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (site[i][j])
					c++;
			}
		}
		return c;
	}

	public boolean percolates() {
		if (isConnected(n * n + 1, 0)) {
			System.out.println("\nSystem PERCOLATED !");
			return true;
		} else
			return false;
	}

	private boolean isValid(int r, int c) {
		return (r >= 1 && r <= n) && (c >= 1 && c <= n);
	}

	private int root(int i) {
		while (i != id[i]) {
			id[i] = id[id[i]];
			i = id[i];
		}
		return i;
	}

	private boolean isConnected(int p, int q) {
		int i = root(p), j = root(q);
		System.out.println("root(" + p + ") = " + i + ", root(" + q + ") = " + j);
		return i == j;
	}

	private void union(int p, int q) {
		int i = root(p), j = root(q);
		if (sz[i] > sz[j]) {
			id[j] = i;
			sz[i] += sz[j];
		} else {
			id[i] = j;
			sz[j] += sz[i];
		}
	}
}
