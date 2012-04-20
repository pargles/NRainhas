import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class QueensRecursivo
{
    private static FileWriter arquivo;
    private int rainhas;

    public QueensRecursivo(int rainhas) throws IOException
    {
       this.rainhas = rainhas;
       enumerate(rainhas);
    }

   /***********************************************************************
    * Return true if queen placement q[n] does not conflict with
    * other queens q[0] through q[n-1]
    ***********************************************************************/
    public static boolean isConsistent(int[] q, int n) {
        for (int i = 0; i < n; i++) {
            if (q[i] == q[n])             return false;   // same column
            if ((q[i] - q[n]) == (n - i)) return false;   // same major diagonal
            if ((q[n] - q[i]) == (n - i)) return false;   // same minor diagonal
        }
        return true;
    }

   /***********************************************************************
    * Print out N-by-N placement of queens from permutation q in ASCII.
    ***********************************************************************/
    public static void printQueens(int[] q) throws IOException {
        String solution;
        solution = Arrays.toString(q).replace("[", "").replace("]","").replaceAll(", ","");
        putboard(solution);
        /*
        int N = q.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
				
                if (q[i] == j) //System.out.print("Q ");
                else           //System.out.print("* ");
            }
            //System.out.println();
        }
        *///System.out.println();
    }

   /* Metodo sincronizado para salva no arquivo os vetores com as solucoes possiveis
   * @param String solution
   * @return void
   */
  public static void putboard(String solution) throws IOException
  {
      arquivo = new FileWriter("out.txt", true);// true é para nao
      BufferedWriter bw = new BufferedWriter(arquivo);
      //System.out.println("\n\nSolution " + (++s));
      //System.out.print("Vetor: " + solution+"\n");
      bw.write(solution);
      bw.newLine();
      bw.close();
   }


   /***********************************************************************
    *  Try all permutations using backtracking
    ***********************************************************************/
    public static void enumerate(int N) throws IOException {
        int[] a = new int[N];
        enumerate(a, 0);
    }

    public static void enumerate(int[] q, int n) throws IOException {
        int N = q.length;
        if (n == N) printQueens(q);
        else {
            for (int i = 0; i < N; i++) {
                q[n] = i;
                if (isConsistent(q, n)) enumerate(q, n+1);
            }
        }
    }

}
