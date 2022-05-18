package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnightChessComponent extends ChessComponent {
    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;
    private Image knightImage;
    public void loadResource() throws IOException {
        if (KNIGHT_WHITE == null) {
            KNIGHT_WHITE = ImageIO.read(new File("./images/knight-white.png"));
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(new File("./images/knight-black.png"));
        }
    }
    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = KNIGHT_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = KNIGHT_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKnightImage(color);
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
    public List<ChessboardPoint> canMoveTo(ChessComponent[][] chessboard) {
        ArrayList<ChessboardPoint> move=new ArrayList<>();
        int x=getChessboardPoint().getX();
        int y=getChessboardPoint().getY();
        if(chessColor==ChessColor.BLACK){
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if((Math.abs(i-x)==2&&Math.abs(j-y)==1)||(Math.abs(i-x)==1&&Math.abs(j-y)==2)){
                        if (chessboard[i][j].getChessColor()!=ChessColor.BLACK){
                            move.add(new ChessboardPoint(i,j));
                        }
                    }
                }
            }
        }else {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if ((Math.abs(i-x)==2&&Math.abs(j-y)==1)||(Math.abs(i-x)==1&&Math.abs(j-y)==2)) {
                        if (chessboard[i][j].getChessColor()!=ChessColor.WHITE) {
                            move.add(new ChessboardPoint(i, j));
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
        g.drawImage(knightImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
