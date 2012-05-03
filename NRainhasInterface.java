import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Cria a interface com o usuário com botões e quadro de jogo
 */
public final class NRainhasInterface extends JPanel {

	private int nrainhas;
	BufferedReader in;

	private JPanel rainhasUI, controlPanel;
	private NrainhasTabuleiro rainhasModel;
	private JLabel status;
	private JFrame res;
	private JButton iniciar, resultado;
	private ButtonGroup group;
	private JRadioButton paralelo, sequencial, recursivo;
	private JTextField entradaRainhas;
	private JList listaSolucoes;
	private DefaultListModel modeloLista;
	private JScrollPane scrollLista;
	private String solSelecionada;
	private NRainhas programa;
	private long tempoInicio, tempoTotal;

	public NRainhasInterface() throws IOException {
		programa = new NRainhas();
		iniciaComponentes();
	}

	/**
	 * Cria e insere todos os botões e ferramentas da interface
	 * 
	 * @param void
	 * @return void
	 */
	public void iniciaComponentes() {
		modeloLista = new DefaultListModel();
		listaSolucoes = new JList();

		iniciar = new JButton("Iniciar");
		iniciar.addActionListener(new Initial());

		resultado = new JButton("Mostrar Resultados");
		resultado.addActionListener(new Resultado());
		resultado.setEnabled(false);
		entradaRainhas = new JTextField();

		status = new JLabel(" Indique os Campos! ");

		paralelo = new JRadioButton("Paralelo");
		paralelo.setMnemonic(KeyEvent.VK_B);
		paralelo.setActionCommand("Paralelo");

		sequencial = new JRadioButton("Sequencial");
		sequencial.setMnemonic(KeyEvent.VK_B);
		sequencial.setActionCommand("Sequencial");

		recursivo = new JRadioButton("Recursivo");
		recursivo.setMnemonic(KeyEvent.VK_B);
		recursivo.setActionCommand("Recursivo");

		group = new ButtonGroup();
		group.add(paralelo);
		group.add(recursivo);
		group.add(sequencial);

		controlPanel = new JPanel();

		controlPanel.setLayout(new GridLayout(0, 1));

		controlPanel.add(new JLabel(" Algoritmo: "));
		controlPanel.add(paralelo);
		controlPanel.add(recursivo);
		controlPanel.add(sequencial);
		controlPanel.add(new JLabel(" Número de Rainhas: "));
		controlPanel.add(entradaRainhas);
		controlPanel.add(iniciar);
		controlPanel.add(resultado);
		controlPanel.add(status);

		res = new JFrame("Resultados");
		res.setResizable(false);

		this.setLayout(new BorderLayout());
		this.add(controlPanel);

	}

	public class GraphicsPanel extends JPanel {

		private static final int CELL_SIZE = 40;
		private Font biggerFont;
		private int nrainhas;

		public GraphicsPanel(int nrainhas) {
			this.nrainhas = nrainhas;
			biggerFont = new Font("", Font.PLAIN, CELL_SIZE - 10);
			this.setPreferredSize(new Dimension(CELL_SIZE * nrainhas, CELL_SIZE
					* nrainhas));
			this.setBackground(Color.black);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for (int r = 0; r < nrainhas; r++) {
				for (int c = 0; c < nrainhas; c++) {
					int x = c * CELL_SIZE;
					int y = r * CELL_SIZE;
					String text = rainhasModel.getFace(r, c);
					if (text != null) {
						if ((r + c) % 2 == 0) {
							g.setColor(Color.white);
							g.fillRect(x + 1, y + 1, CELL_SIZE - 3,
									CELL_SIZE - 3);
							g.setColor(Color.black);
						} else {
							g.setColor(Color.black);
							g.fillRect(x + 1, y + 1, CELL_SIZE - 3,
									CELL_SIZE - 3);
							g.setColor(Color.white);
						}
						g.setFont(biggerFont);
						g.drawString(text, x + 10, y + 30);
					}
				}
			}
		}
	}

	/**
	 * Responsável pelo botão iniciar e a execucao do algoritmo
	 * 
	 * @param void
	 * @return void
	 */
	public class Initial implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			iniciar.setEnabled(false);
			try {
				if (entradaRainhas.getText().equals("")
						|| group.getSelection() == null) {
					status.setText(" Entrada Inválida! ");
					return;
				} else {
					nrainhas = Integer.parseInt(entradaRainhas.getText());
					programa.setRainhas(nrainhas);
				}
				if (paralelo.isSelected()) {
					programa.executa("Paralelo");
				}
				if (recursivo.isSelected()) {
					programa.executa("Recursivo");
				}
				if (sequencial.isSelected()) {
					programa.executa("Sequencial");
				}
				iniciar.setEnabled(true);
				resultado.setEnabled(true);

			} catch (Exception ex) {
				System.err.println("Problema no evento do botao Start");
			}
		}
	}

	/*
	 * Botões de ação do jogo
	 */
	public class Resultado implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			res = new JFrame("Resultado");
			if (entradaRainhas.getText().equals("")
					|| group.getSelection() == null) {
				status.setText(" Entrada Inválida! ");
			} else {
			}
			try {
				printaNoJList();
			} catch (FileNotFoundException ex) {
				System.err.println("Voce excluiu o arquivo"
						+ programa.getNomeArq() + " com os resultados");
			} catch (IOException ex) {
				System.err.println("Voce excluiu o arquivo"
						+ programa.getNomeArq() + " com os resultados");
			}
			res.pack();
			res.setVisible(true);
			res.repaint();

		}
	}

	class Selecao implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent lse) {
			rainhasModel.reset();
			solSelecionada = (String) listaSolucoes.getSelectedValue();
			rainhasModel.reset(solSelecionada);
			rainhasUI.repaint();
		}
	}

	/**
	 * Carrega essas solucoes para JList da interface
	 * 
	 * @param void
	 * @return void
	 */
	public void printaNoJList() throws FileNotFoundException, IOException {

		FileReader file = new FileReader(programa.getNomeArq());
		BufferedReader entrada = new BufferedReader(file);
		String str = null;
		modeloLista = new DefaultListModel();

		while ((str = entrada.readLine()) != null) {
			modeloLista.addElement(str);
		}
		listaSolucoes = new JList(modeloLista);
		listaSolucoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaSolucoes.addListSelectionListener(new Selecao());

		scrollLista = new JScrollPane(listaSolucoes);

		rainhasModel = new NrainhasTabuleiro(nrainhas);
		rainhasUI = new GraphicsPanel(nrainhas);

		res.setLayout(new BorderLayout());
		res.add(rainhasUI, BorderLayout.WEST);
		res.add(scrollLista, BorderLayout.EAST);

	}

}
