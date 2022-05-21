package view;

import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 * 尝试
 */
public class ChessGameFrame extends JFrame {
    //public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    Chessboard chessboard;
    public static JLabel playerLabel;

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addChessboard();
        addPlayerLabel();
        addSaveButton();
        addLoadButton();
        //addLabelCurrentPlayer();

        remake();
        undo();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        this.chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE, null);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10 - 10);
        add(chessboard);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addPlayerLabel() {
        playerLabel = new JLabel(this.gameController.getChessboard().getCurrentColor().getName()+"'s");
        System.out.println(this.gameController.getChessboard().getCurrentColor().getName());
        playerLabel.setLocation(HEIGHT + 60, HEIGHT / 10 - 30);
        playerLabel.setSize(150, 50);
        playerLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(playerLabel);
        repaint();
    }
    public static void setPlayerLabel(ChessColor chessColor){
        playerLabel.setText(chessColor.getName()+"'s");
        System.out.println(chessColor.getName());

    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGHT, HEIGHT / 10 + 90);
        button.setSize(200, 50);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            if (gameController.loadGame(path) != null) {
                remove(this.chessboard);
                repaint();
                this.chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE, gameController.loadGame(path));
                gameController = new GameController(chessboard);
                chessboard.setLocation(HEIGHT / 10, HEIGHT / 10 - 10);
                gameController.loadGameFromFile(path);
                add(chessboard);
            }
        });
    }

    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGHT, HEIGHT / 10 + 30);
        button.setSize(200, 50);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        gameController.setChessboard(chessboard);
        button.addActionListener(e -> {
            System.out.println("Click save");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            gameController.writeDataToFile(path);
        });
    }

    private void remake() {
        JButton Remake = new JButton("Remake");
        Remake.setLocation(HEIGHT, HEIGHT / 10 + 150);
        Remake.setSize(200, 50);
        Remake.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(Remake);

        Remake.addActionListener(e -> {
            System.out.println("Click remake");
            remove(this.chessboard);
            repaint();
            this.chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE, null);
            gameController = new GameController(chessboard);
            chessboard.setLocation(HEIGHT / 10, HEIGHT / 10 - 10);
            add(chessboard);
        });
    }

    private void undo(){
        JButton Undo = new JButton("undo");
        Undo.setLocation(HEIGHT, HEIGHT / 10 + 210);
        Undo.setSize(200, 50);
        Undo.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(Undo);

        Undo.addActionListener(e -> {
            if( chessboard.steps.size() > 1 ){
                System.out.println("Click undo");
                remove(chessboard);
                repaint();

                chessboard.steps.remove(chessboard.steps.size()-1);

                chessboard.loadGame(chessboard.steps.get(chessboard.steps.size()-1));
                chessboard.setLocation(HEIGHT / 10, HEIGHT / 10 - 10);
                add(chessboard);
            }
            else
                JOptionPane.showMessageDialog(this, "error");
        });
    }


//    private void addLabelCurrentPlayer(){
//        String player = chessboard.player;
//        JLabel current = new JLabel(player);
//        current.setLocation(HEIGHT+60, HEIGHT / 10 + 260);
//        current.setSize(150, 50);
//        current.setFont(new Font("Rockwell", Font.BOLD, 20));
//        add(current);
//    }

}
