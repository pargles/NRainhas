
/*
 * Cria o quadro de jogo
 */
public class NrainhasTabuleiro {
    private int nrainhas;
    
    private Tile[][] contents;  
    
    /* metodo construtor da classe NrainhasTabuleiro
     * recebe como parametro a quantidade de rainhas
     * @param int rainhas
     * @return void
     */
    public NrainhasTabuleiro(int nrainhas) {
    	this.nrainhas = nrainhas;
        contents = new Tile[nrainhas][nrainhas];
        reset();               
    }
    
    String getFace(int row, int col) {
        return contents[row][col].getFace();
    }
    
    
    /* metodo que desenha o tabuleiro inicial
     * @param void
     * @return void
     */
    public void reset() {
        for (int r=0; r < nrainhas; r++) {
            for (int c=0; c < nrainhas; c++) {
            	contents[r][c] = new Tile(r, c, "" );
            }
        }
    }

    /* Metodo que recebe um vetor de inteiros
     * contendo as posicoes de cada rainhas
     * @para int[] cells
     * @return void
     */
    public void reset(int[] cells) {
        for (int r = 0; r < cells.length; r++) {
            contents[cells[r]][r] = new Tile(r, cells[r], "\u2655" );
        }
    }

    /* Metodo que recebe a string com as posicoes
     * onde se encontram cada uma das rainhas
     * @param String cells
     * @return void
     */
    public void reset(String cells) {
    	int begin = 0, end = 0;
    	String [] tokens = cells.split(" ");
        for (int r = 0; r < nrainhas; r++) {
            contents[Integer.parseInt(tokens[r])][r] = new Tile(r, Integer.parseInt(tokens[r]), "\u2655" );
        }
    }
    
    public void setNrainhas(int nrainhas){
    	this.nrainhas = nrainhas;
    }
    
    public int getNrainhas(){
    	return nrainhas;
    }
}   
    
 /* metodo que cria o tabuleiro
  * cada tile e um posicao [x,y]
  * contendo rainha ou nao
  * @param void
  * @return void
  */
class Tile {
    private int row;     
    private int col;     
    private String face;  
    
    public Tile(int row, int col, String face) {
        this.row = row;
        this.col = col;
        this.face = face;
    }
    
    public void setFace(String newFace) {
        face = newFace;
    }
    
    public String getFace() {
        return face;
    }
}
