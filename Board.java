import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;
public class Board {
    private Tile[][] board;
    private GameState gs;
    public boolean gameStarted = false;

    public GameState getGs() {
        return gs;
    }

    public void setGs(GameState gs) {
        this.gs = gs;
    }



    public Tile[][] getBoard() {
        return board;
    }

    public void setBoard(Tile[][] board) {
        this.board = board;
    }


    Board(GameMode mode){
        this.gs = new GameState(mode);
        board = new Tile[gs.NUM_ROWS][gs.NUM_COLS];
        initBoard();

    }

    private void initBoard(){
        for(int i = 0; i < gs.NUM_ROWS; i++){
            for(int j = 0; j < gs.NUM_COLS; j++){
                board[i][j] = new Tile(i, j);
                int row = i;
                int col = j;

            }
        }
    }
    public void initBombs(int row, int col){
        Random rng = new Random();
        HashSet<Tile> nbrs = getNeighbors(row,col);
        for(int i = 0; i < gs.NUM_MINES; i++){
            int x = rng.nextInt(gs.NUM_ROWS);
            int y = rng.nextInt(gs.NUM_COLS);
            if(!board[x][y].isBomb() && !nbrs.contains(board[x][y]) && x != row && y != col){
                board[x][y].setBomb(true);
            } else{
                i--;
            }
        }
        gameStarted = true;
        revealCount(row, col);
    }
    private boolean isValidTile(int row, int col){
        return (row >= 0 && row < gs.NUM_ROWS && col >= 0 && col < gs.NUM_COLS);
    }
    private int countNeighborBombs(int row, int col){
        int count = 0;
        if(isValidTile(row+1, col)){
            if(board[row+1][col].isBomb()) count++;
        }
        if(isValidTile(row-1, col)){
            if(board[row-1][col].isBomb()) count++;
        }
        if(isValidTile(row+1, col+1)){
            if(board[row+1][col+1].isBomb()) count++;
        }
        if(isValidTile(row+1, col-1)){
            if(board[row+1][col-1].isBomb()) count++;
        }
        if(isValidTile(row-1, col+1)){
            if(board[row-1][col+1].isBomb()) count++;
        }
        if(isValidTile(row-1, col-1)){
            if(board[row-1][col-1].isBomb()) count++;
        }
        if(isValidTile(row, col+1)){
            if(board[row][col+1].isBomb()) count++;
        }
        if(isValidTile(row, col-1)){
            if(board[row][col-1].isBomb()) count++;
        }
        return count;
    }
    private HashSet<Tile> getNeighbors(int row, int col){
        HashSet<Tile> nbrs = new HashSet<>();
        if(isValidTile(row+1, col)){
            nbrs.add(board[row+1][col]);
        }
        if(isValidTile(row-1, col)){
            nbrs.add(board[row-1][col]);
        }
        if(isValidTile(row+1, col+1)){
            nbrs.add(board[row+1][col+1]);
        }
        if(isValidTile(row+1, col-1)){
            nbrs.add(board[row+1][col-1]);
        }
        if(isValidTile(row, col+1)){
            nbrs.add(board[row][col+1]);
        }
        if(isValidTile(row, col-1)){
            nbrs.add(board[row][col-1]);
        }
        if(isValidTile(row-1, col+1)){
            nbrs.add(board[row-1][col+1]);
        }
        if(isValidTile(row-1, col-1)){
            nbrs.add(board[row-1][col-1]);
        }
        return nbrs;
    }
    public GameState getState(){
        return gs;
    }
    public void flag(int row, int col){
        gs.FLAGS_LEFT--;
        board[row][col].setBackground(Color.RED);
        board[row][col].flagged = true;
    }
    public void unflag(int row, int col){

        gs.FLAGS_LEFT++;
        board[row][col].flagged = false;
        board[row][col].setBackground(null);
    }
    public void revealCount(int row, int col){
        if(!isValidTile(row, col)) return;
        if(countNeighborBombs(row, col) == 0 && !board[row][col].revealed){
            board[row][col].revealed = true;
            board[row][col].setBackground(Color.GRAY);
            revealCount(row, col+1);
            revealCount(row, col-1);
            revealCount(row + 1, col);
            revealCount(row-1, col);
            revealCount(row+1, col+1);
            revealCount(row+1, col-1);
            revealCount(row-1, col+1);
            revealCount(row-1, col-1);


        } else if(countNeighborBombs(row, col) != 0){
            board[row][col].setText(countNeighborBombs(row, col) + "");
            board[row][col].revealed = true;
            return;
        }
    }
    public boolean checkWin(){
        for(int i = 0; i < gs.NUM_ROWS; i++){
            for(int j = 0; j < gs.NUM_COLS; j++){
                if(board[i][j].isBomb()){
                    if(!board[i][j].flagged){
                        return false;
                    }
                }
            }
        }
        return true;
    }

}
