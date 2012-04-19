import java.io.IOException;
import java.io.FileWriter;
/**
 *
 * @author abilio and pargles
 * @version 1.0
 */
public class NQueens
{

    enum algoritmo{Sequencial, Paralelo,Recursivo;}
    private int rainhas;
    private int processadores/*Runtime.getRuntime().availableProcessors()*/,tarefas=0,inferior=0,superior=0;
    public String tipoAlgoritmo = "Paralelo";//default
    paraleloQueens queen;
    private int escalonador[];
    private String nomeArquivo = "out.txt";
    FileWriter arquivo = new FileWriter(nomeArquivo);

    public NQueens()throws IOException
    {
        this.rainhas= 11;//Integer.parseInt(args[0]);
        processadores=2/*Runtime.getRuntime().availableProcessors()*/;
        System.out.println("processadores disponiveis: "+processadores);
        System.out.println("rainhas: "+rainhas);
        escalonador= new int[processadores];
    }

    /* Metodo responsavel pela selecao do algoritmo e sua execucao
     * esse metodo so sera chamando por um evento de botao na interface
     * @param void
     * @return void
     */
    public void executa() throws Exception
    {
        switch(algoritmo.valueOf(tipoAlgoritmo))
        {
            case Sequencial:
                queen = new paraleloQueens(0,rainhas,rainhas,nomeArquivo);//apenas instancia uma thread, ou seja, se torna sequencial
                queen.start();
                break;
            case Paralelo:
                escalonarTarefas();//divide a quantidade de rainhas entre as threads
                printaEscalonamento();
                iniciarTarefas();//cria N (quantidade de processadores dinsponiveis) threads e manda executa
                break;

            case Recursivo:
                break;

        }
    }

    /* Metodo responsavel por gerar um vetor contendo o numero
     * de linhas que cada thread devera tentar econtrar as solucoes
     * @param void
     * @return void
     */
    public void escalonarTarefas()
    {
        if(processadores > rainhas)
        {
            processadores = rainhas;
        }
        else
        {
            tarefas = rainhas / processadores;
        }
          int contTarefas= rainhas;
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


    }

    /* Metodo que cria N ( quantidade de processadores disponiveis) threads
     * e coloca elas para rodar
     * @param void
     * @return void
     */
    private void iniciarTarefas() throws IOException
    {
         paraleloQueens rainha;
        for(int i = 0 ; i< processadores;i++)
        {
            superior += escalonador[i];
            queen = new paraleloQueens(inferior,superior,rainhas,nomeArquivo);
            queen.start();
            inferior= superior;
        }
    }

    /* Metodo apenas para debug
     * @param void
     * @return void
     */
    public void printaEscalonamento()
    {
        System.out.print("ESCALONAMENTO: ");
        for(int i=0;i<escalonador.length;i++)
        {
            System.out.print(escalonador[i]+ " ");
        }
        System.out.println();
    }

    /* Metodo get para o nome do arquivo para ser salvo as solucoes
     * @param void
     * @return String nomeArquivo
     */
    public String getNomeArq()
    {
        return nomeArquivo;
    }

}
