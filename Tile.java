import javax.swing.JButton;
public class Tile extends JButton{
    private int row;
    private int col;
    private boolean isBomb;
    public boolean revealed = false;
    public boolean flagged = false;


    Tile(){
        row = 0;
        col = 0;
        isBomb = false;
    }
    Tile(int row, int col){
        super();
        this.row = row;
        this.col = col;
        isBomb = false;
    }
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void setBomb(boolean bomb) {
        isBomb = bomb;
    }
    @Override
    public boolean equals(Object other){
        Tile otherTile = (Tile) other;
        return (this.row == otherTile.getRow() && this.col == otherTile.getCol());
    }
    @Override
    public int hashCode(){
        return 31*row + 37*col;
    }

}
