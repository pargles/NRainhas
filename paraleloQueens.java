package rainhas;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class paraleloQueens extends Thread {
    private int rainhas;
    private static  FileWriter arquivo;
    private int[] vetorPosicoes;
    private  static int s = 0;
    int limiteSuperior;
    int limiteInferior;

    public paraleloQueens(int i, int j, int rainhas) throws IOException
    {
      limiteInferior = i;
      limiteSuperior = j;
      this.rainhas= rainhas;
      vetorPosicoes= new int[this.rainhas];
      
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

  public synchronized void putboard(String solution) throws IOException {

       arquivo = new FileWriter("out.txt",true);//true é para nao sobrescrever quando vai escrever algo
       synchronized(arquivo)
       {
          
           System.out.println("\n\nSolution " + (++s));
           System.out.print("Vetor: "+solution);
           arquivo.write(solution+"\n");
           System.out.println();
           arquivo.close();
       }   
  }

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

  public static void main(String[] args) throws IOException {
       int rainhas = 13;//Integer.parseInt(args[0]);
      int processadores=2/*Runtime.getRuntime().availableProcessors()*/,tarefas=0,inferior=0,superior=0;
      System.out.println("processadores disponiveis: "+processadores);
      System.out.println("rainhas: "+rainhas);
      arquivo = new FileWriter("out.txt");//abre aki o arquivo so para limpar o conteudo do anteriormente salvo

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
