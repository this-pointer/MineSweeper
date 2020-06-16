public class GameState {
    public int NUM_MINES;
    public int NUM_ROWS;
    public int NUM_COLS;
    public int FLAGS_LEFT;
    GameState(GameMode mode){
        switch(mode){
            case EASY:
                NUM_MINES = 10;
                NUM_ROWS = 9;
                NUM_COLS = 9;
                FLAGS_LEFT = NUM_MINES;
                break;
            case MEDIUM:
                NUM_MINES = 40;
                NUM_ROWS = 16;
                NUM_COLS = 16;
                FLAGS_LEFT = NUM_MINES;
                break;
            case HARD:
                NUM_MINES = 99;
                NUM_ROWS = 24;
                NUM_COLS = 24;
                FLAGS_LEFT = NUM_MINES;
                break;
        }
    }
}
