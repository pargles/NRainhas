import java.io.FileWriter;
import java.io.IOException;


public class paraleloQueens extends Thread {
    private FileWriter arquivo;
    private int rainhas;
  private int[] vetorPosicoes;
  private  int s = 0;
  int limiteSuperior;
  int limiteInferior;
  public paraleloQueens(int i, int j, int rainhas) throws IOException
    {
      limiteInferior = i;
      limiteSuperior = j;
      this.rainhas= rainhas;
      vetorPosicoes= new int[this.rainhas];
      arquivo = new FileWriter("out.txt",true);//true é para nao sobrescrever quando vai escrever algo
  }



  boolean unsafe(int colunaAtual) {
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

  public synchronized void putboard() throws IOException {
    System.out.println("\n\nSolution " + (++s));
    System.out.print("Vetor: ");
    
    for(int i=0;i<vetorPosicoes.length;i++)
    {
        System.out.print(vetorPosicoes[i]+" ");
        arquivo.write(vetorPosicoes[i]+"");
    }
    System.out.println();
    
    arquivo.write("\n");
    arquivo.close();

    /*
    for (int y = 0; y < n; y++) {
      for (int x = 0; x < n; x++) {
        System.out.print((b[y] == x) ? "|Q" : "|_");
      }
      System.out.println("|");
    }*/
  }

    @Override
  public void run()
    {
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
                    try { putboard();} catch (IOException ex)
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

  public static void main(String[] args) throws IOException {
       int rainhas = Integer.parseInt(args[0]);
      int processadores=1/*Runtime.getRuntime().availableProcessors()*/,tarefas=0,inferior=0,superior=0;
      System.out.println("processadores disponiveis: "+processadores);
      System.out.println("rainhas: "+rainhas);

      if(processadores > rainhas)
      {
          processadores = rainhas;
      }
      else
      {
          tarefas = rainhas / processadores;
      }
      int contTarefas= rainhas;
      int escalonador[]= new int[processadores];
      for(int i=0;i<processadores;i++)
      {
          escalonador[i]=tarefas;
          contTarefas-=tarefas;
      }
      if(contTarefas > 0)
      {
          for(int i=0;i<contTarefas;i++)
          {
              escalonador[i]++;
          }
      }
     System.out.print("ESCALONAMENTO: ");
      for(int i=0;i<escalonador.length;i++)
      {
          System.out.print(escalonador[i]+ " ");
      }
     System.out.println();
    
    paraleloQueens rainha;
    for(int i = 0 ; i< processadores;i++)
    {
        superior += escalonador[i];
        rainha = new paraleloQueens(inferior,superior,rainhas);
        rainha.start();
        inferior= superior;

    }
    
  }
}
