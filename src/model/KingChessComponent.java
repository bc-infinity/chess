package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KingChessComponent extends ChessComponent{
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
        ArrayList<ChessboardPoint>move=new ArrayList<>();
        int x=getChessboardPoint().getX();
        int y=getChessboardPoint().getY();
        if(getChessColor()==ChessColor.BLACK){
            if (x-1>=0&&y-1>=0){
                if(chessComponents[x-1][y-1].getChessColor()!=ChessColor.BLACK){
                    move.add(new ChessboardPoint(x-1,y-1));
                }
            }
            if (x-1>=0){
                if(chessComponents[x-1][y].getChessColor()!=ChessColor.BLACK){
                    move.add(new ChessboardPoint(x-1,y));
                }
            }
            if (x-1>=0&&y+1<8){
                if(chessComponents[x-1][y+1].getChessColor()!=ChessColor.BLACK){
                    move.add(new ChessboardPoint(x-1,y+1));
                }
            }
            if (y-1>=0){
                if(chessComponents[x][y-1].getChessColor()!=ChessColor.BLACK){
                    move.add(new ChessboardPoint(x,y-1));
                }
            }
            if (y+1<8){
                if(chessComponents[x][y+1].getChessColor()!=ChessColor.BLACK){
                    move.add(new ChessboardPoint(x,y+1));
                }
            }
            if (x+1<8&&y-1>=0){
                if(chessComponents[x+1][y-1].getChessColor()!=ChessColor.BLACK){
                    move.add(new ChessboardPoint(x+1,y-1));
                }
            }
            if (x+1<8){
                if(chessComponents[x+1][y].getChessColor()!=ChessColor.BLACK){
                    move.add(new ChessboardPoint(x+1,y));
                }
            }
            if (x+1<8&&y+1<8){
                if(chessComponents[x+1][y+1].getChessColor()!=ChessColor.BLACK){
                    move.add(new ChessboardPoint(x+1,y+1));
                }
            }
        }else{
            if (x-1>=0&&y-1>=0){
                if(chessComponents[x-1][y-1].getChessColor()!=ChessColor.WHITE){
                    move.add(new ChessboardPoint(x-1,y-1));
                }
            }
            if (x-1>=0){
                if(chessComponents[x-1][y].getChessColor()!=ChessColor.WHITE){
                    move.add(new ChessboardPoint(x-1,y));
                }
            }
            if (x-1>=0&&y+1<8){
                if(chessComponents[x-1][y+1].getChessColor()!=ChessColor.WHITE){
                    move.add(new ChessboardPoint(x-1,y+1));
                }
            }
            if (y-1>=0){
                if(chessComponents[x][y-1].getChessColor()!=ChessColor.WHITE){
                    move.add(new ChessboardPoint(x,y-1));
                }
            }
            if (y+1<8){
                if(chessComponents[x][y+1].getChessColor()!=ChessColor.WHITE){
                    move.add(new ChessboardPoint(x,y+1));
                }
            }
            if (x+1<8&&y-1>=0){
                if(chessComponents[x+1][y-1].getChessColor()!=ChessColor.WHITE){
                    move.add(new ChessboardPoint(x+1,y-1));
                }
            }
            if (x+1<8){
                if(chessComponents[x+1][y].getChessColor()!=ChessColor.WHITE){
                    move.add(new ChessboardPoint(x+1,y));
                }
            }
            if (x+1<8&&y+1<8){
                if(chessComponents[x+1][y+1].getChessColor()!=ChessColor.WHITE){
                    move.add(new ChessboardPoint(x+1,y+1));
                }
            }

        }
        return move;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }

}
