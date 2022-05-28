package view;

import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
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
    public static JLabel roundLabel;
    private int n = 0;
    private JLabel imgLabel;
    private Container cp;


    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project");
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        ImageIcon img = new ImageIcon("./images/20211016120322.jpg");
        imgLabel = new JLabel(img);
        getLayeredPane().add(imgLabel, Integer.valueOf(Integer.MIN_VALUE));
        imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
        imgLabel.setLocation(0, 0);
        cp = getContentPane();
        cp.setLayout(null);
        ((JPanel) cp).setOpaque(false);


        addChessboard();
        addPlayerLabel();
        addRoundLabel();
        addSaveButton();
        addLoadButton();
        //addLabelCurrentPlayer();

        remake();
        undo();
        changeBackground();
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
        playerLabel = new JLabel(this.gameController.getChessboard().getCurrentColor().getName() + "'s");
        System.out.println(this.gameController.getChessboard().getCurrentColor().getName());
        playerLabel.setLocation(HEIGHT, HEIGHT / 10 - 30);
        playerLabel.setSize(150, 50);
        playerLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        playerLabel.setForeground(Color.white);
        add(playerLabel);
        repaint();
    }

    private void addRoundLabel() {
        roundLabel = new JLabel("Round:" + this.gameController.getChessboard().steps.size());
        System.out.println(this.gameController.getChessboard().steps.size());
        roundLabel.setLocation(HEIGHT + 100, HEIGHT / 10 - 30);
        roundLabel.setSize(150, 50);
        roundLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        roundLabel.setForeground(Color.white);
        add(roundLabel);
        repaint();
    }

    public static void setPlayerLabel(ChessColor chessColor) {
        playerLabel.setText(chessColor.getName() + "'s");
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

            String path = "";
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            int returnValue = jfc.showOpenDialog(null);
            //int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                path = selectedFile.getAbsolutePath();
            }

            //String path = JOptionPane.showInputDialog(this, "Input Path here");
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

            String path = null;
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.setDialogTitle("Choose a directory to save your file: ");
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = jfc.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                if (jfc.getSelectedFile().isDirectory()) {
                    path = jfc.getSelectedFile().toString();
                }
            }

            if (path != null) {
                String name = JOptionPane.showInputDialog(this, "Input name here");
                if (name != null)
                    gameController.writeDataToFile(path + "/" + name + ".txt");
            }

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
            playerLabel.setText(this.gameController.getChessboard().getCurrentColor().getName() + "'s");
            roundLabel.setText("Round:" + this.gameController.getChessboard().steps.size());
        });
    }

    private void undo() {
        JButton Undo = new JButton("undo");
        Undo.setLocation(HEIGHT, HEIGHT / 10 + 210);
        Undo.setSize(200, 50);
        Undo.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(Undo);

        Undo.addActionListener(e -> {
            if (chessboard.steps.size() > 1) {
                System.out.println("Click undo");
                remove(chessboard);
                repaint();

                chessboard.steps.remove(chessboard.steps.size() - 1);

                chessboard.loadGame(chessboard.steps.get(chessboard.steps.size() - 1));
                chessboard.setLocation(HEIGHT / 10, HEIGHT / 10 - 10);
                add(chessboard);
                playerLabel.setText(this.gameController.getChessboard().getCurrentColor().getName() + "'s");
                roundLabel.setText("Round:" + (this.gameController.getChessboard().steps.size()+1)/2);
            } else
                JOptionPane.showMessageDialog(this, "error");
        });
    }

    private void changeBackground() {
        JButton changeBackGround = new JButton("Change Background");
        changeBackGround.setLocation(HEIGHT, HEIGHT / 10 + 270);
        changeBackGround.setSize(200, 30);
        changeBackGround.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(changeBackGround);

        changeBackGround.addActionListener(e -> {
            if (n == 0) {
                getLayeredPane().remove(imgLabel);
                repaint();

                ImageIcon img = new ImageIcon("images/IMG_9006(20220521-092825).JPG");
                imgLabel = new JLabel(img);
                getLayeredPane().add(imgLabel, Integer.valueOf(Integer.MIN_VALUE));
                imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
                imgLabel.setLocation(0, 0);
                cp = getContentPane();
                cp.setLayout(null);
                ((JPanel) cp).setOpaque(false);

                n = 1;
            } else {
                getLayeredPane().remove(imgLabel);
                repaint();

                ImageIcon img = new ImageIcon("./images/20211016120322.jpg");
                imgLabel = new JLabel(img);
                getLayeredPane().add(imgLabel, Integer.valueOf(Integer.MIN_VALUE));
                imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
                imgLabel.setLocation(0, 0);
                cp = getContentPane();
                cp.setLayout(null);
                ((JPanel) cp).setOpaque(false);

                n = 0;
            }
        });
    }

}
