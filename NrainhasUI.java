import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

/*
 * Cria o painel de interface com o usuário
 *      com botões e quadro de jogo
 */
public final class NrainhasUI extends JPanel {

	private int nrainhas;

	private JPanel rainhasUI,controlPanel;
	private NrainhasModel rainhasModel;
        private JLabel status;
	private JFrame res;
	private JButton iniciar,resultado;
	private ButtonGroup group;
	private JRadioButton paralelo,sequencial,recursivo;
	private JTextField entradaRainhas;
	private JList listaSolucoes;
	JScrollPane scrollLista;// para aparecerem as barras de scroll
	private String solSelecionada;
	private NQueens programa;
        String[] solucoes = {"02461357","02461357","02461357","02461357","02461357","02461357",
        "02461357","02461357","02461357","02461357","02461357","02461357","02461357","02461357","02461357",
        "02461357","02461357","02461357","02461357","02461357","02461357","02461357","02461357","02461357",
        "02461357","02461357","02461357","02461357","02461357","02461357",};



	public NrainhasUI() throws IOException {
		programa = new NQueens();
                iniciaComponentes();

	}

    /* Metodo que cria e insere todos os botoes e
     * ferramentas da interface
     * @param void
     * @return void
     *
     */
    public void iniciaComponentes()
    {
        listaSolucoes = new JList(solucoes);

        listaSolucoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// para
        iniciar = new JButton("Iniciar");
        iniciar.addActionListener(new Initial());

        resultado = new JButton("Mostrar Resultados");
        resultado.addActionListener(new Resultado());

        listaSolucoes.addListSelectionListener(new Selecao());
        scrollLista = new JScrollPane(listaSolucoes);

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
        // panelLista = new JPanel();
        controlPanel.setLayout(new GridLayout(0, 1));
        // panelLista.setLayout(new FlowLayout());

        // controlPanel.add(selectAlg);
        controlPanel.add(new JLabel(" Algoritmo: "));
        controlPanel.add(paralelo);
        controlPanel.add(recursivo);
        controlPanel.add(sequencial);
        controlPanel.add(new JLabel(" Número de Rainhas: "));
        controlPanel.add(entradaRainhas);
        controlPanel.add(iniciar);
        controlPanel.add(resultado);
        controlPanel.add(status);

        // controlPanel.add(listaSolucoes);
        // panelLista.add(scrollLista);
        res = new JFrame("Resultados");
        res.setResizable(false);

        this.setLayout(new BorderLayout());
        this.add(controlPanel);

    }

    public class GraphicsPanel extends JPanel {

        private static final int CELL_SIZE = 40;
        private Font _biggerFont;
        private int nrainhas;

        public GraphicsPanel(int nrainhas) {
            this.nrainhas = nrainhas;
            _biggerFont = new Font("", Font.PLAIN, CELL_SIZE - 10);
            this.setPreferredSize(new Dimension(CELL_SIZE * nrainhas, CELL_SIZE
                    * nrainhas));
            this.setBackground(Color.black);
        }

        //public void setNrainhas(int nrainhas){
        //	this.nrainhas = nrainhas;
        //}
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            System.err.println(nrainhas);
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
                        g.setFont(_biggerFont);
                        g.drawString(text, x + 10, y + 30);
                    }
                }
            }
        }
    }

	/*
	 * Botões de ação do jogo
	 */
    public class Initial implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                if (entradaRainhas.getText().equals("")
                        || group.getSelection() == null) {
                    status.setText(" Entrada Inválida! ");
                    return;
                } else {
                    nrainhas = Integer.parseInt(entradaRainhas.getText());
                    rainhasModel = new NrainhasModel(nrainhas);

//					((GraphicsPanel) rainhasUI).setNrainhas(nrainhas);
                    rainhasUI = new GraphicsPanel(nrainhas);

                    res.setLayout(new BorderLayout());
                    res.add(rainhasUI, BorderLayout.EAST);
                    res.add(listaSolucoes, BorderLayout.WEST);
                    System.out.println(nrainhas);
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
            if (entradaRainhas.getText().equals("")
                    || group.getSelection() == null) {
                status.setText(" Entrada Inválida! ");
            } else {
            }

            res.pack();
            res.setVisible(true);

        }
    }

	class Selecao implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent lse) {
			solSelecionada = (String) listaSolucoes.getSelectedValue();
                        int vetor[] = new int[solSelecionada.length()];
                        stringToIntegerArray(solSelecionada,vetor);
			rainhasModel.reset(vetor);
			rainhasUI.repaint();
		}
	}

    /* Metodo que transforma uma string recebida pelo JList
     * em um vetor de inteiros para a classe NRainhasModel para
     * o resultado ser impresso no tabuleiro
     * @param String s ,int[] vetor
     * @return void
     */
    public void stringToIntegerArray(String s, int[] vetor) {
        if (s != null) {

            for (int i = 0; i < s.length(); i++) {
                vetor[i] = Integer.parseInt(s.substring(i, i + 1));
            }
        }
    }


}

