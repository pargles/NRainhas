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
	private static FileWriter arquivo;
	private int[] vetorPosicoes;
	private static int s = 0;
	int limiteSuperior;
	int limiteInferior;
	int rainhas;
	private static String nomeArq;

	/**
	 * Contrutor da classe recebe a quantidade de rainhas e os limites de cada
	 * thread (quantidade de linhas para cada thread)
	 * 
	 * @param int i, int j , int rainhas
	 * @return void
	 */
	public paraleloNRainhas(int limInferior, int limSuperior, int rainhas,
			String arquivo) throws IOException {
		limiteInferior = limInferior;
		limiteSuperior = limSuperior;
		this.rainhas = rainhas;
		vetorPosicoes = new int[rainhas];
		nomeArq = arquivo;
	}

	/**
	 * Sincronizador para salvar no arquivo os vetores com as soluções
	 * 
	 * @param String
	 *            solution
	 * @return void
	 */
	public static synchronized void putboard(String solution)
			throws IOException {
		arquivo = new FileWriter(nomeArq, true);
		BufferedWriter bw = new BufferedWriter(arquivo);
		bw.write(solution);
		bw.newLine();
		bw.close();
	}

	/**
	 * Responsável pela execucao, vai colocando as rainhas em cada posição do
	 * tabuleiro até encontrar uma solução
	 * 
	 * @param void
	 * @return void
	 */
	@Override
	public void run() {
		String solution;
		long tempoInicio = System.currentTimeMillis();
		int posicaoAtual = 0;
		vetorPosicoes[0] = limiteInferior - 1;
		while (posicaoAtual >= 0 && vetorPosicoes[0] < limiteSuperior) {
			/*
			 * Verifica onde colocar a próxima peça sem que haja conflito
			 */
			do {
				vetorPosicoes[posicaoAtual]++;

			} while ((vetorPosicoes[posicaoAtual] < rainhas)
					&& existeConflito(posicaoAtual));

			if (vetorPosicoes[posicaoAtual] < rainhas) {
				if (posicaoAtual < rainhas - 1) {
					vetorPosicoes[++posicaoAtual] = -1;
				} else {
					solution = Arrays.toString(vetorPosicoes).replace("[", "")
							.replace("]", "").replaceAll(", ", " ");
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
				+ (System.currentTimeMillis() - tempoInicio) / 1000
				+ " segundos");

	}

	/**
	 * Avalia se existe conflito entre as rainhas
	 * 
	 * @param int coluna Atual
	 * @return boolean
	 */
	public boolean existeConflito(int colunaAtual) {
		/*
		 * y é a coluna que eu estou no momento
		 */
		int x = vetorPosicoes[colunaAtual];
		/*
		 * i = 1 pois y-i sempre pega o elemento anterior da coluna sempre pega
		 * o elemento anterior a posição y no vetor b
		 */
		for (int i = 1; i <= colunaAtual; i++) {
			int elementoAnterior = vetorPosicoes[colunaAtual - i];
			/*
			 * x e a posicao atual do elemento
			 */
			if (elementoAnterior == x || elementoAnterior == x - i
					|| elementoAnterior == x + i) {
				return true;
			}
		}

		return false;
	}

}
