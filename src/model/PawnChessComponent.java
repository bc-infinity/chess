package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PawnChessComponent extends ChessComponent {
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;
    private Image pawnImage;
    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }
    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
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
        int x = getChessboardPoint().getX();
        int y = getChessboardPoint().getY();
        if (getChessColor()==ChessColor.BLACK) {
            if (x == 1) {
                if (chessComponents[2][y].getChessColor()==ChessColor.NONE) {
                    move.add(new ChessboardPoint(2, y));
                    if (chessComponents[3][y].getChessColor()==ChessColor.NONE) {
                        move.add(new ChessboardPoint(3, y));
                    }
                }
                if (y - 1 >= 0) {
                    if (chessComponents[2][y - 1].getChessColor()==ChessColor.WHITE) {
                        move.add(new ChessboardPoint(2, y - 1));
                    }
                }
                if (y + 1 < 8) {
                    if (chessComponents[2][y + 1].getChessColor()==ChessColor.WHITE) {
                        move.add(new ChessboardPoint(2, y + 1));
                    }
                }
            } else if (x < 7) {
                if (chessComponents[x + 1][y].getChessColor()==ChessColor.NONE) {
                    move.add(new ChessboardPoint(x + 1, y));
                }
                if (y - 1 >= 0) {
                    if (chessComponents[x + 1][y - 1].getChessColor()==ChessColor.WHITE) {
                        move.add(new ChessboardPoint(x + 1, y - 1));
                    }
                }
                if (y + 1 < 8) {
                    if (chessComponents[x + 1][y + 1].getChessColor()==ChessColor.WHITE) {
                        move.add(new ChessboardPoint(x + 1, y + 1));
                    }
                }
            }
        } else {
            if (x == 6) {
                if (chessComponents[5][y].getChessColor()==ChessColor.NONE) {
                    move.add(new ChessboardPoint(5, y));
                    if (chessComponents[4][y].getChessColor()==ChessColor.NONE) {
                        move.add(new ChessboardPoint(4, y));
                    }
                }
                if (y - 1 >= 0) {
                    if (chessComponents[5][y - 1].getChessColor()==ChessColor.BLACK) {
                        move.add(new ChessboardPoint(5, y - 1));
                    }
                }
                if (y + 1 < 8) {
                    if (chessComponents[5][y + 1].getChessColor()==ChessColor.BLACK) {
                        move.add(new ChessboardPoint(5, y + 1));
                    }
                }
            } else if (x > 0) {
                if (chessComponents[x - 1][y].getChessColor()==ChessColor.NONE) {
                    move.add(new ChessboardPoint(x - 1, y));
                }
                if (y - 1 >= 0) {
                    if (chessComponents[x - 1][y - 1].getChessColor()==ChessColor.BLACK) {
                        move.add(new ChessboardPoint(x - 1, y - 1));
                    }
                }
                if (y + 1 < 8) {
                    if (chessComponents[x - 1][y + 1].getChessColor()==ChessColor.BLACK) {
                        move.add(new ChessboardPoint(x - 1, y + 1));
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
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
