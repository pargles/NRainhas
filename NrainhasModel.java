
/*
 * Cria o quadro de jogo
 */
public class NrainhasModel {
    private static final int ROWS = 8;
    private static final int COLS = 8;
    
    private Tile[][] contents;  
    
    
    public NrainhasModel() {
        contents = new Tile[ROWS][COLS];
        reset();               
    }
    
    String getFace(int row, int col) {
        return contents[row][col].getFace();
    }
    
    
    /*
     * Desenha o quadro inicial
     */
    public void reset() {
        for (int r=0; r<ROWS; r++) {
            for (int c=0; c<COLS; c++) {
            	contents[r][c] = new Tile(r, c, "" );
            }
        }
    }
    
    public void reset(int[] cells) {
        for (int r = 0; r < cells.length; r++) {
            contents[r][cells[r]] = new Tile(r, cells[r], "\u2655" );
        }
    }
    
    
    public void result(char[] board) {
        for (int r=0; r<ROWS; r++) {
            for (int c=0; c<COLS; c++) {
            	contents[r][c] = new Tile(r, c, Character.toString(board[r*8 + c]));
            }
        }
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
