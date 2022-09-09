import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
	public static void main(String[] args) {
		// StdOut.println();
		String str = null, champ = null;
		int i = 1;
		while (!StdIn.isEmpty()) {
			str = StdIn.readString();
			boolean b = StdRandom.bernoulli(1.0 / i);
			// StdOut.println("\nRead Word : " + str + ", Probability = " + b);
			if (b)
				champ = str;
			i++;
		}
		StdOut.println(champ);
	}
}