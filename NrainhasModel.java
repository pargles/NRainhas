
/*
 * Cria o quadro de jogo
 */
public class NrainhasModel {
    private int nrainhas = 8;
    
    private Tile[][] contents;  
    
    
    public NrainhasModel() {
        contents = new Tile[nrainhas][nrainhas];
        reset();               
    }
    
    String getFace(int row, int col) {
        return contents[row][col].getFace();
    }
    
    
    /*
     * Desenha o quadro inicial
     */
    public void reset() {
        for (int r=0; r < nrainhas; r++) {
            for (int c=0; c < nrainhas; c++) {
            	contents[r][c] = new Tile(r, c, "" );
            }
        }
    }
    
    public void reset(int[] cells) {
        for (int r = 0; r < cells.length; r++) {
            contents[r][cells[r]] = new Tile(r, cells[r], "\u2655" );
        }
    }
    
    
    
    public void setNrainhas(int nrainhas){
    	this.nrainhas = nrainhas;
    }
    
    public int getNrainhas(){
    	return nrainhas;
    }
}   
    
    
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
