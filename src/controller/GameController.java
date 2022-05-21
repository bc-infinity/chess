package controller;

import model.ChessColor;
import view.Chessboard;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static view.ChessGameFrame.playerLabel;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
    }
    public Chessboard getChessboard(){
        return chessboard;
    }

    public void writeDataToFile(String path) {
        File file = new File(path);
        List<String> board = chessboard.getLastStep();
        System.out.println(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader reader = new FileReader(path);
            FileWriter writer = new FileWriter(path);
            for (int i = 0; i < 9; i++) {
                writer.write(board.get(i));
                writer.write("\n");
            }
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<String> loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));
            chessboard.loadGame(chessData);
            if(chessboard.getCurrentColor()== ChessColor.BLACK){
                playerLabel.setText(ChessColor.BLACK.getName()+"'s");
            }else{
                playerLabel.setText(ChessColor.WHITE.getName()+"'s");
            }
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> loadGame(String path) {
        try {
            if(path!=null) {
                if (path.length() >= 3) {
                    if (path.substring(path.length() - 3, path.length()).equals("txt")) {
                        List<String> chessData = Files.readAllLines(Path.of(path));
                        if (checkBoard(chessData)) {
                            if (checkPiece(chessData)) {
                                if (checkPlayer(chessData)) {
                                    return chessData;
                                } else {
                                    System.out.println("103");
                                    JOptionPane.showMessageDialog(new JOptionPane(), "103");
                                    return null;
                                }
                            } else {
                                System.out.println("102");
                                JOptionPane.showMessageDialog(new JOptionPane(), "102");
                                return null;
                            }

                        } else {
                            System.out.println("101");
                            JOptionPane.showMessageDialog(new JOptionPane(), "101");
                            return null;
                        }
                    } else {
                        System.out.println("104");
                        JOptionPane.showMessageDialog(new JOptionPane(), "104");
                        return null;
                    }
                } else {
                    return null;
                }
            }else{
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkBoard(List<String> chessData) {
        boolean ok = false;
        if (chessData.size() >= 8) {
            boolean len = true;
            for (int i = 0; i < 8; i++) {
                if (chessData.get(i).length() != 8) {
                    len = false;
                    break;
                }
            }
            if (len) {
                ok = true;
            }
        }
        return ok;
    }

    public boolean checkPiece(List<String> chessData) {
        boolean ok = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char piece = chessData.get(i).charAt(j);
                if (piece < 65 || (piece > 90 && piece < 97 && piece != 95) || piece > 122) {
                    ok = false;
                    break;
                }
            }
        }
        return ok;
    }

    public boolean checkPlayer(List<String> chessData) {
        if (chessData.size() == 9) {
            return true;
        } else {
            return false;
        }
    }
}
