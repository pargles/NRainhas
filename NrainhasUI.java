import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
        private DefaultListModel modeloLista;
	private JScrollPane scrollLista;// para aparecerem as barras de scroll
	private String solSelecionada;
	private NQueens programa;
        private long tempoInicio,tempoTotal;


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
                tempoInicio= System.currentTimeMillis();
                if (paralelo.isSelected()) {
                    programa.executa("Paralelo");
                }
                if (recursivo.isSelected()) {
                    programa.executa("Recursivo");
                }
                if (sequencial.isSelected()) {
                    programa.executa("Sequencial");
                }
                tempoTotal = (System.currentTimeMillis()-tempoInicio) / 1000;//em segundos
                System.out.println("TEMPO TOTAL: "+tempoTotal);
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
            if (entradaRainhas.getText().equals("")
                    || group.getSelection() == null) {
                status.setText(" Entrada Inválida! ");
            } else {
            }
            try {
                printaNoJList();
            } catch (FileNotFoundException ex) {
                System.err.println("Voce excluiu o arquivo"+ programa.getNomeArq()+ " com os resultados");
            } catch (IOException ex) {
               System.err.println("Voce excluiu o arquivo"+ programa.getNomeArq()+ " com os resultados");
            }
            res.pack();
            res.setVisible(true);

        }
    }

	class Selecao implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent lse) {
                        rainhasModel.reset();
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

    public void printaNoJList() throws FileNotFoundException, IOException {

        BufferedReader in = new BufferedReader(new FileReader(programa.getNomeArq()));
        String str;
        while (in.ready()) {
            str = in.readLine();
            modeloLista.addElement(str);
        }
        in.close();
        listaSolucoes = new JList(modeloLista);
        listaSolucoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// para
        listaSolucoes.addListSelectionListener(new Selecao());
        scrollLista = new JScrollPane(listaSolucoes);

        rainhasModel = new NrainhasModel(nrainhas);

        rainhasUI = new GraphicsPanel(nrainhas);

        res.setLayout(new BorderLayout());
        res.add(rainhasUI, BorderLayout.EAST);
        res.add(scrollLista, BorderLayout.WEST);
        System.out.println(nrainhas);
    }


}


