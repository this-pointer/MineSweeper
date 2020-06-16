import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class GUI{
    private JFrame win;
    private JPanel startPanel, gamePanel, scorePanel, endPanel, parentPanel;
    private JLabel timeLabel, flagsLeftLabel;
    private Timer timer;
    private int timeElapsedMs;
    private Board b;
    public GUI(){
        win = new JFrame("Mine$weeper");
        initStartScreen();
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        win.setVisible(true);

    }
    private void initStartScreen(){
        win.getContentPane().removeAll();
        startPanel = new JPanel();
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
        startPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JRadioButton ez = new JRadioButton("Easy");
        ez.setAlignmentX(Component.CENTER_ALIGNMENT);
        JRadioButton med = new JRadioButton("Medium");
        med.setAlignmentX(Component.CENTER_ALIGNMENT);
        JRadioButton hard = new JRadioButton("Hard");
        hard.setAlignmentX(Component.CENTER_ALIGNMENT);
        ButtonGroup grp = new ButtonGroup();
        JButton submit  = new JButton("Submit");
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);
        submit.addActionListener((e)->{
            if(ez.isSelected()){
                b = new Board(GameMode.EASY);
                initGameScreen();
            }
            if(med.isSelected()){
                b = new Board(GameMode.MEDIUM);
                initGameScreen();
            }
            if(hard.isSelected()){
                b = new Board(GameMode.HARD);
                initGameScreen();
            }
        });
        grp.add(ez);
        grp.add(med);
        grp.add(hard);
        startPanel.add(ez);
        startPanel.add(Box.createVerticalGlue());
        startPanel.add(med);
        startPanel.add(Box.createVerticalGlue());
        startPanel.add(hard);
        startPanel.add(Box.createVerticalGlue());
        startPanel.add(submit);
        win.add(startPanel);
        win.pack();
    }
    private void initGameScreen(){
        GameState gs = b.getState();
        win.getContentPane().removeAll();
        parentPanel = new JPanel();
        parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
        scorePanel = new JPanel();
        scorePanel.setLayout(new FlowLayout(FlowLayout.LEADING, 80, 0));
        timeLabel = new JLabel("TIME ELAPSED: 0:00");
        flagsLeftLabel = new JLabel("FLAGS LEFT: " + gs.FLAGS_LEFT);
        flagsLeftLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        timeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        flagsLeftLabel.setFont(new Font(flagsLeftLabel.getFont().getName(),Font.BOLD, 40));
        timeLabel.setFont(new Font(flagsLeftLabel.getFont().getName(),Font.BOLD, 40));
        flagsLeftLabel.setForeground(Color.RED);
        timeLabel.setForeground(Color.GREEN);
        flagsLeftLabel.setHorizontalTextPosition(SwingConstants.LEFT);
        timeLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        scorePanel.add(flagsLeftLabel);
        scorePanel.add(timeLabel);
        timer = new Timer(1000, e -> {
            timeElapsedMs += 1000;
            String time = getTime(timeElapsedMs);
            timeLabel.setText("TIME ELAPSED: " + time);
        });
        gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(gs.NUM_ROWS, gs.NUM_COLS));
        for(int i = 0; i < gs.NUM_ROWS; i++){
            for(int j = 0; j < gs.NUM_COLS; j++){
                Tile t = b.getBoard()[i][j];
                int row = i;
                int col = j;
                t.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(!b.gameStarted){
                            b.initBombs(row,col);
                            timer.start();
                        } else{
                            if(e.getButton() == MouseEvent.BUTTON1){
                                if(b.getBoard()[row][col].isBomb()){
                                    gameOver(false);
                                    timer.stop();
                                    timeElapsedMs = 0;
                                } else{
                                    b.revealCount(row, col);
                                }

                            }
                            if(e.getButton() == MouseEvent.BUTTON3){
                                if(!b.getBoard()[row][col].flagged){
                                    b.flag(row, col);
                                } else{
                                    b.unflag(row, col);
                                }
                                SwingUtilities.invokeLater(() -> {flagsLeftLabel.setText("FLAGS LEFT: " + gs.FLAGS_LEFT);});
                                if(b.checkWin()){
                                    gameOver(true);
                                    timer.stop();
                                    timeElapsedMs = 0;
                                }

                            }
                        }
                    }

                });
                gamePanel.add(t);
            }
        }
        parentPanel.setPreferredSize(new Dimension(1000,1000));
        scorePanel.setPreferredSize(new Dimension(1000, 100));
        gamePanel.setPreferredSize(new Dimension(1000, 900));
        parentPanel.add(scorePanel);
        parentPanel.add(gamePanel);
        win.add(parentPanel);

        win.pack();
    }
    private void gameOver(boolean won){
        win.getContentPane().removeAll();


        endPanel = new JPanel();
        endPanel.setLayout(new BoxLayout(endPanel, BoxLayout.Y_AXIS));
        JLabel msg = new JLabel();
        String text = (won) ? "CONGRATS! YOU WON in " + getTime(timeElapsedMs) + "!": "GARBAGE YOU LOST!";
        msg.setText(text);
        msg.setAlignmentX(Component.CENTER_ALIGNMENT);
        msg.setFont(new Font(msg.getFont().getName(), Font.BOLD, 30));
        JButton playAgain = new JButton("Play Again");
        playAgain.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgain.addActionListener(e -> {
            initStartScreen();
        });
        endPanel.add(Box.createVerticalGlue());
        endPanel.add(msg);
        endPanel.add(Box.createVerticalGlue());
        endPanel.add(playAgain);
        endPanel.setPreferredSize(new Dimension(500,400));
        win.add(endPanel);
        win.pack();

    }
    private String getTime(int ms){
        int s = ms/1000;
        int mins = s/60;
        s = s % 60;
        if(s < 10){
            return mins + ":0" + s;
        }
        return mins + ":" + s;

    }
}
