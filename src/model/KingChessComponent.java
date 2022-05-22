package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KingChessComponent extends ChessComponent {
    private static Image KING_WHITE;
    private static Image KING_BLACK;
    private Image kingImage;

    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size);
        initiateKingImage(chessColor);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        boolean ok = false;
        List<ChessboardPoint> move = canMoveTo(chessboard);
        if (move.size() != 0) {
            for (int i = 0; i < move.size(); i++) {
                if (move.get(i).getX() == destination.getX() && move.get(i).getY() == destination.getY()) {
                    ok = true;
                    break;
                }
            }
        }
        return ok;
    }

    @Override
    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/king-black.png"));
        }
    }

    @Override
    public List<ChessboardPoint> canMoveTo(ChessComponent[][] chessComponents) {
        ArrayList<ChessboardPoint> move = new ArrayList<>();
        ArrayList<List<ChessboardPoint>> cut = new ArrayList<>();
        int x = getChessboardPoint().getX();
        int y = getChessboardPoint().getY();
        int[] dx = {-1, -1, -1, 0, 0, +1, +1, +1};
        int[] dy = {+1, 0, -1, +1, -1, +1, 0, -1};
        if (getChessColor() == ChessColor.BLACK) {
            for (int i = 0; i < 8; i++) {
                if (x + dx[i] >= 0 && x + dx[i] <= 7 && y + dy[i] >= 0 && y + dy[i] <= 7) {
                    if (chessComponents[x + dx[i]][y + dy[i]].getChessColor() != ChessColor.BLACK) {
                        move.add(new ChessboardPoint(x + dx[i], y + dy[i]));
                    }
                }
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        if (chessComponents[i][j] instanceof PawnChessComponent) {
                            int t = 0;
                            List<ChessboardPoint> p = chessComponents[i][j].canMoveTo(chessComponents);
                            while (t < p.size()) {
                                if (p.get(t).getY() == j) {
                                    p.remove(t);
                                } else {
                                    t++;
                                }
                            }
                            if (i > 0) {
                                if (j > 0) {
                                    p.add(new ChessboardPoint(i - 1, j - 1));
                                }
                                if (j < 7) {
                                    p.add(new ChessboardPoint(i - 1, j + 1));
                                }
                            }
                            cut.add(p);
                        } else if (chessComponents[i][j] instanceof KingChessComponent) {
                            List<ChessboardPoint> k = new ArrayList<>();
                            for (int t = 0; t < 8; t++) {
                                if (x + dx[i] >= 0 && x + dx[i] <= 7 && y + dy[i] >= 0 && y + dy[i] <= 7) {
                                    k.add(new ChessboardPoint(i + dx[t], j + dy[t]));
                                }
                                cut.add(k);
                            }
                        } else {
                            cut.add(chessComponents[i][j].canMoveTo(chessComponents));
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < 8; i++) {
                if (x + dx[i] >= 0 && x + dx[i] <= 7 && y + dy[i] >= 0 && y + dy[i] <= 7) {
                    if (chessComponents[x + dx[i]][y + dy[i]].getChessColor() != ChessColor.WHITE) {
                        move.add(new ChessboardPoint(x + dx[i], y + dy[i]));
                    }
                }
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                        if (chessComponents[i][j] instanceof PawnChessComponent) {
                            int t = 0;
                            List<ChessboardPoint> p = chessComponents[i][j].canMoveTo(chessComponents);
                            while (t < p.size()) {
                                if (p.get(t).getY() == j) {
                                    p.remove(t);
                                } else {
                                    t++;
                                }
                            }
                            if (i < 7) {
                                if (j > 0) {
                                    p.add(new ChessboardPoint(i + 1, j - 1));
                                }
                                if (j < 7) {
                                    p.add(new ChessboardPoint(i + 1, j + 1));
                                }
                            }
                            cut.add(p);
                        } else if (chessComponents[i][j] instanceof KingChessComponent) {
                            List<ChessboardPoint> k = new ArrayList<>();
                            for (int t = 0; t < 8; t++) {
                                if (x + dx[i] >= 0 && x + dx[i] <= 7 && y + dy[i] >= 0 && y + dy[i] <= 7) {
                                    k.add(new ChessboardPoint(i + dx[t], j + dy[t]));
                                }
                            }
                            cut.add(k);
                        } else {
                            cut.add(chessComponents[i][j].canMoveTo(chessComponents));
                        }
                    }
                }
            }
        }
        int i = 0;
        boolean same = false;
        while (i < move.size()) {
            if (cut.size() > 0) {
                for (int j = 0; j < cut.size(); j++) {
                    if (cut.get(j).size() > 0) {
                        for (int k = 0; k < cut.get(j).size(); k++) {
                            if (move.get(i).getX() == cut.get(j).get(k).getX() && move.get(i).getY() == cut.get(j).get(k).getY()) {
                                move.remove(i);
                                same = true;
                                break;
                            }
                        }
                    }
                    if (same) {
                        same = false;
                        i--;
                        break;
                    }
                }
                i++;
            }
        }
        return move;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(kingImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }

}

