import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationT {
    private int n, nOpen;
    private boolean[] site;
    private WeightedQuickUnionUF wquf;

    public PercolationT(int n) {
        if (n <= 0)
            throw new IllegalArgumentException();
        this.n = n;
        int nS = n * n, nSP = nS + 1, noN = nSP + 1, nSPM = nSP - n;
        wquf = new WeightedQuickUnionUF(noN);
        site = new boolean[noN];
        site[0] = true;
        site[nSP] = true;
        nOpen = 0;
        for (int i = 1; i <= nS; i++) {
            site[i] = false;
            if (i <= n)
                wquf.union(0, i);
            if (i >= nSPM)
                wquf.union(nSP, i);
        }
    }

    public void open(int row, int col) {
        if (isOpen(row, col))
            return;
        int rM = row - 1, cM = col - 1, rP = row + 1, cP = col + 1, x = rM * n + col, y = -1;
        site[x] = true;
        nOpen++;
        System.out.println("Opened : (" + row + "," + col + ")");
        if (isValid(rM, col) && isOpen(rM, col)) {
            y = (rM - 1) * n + col;
            if (!isConnected(x, y)) {
                wquf.union(x, y);
                System.out.println("(" + row + "," + col + ")" + " -> " + "(" + rM + "," + col + ")");
            }
        }
        if (isValid(rP, col) && isOpen(rP, col)) {
            y = row * n + col;
            if (!isConnected(x, y)) {
                wquf.union(x, y);
                System.out.println("(" + row + "," + col + ")" + " -> " + "(" + rP + "," + col + ")");
            }
        }
        if (isValid(row, cM) && isOpen(row, cM)) {
            y = rM * n + cM;
            if (!isConnected(x, y)) {
                wquf.union(x, y);
                System.out.println("(" + row + "," + col + ")" + " -> " + "(" + row + "," + cM + ")");
            }
        }
        if (isValid(row, cP) && isOpen(row, cP)) {
            y = rM * n + cP;
            if (!isConnected(x, y)) {
                wquf.union(x, y);
                System.out.println("(" + row + "," + col + ")" + " -> " + "(" + row + "," + cP + ")");
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException();
        return site[(row - 1) * n + col];
    }

    public boolean isFull(int row, int col) {
        return isOpen(row, col) && isConnected((row - 1) * n + col, 0);
    }

    public int numberOfOpenSites() {
        return nOpen;
    }

    public boolean percolates() {
        System.out.println("Checking Percolation ...");
        if (isConnected(n * n + 1, 0)) {
            System.out.println("\nSystem PERCOLATED !");
            return true;
        } else
            return false;
    }

    public int getCount() {
        return wquf.count();
    }

    private boolean isValid(int r, int c) {
        return (r >= 1 && r <= n) && (c >= 1 && c <= n);
    }

    private boolean isConnected(int p, int q) {
        int i = wquf.find(p), j = wquf.find(q);
        System.out.println("find(" + p + ") = " + i + ", find(" + q + ") = " + j);
        return i == j;
    }
}
