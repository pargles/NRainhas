import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;


/*
 * Cria o painel de interface com o usuário
 *      com botões e quadro de jogo
 */
public class NrainhasUI extends JPanel {
	
	private JPanel rainhasUI;
	private NrainhasModel rainhasModel;
	private JFrame res;
	
	private JPanel controlPanel;
	
	private JButton iniciar;
	private JButton resultado;
	
	private ButtonGroup group;
	private JRadioButton paralelo;
	private JRadioButton sequencial;
	private JRadioButton recursivo;
    
	private JTextField nrainhas;
    
	private JLabel status;
	
	private JList listaSolucoes;
    private JPanel panelLista;
    
    private RadioButtonBorder selcAlg;
    String[] solucoes = {"10243567","10243567","10243567","10243567"};
    JScrollPane scrollLista;//para aparecerem as barras de scroll
    String solSelecionada;
    NQueens programa;
    JComboBox selectAlg;
	
    
    
	
    public NrainhasUI() throws IOException {
        programa = new NQueens();
        listaSolucoes = new JList(solucoes);
        rainhasModel = new NrainhasModel();
        listaSolucoes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//para poder selecionar apenas 1 pro vez
        //selectAlg = new JComboBox();
        //selectAlg.setModel(new DefaultComboBoxModel(new String[] { , "Sequencial","Recursivo"}));
        //selectAlg.addActionListener(new selecionaAlgoritmo());
        iniciar = new JButton("Iniciar");
        iniciar.addActionListener(new Initial());
        
        resultado = new JButton("Mostrar Resultados");
        resultado.addActionListener(new Resultado());
        
        listaSolucoes.addListSelectionListener(new Selecao());
        scrollLista = new JScrollPane( listaSolucoes);

        nrainhas = new JTextField();
        
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
        //panelLista = new JPanel();
        controlPanel.setLayout(new GridLayout(0,1));
        //panelLista.setLayout(new FlowLayout());

        
        
        //controlPanel.add(selectAlg);
        controlPanel.add(new JLabel(" Algoritmo: "));
        controlPanel.add(paralelo);
        controlPanel.add(recursivo);
        controlPanel.add(sequencial);
        controlPanel.add(new JLabel(" Número de Rainhas: "));
        controlPanel.add(nrainhas);
        controlPanel.add(iniciar);
        controlPanel.add(resultado);
        controlPanel.add(status);
        
        //controlPanel.add(listaSolucoes);
        //panelLista.add(scrollLista);
        res = new JFrame("Resultados");
        rainhasUI = new GraphicsPanel();
        
        res.setLayout(new BorderLayout());
        res.add(rainhasUI, BorderLayout.EAST);
        res.add(listaSolucoes,BorderLayout.WEST);

        this.setLayout(new BorderLayout());
        this.add(controlPanel);
        
       
        
        //this.add(rainhasUI, BorderLayout.EAST);
        //this.add(panelLista,BorderLayout.WEST);

    }

    public class GraphicsPanel extends JPanel {

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
            	if(nrainhas.getText().equals("") || group.getSelection() == null){
        			status.setText(" Entrada Inválida! ");
            	}
        		else if(paralelo.isSelected()){
            		programa.executa("Paralelo");
            	}
            	else if(recursivo.isSelected()){
            		programa.executa("Recursivo");
            	}
            	else if(sequencial.isSelected()){
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
        	if(nrainhas.getText().equals("") || group.getSelection() == null){
    			status.setText(" Entrada Inválida! ");
        	} else{
        		rainhasUI.setVisible(true);
        	}
        	res.pack();
        	res.setVisible(true);
        	int[] nrainha = {3, 5, 4, 6, 1, 7, 3, 2};
            
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

    public class selecionaAlgoritmo implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            programa.tipoAlgoritmo= (String) selectAlg.getSelectedItem();
        }
    }
    
    

}

