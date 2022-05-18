package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BishopChessComponent extends ChessComponent {
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;
    private Image bishopImage;
    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/bishop-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }
    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }
    @Override
    public boolean canMoveTo( ChessComponent[][] chessboard,ChessboardPoint destination) {
        boolean ok=false;
        List<ChessboardPoint> move=canMoveTo(chessboard);
        if (move.size()!=0) {
            for (int i = 0; i < move.size(); i++) {
                if(move.get(i).getX()==destination.getX()&&move.get(i).getY()==destination.getY()){
                    ok=true;
                    break;
                }
            }
        }
        return ok;
    }
    @Override
    public List<ChessboardPoint> canMoveTo(ChessComponent[][] chessComponents) {
        ArrayList<ChessboardPoint> move = new ArrayList<>();
        int[] dx = {1, -1};
        int[] dy = {1, -1};
        if (getChessColor()==ChessColor.BLACK) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    int x = getChessboardPoint().getX();
                    int y = getChessboardPoint().getY();
                    for (int k = 0; k < 8; k++) {
                        x += dx[i];
                        y += dy[j];
                        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
                            if (chessComponents[x][y].getChessColor()==ChessColor.NONE) {
                                move.add(new ChessboardPoint(x, y));
                            } else if (chessComponents[x][y].getChessColor()==ChessColor.WHITE) {
                                move.add(new ChessboardPoint(x, y));
                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }

        } else {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    int x = getChessboardPoint().getX();
                    int y = getChessboardPoint().getY();
                    for (int k = 0; k < 8; k++) {
                        x += dx[i];
                        y += dy[j];
                        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
                            if (chessComponents[x][y].getChessColor()==ChessColor.NONE) {
                                move.add(new ChessboardPoint(x, y));
                            } else if (chessComponents[x][y].getChessColor()==ChessColor.BLACK) {
                                move.add(new ChessboardPoint(x, y));
                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return move;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(bishopImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
