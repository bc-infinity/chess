package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QueenChessComponent extends ChessComponent {
    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;
    private Image queenImage;
    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File("./images/queen-white.png"));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("./images/queen-black.png"));
        }
    }
    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKnightImage(color);
    }
    @Override
    public List<ChessboardPoint> canMoveTo(ChessComponent[][] chessComponents) {
        ArrayList<ChessboardPoint> move = new ArrayList<>();
        int[] d = {1, -1};
        int[] dx = {1, -1};
        int[] dy = {1, -1};
        if (getChessColor()==ChessColor.BLACK) {
            for (int i = 0; i < 2; i++) {
                int x = getChessboardPoint().getX();
                int y = getChessboardPoint().getY();
                for (int j = 0; j < 8; j++) {
                    x += d[i];
                    if (x < 8 && x >= 0) {
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
            for (int i = 0; i < 2; i++) {
                int x = getChessboardPoint().getX();
                int y = getChessboardPoint().getY();
                for (int j = 0; j < 8; j++) {
                    y += d[i];
                    if (y < 8 && y >= 0) {
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
                int x = getChessboardPoint().getX();
                int y = getChessboardPoint().getY();
                for (int j = 0; j < 8; j++) {
                    x += d[i];
                    if (x < 8 && x >= 0) {
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
            for (int i = 0; i < 2; i++) {
                int x = getChessboardPoint().getX();
                int y = getChessboardPoint().getY();
                for (int j = 0; j < 8; j++) {
                    y += d[i];
                    if (y < 8 && y >= 0) {
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
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(queenImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
