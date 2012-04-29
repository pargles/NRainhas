import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author abilio and pargles
 * @version 4.2
 */
public class paraleloNRainhas extends Thread {
	private static  FileWriter arquivo;
    private int[] vetorPosicoes;
    private  static int s = 0;
    int limiteSuperior;
    int limiteInferior;
    int rainhas;
    private static String nomeArq;

    /* Metodo contrutor da classe recebe a quantidade de rainhas e
     * os limites de cada thread (quantidade de linhas para cada thread)
     * @param int i, int j , int rainhas
     * @return void
     */
    public paraleloNRainhas(int limInferior, int limSuperior, int rainhas,String arquivo) throws IOException
    {
      limiteInferior = limInferior;
      limiteSuperior = limSuperior;
      this.rainhas= rainhas;
      vetorPosicoes= new int[rainhas];
      nomeArq = arquivo;
    }

  /* Metodo sincronizado para salva no arquivo os vetores com as solucoes possiveis
   * @param String solution
   * @return void
   */
  public static synchronized void putboard(String solution) throws IOException
  {
      arquivo = new FileWriter(nomeArq, true);// true é para nao
      //sobrescrever quando vai
      // escrever algo
      BufferedWriter bw = new BufferedWriter(arquivo);
      //System.out.println("\n\nSolution " + (++s));
      //System.out.print("Vetor: " + solution+"\n");
      bw.write(solution);
      bw.newLine();
      bw.close();
  }

    /* Metodo responsavel pela execucao, vai colocando as rainhas em cada posicao do tabuleiro
     * ate encontrar uma solucao
     * @param void
     * @return void     *
     */
    @Override
  public void run()
    {
        String solution;
        long tempoInicio= System.currentTimeMillis();
        int posicaoAtual = 0;
        vetorPosicoes[0] = limiteInferior-1;
        while (posicaoAtual >= 0 && vetorPosicoes[0]<limiteSuperior)
        {
            do
            {
                vetorPosicoes[posicaoAtual]++;

            } while ((vetorPosicoes[posicaoAtual] < rainhas) && existeConflito(posicaoAtual)); // ese laco pesquisa onde colocar a proxima peca sem que haja conflito


            if (vetorPosicoes[posicaoAtual] < rainhas)
            {
                if (posicaoAtual < rainhas-1)
                {
                    vetorPosicoes[++posicaoAtual] = -1;
                }
                else
                {
                    solution = Arrays.toString(vetorPosicoes).replace("[", "").replace("]","").replaceAll(", "," ");
                    try { putboard(solution);} catch (IOException ex)
                            { System.err.println("Problema ao abrir arquivo");}
                }
            }
            else
            {
                posicaoAtual--;
            }
        }
        System.out.println("tempo necessario: "+(System.currentTimeMillis()-tempoInicio) / 1000 +" segundos");

  }

   /* Metodo que avalia se existe conflito entre rainhas
    * @param int coluna Atual
    * @return boolean
    */
  boolean existeConflito(int colunaAtual)
  {
    int x = vetorPosicoes[colunaAtual];// y é a coluna que eu estou no momento
    for (int i = 1; i <= colunaAtual; i++) // i = 1 pois y-i sempre pega o elemento anterior da coluna
    {
        int elementoAnterior = vetorPosicoes[colunaAtual - i]; // sempre pega o elemento anterior a posicao y no vetor b
        if (elementoAnterior == x || elementoAnterior == x - i || elementoAnterior == x + i) // x e a posicao atual do elemento
        {
            return true;
        }
    }

    return false;
  }

}


