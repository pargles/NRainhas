import java.io.IOException;
import javax.swing.JFrame;

/**
 * Trabalho NRainhas - Sistemas Operacionais
 * 
 * @author abilio and pargles
 * @version 1.0
 */
public class Main {
	public static void main(String[] args) throws IOException, Exception {
		JFrame window = new JFrame("N-Rainhas SO");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(new NRainhasInterface());
		window.pack();
		window.show();
		window.setResizable(false);
	}
}
