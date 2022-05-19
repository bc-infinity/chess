package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    Chessboard chessboard;

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
        addLabel();
        addHelloButton();
        addLoadButton();

        remake();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        gameController = new GameController(chessboard);
        this.chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE,null);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10 - 10);
        add(chessboard);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("Sample label");
        statusLabel.setLocation(HEIGHT+60, HEIGHT / 10 - 30);
        statusLabel.setSize(150, 50);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */
    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGHT, HEIGHT / 10 + 30);
        button.setSize(200, 50);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGHT, HEIGHT / 10 + 90);
        button.setSize(200, 50);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            if(gameController.loadGame(path)!=null) {
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


    private void remake(){
        JButton Remake = new JButton("Remake");
        Remake.setLocation(HEIGHT, HEIGHT / 10 + 150);
        Remake.setSize(200, 50);
        Remake.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(Remake);

        Remake.addActionListener(e -> {
            System.out.println("Click remake");
            remove(this.chessboard);
            repaint();
            this.chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE,null);
            gameController = new GameController(chessboard);
            chessboard.setLocation(HEIGHT / 10, HEIGHT / 10 - 10);
            add(chessboard);
        });
    }

}
