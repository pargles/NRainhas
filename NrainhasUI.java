import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;


/*
 * Cria o painel de interface com o usuário
 *      com botões e quadro de jogo
 */
public class NrainhasUI extends JPanel {
	
	private GraphicsPanel rainhasUI;
	private NrainhasModel rainhasModel;
	
	
	private JPanel controlPanel;
	private JButton initial;
	
	
	
	public NrainhasUI() {
		rainhasModel = new NrainhasModel();

		initial = new JButton("Initial Game");
		initial.addActionListener(new Initial());

		
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		controlPanel.add(initial);
		
		rainhasUI = new GraphicsPanel();

		this.setLayout(new BorderLayout());
		this.add(controlPanel, BorderLayout.NORTH);

		this.add(rainhasUI, BorderLayout.SOUTH);
		
	}

	public class GraphicsPanel extends JPanel implements MouseListener {
		private static final int ROWS = 8;
		private static final int COLS = 8;

		private static final int CELL_SIZE = 80;
		private Font _biggerFont;

		public GraphicsPanel() {
			_biggerFont = new Font("", Font.PLAIN, CELL_SIZE - 10);
			this.setPreferredSize(new Dimension(CELL_SIZE * COLS, CELL_SIZE* ROWS));
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
						if((r+c)%2 == 0){
							g.setColor(Color.white);
							g.fillRect(x + 1, y + 1, CELL_SIZE - 3, CELL_SIZE - 3);
							g.setColor(Color.black);
						} else{
							g.setColor(Color.black);
							g.fillRect(x + 1, y + 1, CELL_SIZE - 3, CELL_SIZE - 3);
							g.setColor(Color.white);
						}
						g.setFont(_biggerFont);
						g.drawString(text, x + 10  , y + 70);
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
			int[] nrainha = {3,5,4,6,1,7,3,2};
			rainhasModel.reset(nrainha);
			rainhasUI.repaint();
		}
	}


}