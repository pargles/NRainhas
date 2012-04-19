import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author abilio and pargles
 * @version 2.1
 */
public class paraleloQueens extends Thread {

<<<<<<< HEAD
	public paraleloQueens(int i, int j, int rainhas) throws IOException {
		limiteInferior = i;
		limiteSuperior = j;
		this.rainhas = rainhas;
		vetorPosicoes = new int[this.rainhas];

	}

	boolean unsafe(int colunaAtual) {
		int x = vetorPosicoes[colunaAtual];// y é a coluna que eu estou no
											// momento
		for (int i = 1; i <= colunaAtual; i++) // i = 1 pois y-i sempre pega o
												// elemento anterior da coluna
		{
			int elementoAnterior = vetorPosicoes[colunaAtual - i]; // sempre
																	// pega o
																	// elemento
																	// anterior
																	// a posicao
																	// y no
																	// vetor b
			if (elementoAnterior == x || elementoAnterior == x - i
					|| elementoAnterior == x + i) // x e a posicao atual do
													// elemento
			{
				return true;
			}
		}

		return false;
	}

	public synchronized void putboard(String solution) throws IOException {

		arquivo = new FileWriter("out.txt",true);// true é para nao
													// sobrescrever quando vai
													// escrever algo
		synchronized(this){
			BufferedWriter bw = new BufferedWriter(arquivo);
			System.out.println("\n\nSolution " + (++s));
			System.out.print("Vetor: " + solution);
			bw.write(solution);
			System.out.println();
			bw.newLine();
			bw.close();
		}
	}

	@Override
	public void run() {
		String solution;
		long tempoInicio = System.currentTimeMillis();

		int posicaoAtual = 0;
		vetorPosicoes[0] = limiteInferior - 1;
		while (posicaoAtual >= 0 && vetorPosicoes[0] < limiteSuperior) {
			do {
				vetorPosicoes[posicaoAtual]++;

			} while ((vetorPosicoes[posicaoAtual] < rainhas)
					&& unsafe(posicaoAtual)); // ese laco pesquisa onde colocar
												// a proxima peca sem que haja
												// conflito

			if (vetorPosicoes[posicaoAtual] < rainhas) {
				if (posicaoAtual < rainhas - 1) {
					vetorPosicoes[++posicaoAtual] = -1;
				} else {
					solution = Arrays.toString(vetorPosicoes).replace("[", "")
							.replace("]", "").replaceAll(", ", "");

					try {
						putboard(solution);
					} catch (IOException ex) {
						System.err.println("Problema ao abrir arquivo");
					}
				}
			} else {
				posicaoAtual--;
			}
		}
		System.out.println("tempo necessario: "
				+ (System.currentTimeMillis() - tempoInicio) / 100000
				+ " minutos");

	}

	public static void main(String[] args) throws IOException {
		int rainhas = 11;// Integer.parseInt(args[0]);
		int processadores = 2/* Runtime.getRuntime().availableProcessors() */, tarefas = 0, inferior = 0, superior = 0;
		System.out.println("processadores disponiveis: " + processadores);
		System.out.println("rainhas: " + rainhas);
		arquivo = new FileWriter("out.txt");// abre aki o arquivo so para limpar
											// o conteudo do anteriormente salvo

		if (processadores > rainhas) {
			processadores = rainhas;
		} else {
			tarefas = rainhas / processadores;
		}
		int contTarefas = rainhas;
		int escalonador[] = new int[processadores];
		for (int i = 0; i < processadores; i++) {
			escalonador[i] = tarefas;
			contTarefas -= tarefas;
		}
		if (contTarefas > 0) {
			for (int i = 0; i < contTarefas; i++) {
				escalonador[i]++;
			}
		}
		System.out.print("ESCALONAMENTO: ");
		for (int i = 0; i < escalonador.length; i++) {
			System.out.print(escalonador[i] + " ");
		}
		System.out.println();

		paraleloQueens rainha;
		for (int i = 0; i < processadores; i++) {
			superior += escalonador[i];
			rainha = new paraleloQueens(inferior, superior, rainhas);
			rainha.start();
			inferior = superior;

		}

	}
=======
    private static  FileWriter arquivo;
    private int[] vetorPosicoes;
    private  static int s = 0;
    int limiteSuperior;
    int limiteInferior;
    int rainhas;
    private String nomeArq;

    /* Metodo contrutor da classe recebe a quantidade de rainhas e
     * os limites de cada thread (quantidade de linhas para cada thread)
     * @param int i, int j , int rainhas
     * @return void
     */
    public paraleloQueens(int limInferior, int limSuperior, int rainhas,String arquivo) throws IOException
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
  public synchronized void putboard(String solution) throws IOException 
  {
       arquivo = new FileWriter(nomeArq,true);//true é para nao sobrescrever quando vai escrever algo
       BufferedWriter bw = new BufferedWriter(arquivo);
       bw.write(solution);
       bw.newLine();
        bw.close();
      System.out.println("\n\nSolution " + (++s));
      System.out.print("Vetor: "+solution);
      System.out.println();  
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

            } while ((vetorPosicoes[posicaoAtual] < rainhas) && unsafe(posicaoAtual)); // ese laco pesquisa onde colocar a proxima peca sem que haja conflito


            if (vetorPosicoes[posicaoAtual] < rainhas)
            {
                if (posicaoAtual < rainhas-1)
                {
                    vetorPosicoes[++posicaoAtual] = -1;
                }
                else
                {
                    solution = Arrays.toString(vetorPosicoes).replace("[", "").replace("]","").replaceAll(", ","");
                    try { putboard(solution);} catch (IOException ex)
                            { System.err.println("Problema ao abrir arquivo");}
                }
            }
            else
            {
                posicaoAtual--;
            }
        }
        System.out.println("tempo necessario: "+(System.currentTimeMillis()-tempoInicio) / 100000 +" minutos");

  }

   /* Metodo que avalia se existe conflito entre rainhas
    * @param int coluna Atual
    * @return boolean
    */
  boolean unsafe(int colunaAtual)
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
>>>>>>> 86b0a6778086e30bcdf552b092adc10db13e1834
}
