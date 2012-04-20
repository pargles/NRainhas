import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;

/**
 *
 * @author abilio and pargles
 * @version 1.0
 */
public class Main 
{
	
	public static void main(String[] args) throws IOException, Exception
	{
            NQueens programa = new NQueens();
            programa.executa();
            JFrame window = new JFrame("N-Rainhas IA");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setContentPane(new NrainhasUI());
            window.pack();
            window.show();
            window.setResizable(false);
	}
}

