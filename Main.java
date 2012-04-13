import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;

/*
 * Trabalho para a disciplina de Inteligência Artificial - prof.: Anderson Ferrugem
 * 		Alunos: Abilio Gambim Parada
 * 				Vanderson Oliveira da Silva
 */
public class Main {
	
	public static void main(String[] args) throws IOException {
		JFrame window = new JFrame("N-Rainhas IA");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new NrainhasUI());
        window.pack();  
        window.show();  
        window.setResizable(false);
	}
}
