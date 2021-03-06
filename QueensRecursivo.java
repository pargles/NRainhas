import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Algoritmo recursivo obtido em:
 * @reference http://introcs.cs.princeton.edu/java/23recursion/Queens.java.html
 * do livro "Introduction to Programming in Java: An Interdisciplinary Approach"
 * para efeitos de comparacao
 */
public class QueensRecursivo {
	private static FileWriter arquivo;
	private static String nomeArquivo;

	public QueensRecursivo(int rainhas, String arquivo) throws IOException {
		nomeArquivo = arquivo;
		enumerate(rainhas);
	}

	/***********************************************************************
	 * Return true if queen placement q[n] does not conflict with other queens
	 * q[0] through q[n-1]
	 ***********************************************************************/
	public static boolean isConsistent(int[] q, int n) {
		for (int i = 0; i < n; i++) {
			if (q[i] == q[n])
				return false; // same column
			if ((q[i] - q[n]) == (n - i))
				return false; // same major diagonal
			if ((q[n] - q[i]) == (n - i))
				return false; // same minor diagonal
		}
		return true;
	}

	/***********************************************************************
	 * Print out N-by-N placement of queens from permutation q in ASCII.
	 ***********************************************************************/
	public static void printQueens(int[] q) throws IOException {
		String solution;
		solution = Arrays.toString(q).replace("[", "").replace("]", "")
				.replaceAll(", ", " ");
		putboard(solution);
	}

	/**
	 * Sincronizador para salvar no arquivo os vetores com as solucções
	 * 
	 * @param String
	 *            solution
	 * @return void
	 */
	public static void putboard(String solution) throws IOException {
		arquivo = new FileWriter(nomeArquivo, true);
		BufferedWriter bw = new BufferedWriter(arquivo);
		bw.write(solution);
		bw.newLine();
		bw.close();
	}

	/***********************************************************************
	 * Try all permutations using backtracking
	 ***********************************************************************/
	public static void enumerate(int N) throws IOException {
		int[] a = new int[N];
		long tempoInicio = System.currentTimeMillis();
		enumerate(a, 0);
		System.out.println("tempo necessario: "
				+ (System.currentTimeMillis() - tempoInicio) / 1000
				+ " segundos");
	}

	public static void enumerate(int[] q, int n) throws IOException {
		int N = q.length;

		if (n == N)
			printQueens(q);
		else {
			for (int i = 0; i < N; i++) {
				q[n] = i;
				if (isConsistent(q, n))
					enumerate(q, n + 1);
			}
		}

	}

}
