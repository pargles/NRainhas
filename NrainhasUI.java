import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/*
 * Cria o painel de interface com o usuário
 *      com botões e quadro de jogo
 */
public class NrainhasUI extends JPanel {


	
	private GraphicsPanel rainhasUI;
	private NrainhasModel rainhasModel;
	private JPanel controlPanel;
	private JButton initial;
        private JList listaSolucoes;
        private JPanel panelLista;
        String[] solucoes = {"10243567","10243567","10243567","10243567"};
        JScrollPane scrollLista;//para aparecerem as barras de scroll
        String solSelecionada;
         NQueens programa;
	
	
	
    public NrainhasUI() throws IOException {
        programa = new NQueens();
        listaSolucoes = new JList(solucoes);
        rainhasModel = new NrainhasModel();
        listaSolucoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//para poder selecionar apenas 1 pro vez

        initial = new JButton("Iniciar");
        initial.addActionListener(new Initial());
        listaSolucoes.addListSelectionListener(new Selecao());
        scrollLista = new JScrollPane( listaSolucoes);



        controlPanel = new JPanel();
        panelLista = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        controlPanel.add(initial);
        panelLista.add(scrollLista);

        rainhasUI = new GraphicsPanel();

        

        this.setLayout(new BorderLayout());
        this.add(controlPanel, BorderLayout.WEST);

        this.add(rainhasUI, BorderLayout.CENTER);
        this.add(panelLista,BorderLayout.EAST);

    }

    public class GraphicsPanel extends JPanel implements MouseListener {

        private static final int ROWS = 8;
        private static final int COLS = 8;
        private static final int CELL_SIZE = 80;
        private Font _biggerFont;

        public GraphicsPanel() {
            _biggerFont = new Font("", Font.PLAIN, CELL_SIZE - 10);
            this.setPreferredSize(new Dimension(CELL_SIZE * COLS, CELL_SIZE * ROWS));
            this.setBackground(Color.black);
        }

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int r = 0; r < ROWS; r++) {
                    for (int c = 0; c < COLS; c++) {
                        int x = c * CELL_SIZE;
                        int y = r * CELL_SIZE;
                        String text = rainhasModel.getFace(r, c);
                        if (text != null) {
                            if ((r + c) % 2 == 0) {
                                g.setColor(Color.white);
                                g.fillRect(x + 1, y + 1, CELL_SIZE - 3, CELL_SIZE - 3);
                                g.setColor(Color.black);
                            } else {
                                g.setColor(Color.black);
                                g.fillRect(x + 1, y + 1, CELL_SIZE - 3, CELL_SIZE - 3);
                                g.setColor(Color.white);
                            }
                            g.setFont(_biggerFont);
                            g.drawString(text, x + 10, y + 70);
                        }
                    }
                }
            }
		public void mousePressed(MouseEvent e) {}

		public void mouseClicked(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}

	}

	/*
	 * Botões de ação do jogo
	 */
    public class Initial implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                
                programa.executa();

            } catch (Exception ex) {
                System.err.println("Problema no evento do botao Start");
            }
        }
    }

    class Selecao implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent lse) {
            solSelecionada = (String) listaSolucoes.getSelectedValue();
            int[] nrainha = {3, 5, 4, 6, 1, 7, 3, 2};
            rainhasModel.reset(nrainha);
            rainhasUI.repaint();
        }
    }


}

