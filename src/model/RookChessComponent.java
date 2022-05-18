package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示国际象棋里面的车
 */
public class RookChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image ROOK_WHITE;
    private static Image ROOK_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image rookImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (ROOK_WHITE == null) {
            ROOK_WHITE = ImageIO.read(new File("./images/rook-white.png"));
        }

        if (ROOK_BLACK == null) {
            ROOK_BLACK = ImageIO.read(new File("./images/rook-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                rookImage = ROOK_WHITE;
            } else if (color == ChessColor.BLACK) {
                rookImage = ROOK_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RookChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateRookImage(color);
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */

    @Override
    public boolean canMoveTo( ChessComponent[][] chessComponents,ChessboardPoint destination) {
        boolean ok=false;
        List<ChessboardPoint> move=canMoveTo(chessComponents);
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

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(rookImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }

    @Override
    public List<ChessboardPoint> canMoveTo(ChessComponent[][] chessboard) {
        ArrayList<ChessboardPoint> move = new ArrayList<>();
        int[]d={1,-1};
        if(chessColor==ChessColor.BLACK) {
            for(int i=0;i<2;i++){
                int x = getChessboardPoint().getX();
                int y = getChessboardPoint().getY();
                for (int j=0;j<8;j++) {
                    x+=d[i];
                    if (x < 8&&x>=0) {
                        if (chessboard[x][y].getChessColor()==ChessColor.NONE){
                            move.add(new ChessboardPoint(x,y));
                        }else if(chessboard[x][y].getChessColor()==ChessColor.WHITE){
                            move.add(new ChessboardPoint(x,y));
                            break;
                        }else{
                            break;
                        }
                    }else{
                        break;
                    }
                }
            }
            for(int i=0;i<2;i++){
                int x = getChessboardPoint().getX();
                int y = getChessboardPoint().getY();
                for (int j=0;j<8;j++) {
                    y+=d[i];
                    if (y < 8&&y>=0) {
                        if (chessboard[x][y].getChessColor()==ChessColor.NONE){
                            move.add(new ChessboardPoint(x,y));
                        }else if(chessboard[x][y].getChessColor()==ChessColor.WHITE){
                            move.add(new ChessboardPoint(x,y));
                            break;
                        }else{
                            break;
                        }
                    }else {
                        break;
                    }
                }
            }
        }else {
            for(int i=0;i<2;i++){
                int x = getChessboardPoint().getX();
                int y = getChessboardPoint().getY();
                for (int j=0;j<8;j++) {
                    x+=d[i];
                    if (x < 8&&x>=0) {
                        if (chessboard[x][y].getChessColor()==ChessColor.NONE){
                            move.add(new ChessboardPoint(x,y));
                        }else if(chessboard[x][y].getChessColor()==ChessColor.BLACK){
                            move.add(new ChessboardPoint(x,y));
                            break;
                        }else{
                            break;
                        }
                    }else {
                        break;
                    }
                }
            }
            for(int i=0;i<2;i++){
                int x = getChessboardPoint().getX();
                int y = getChessboardPoint().getY();
                for (int j=0;j<8;j++) {
                    y+=d[i];
                    if (y < 8&&y>=0) {
                        if (chessboard[x][y].getChessColor()==ChessColor.NONE){
                            move.add(new ChessboardPoint(x,y));
                        }else if(chessboard[x][y].getChessColor()==ChessColor.BLACK){
                            move.add(new ChessboardPoint(x,y));
                            break;
                        }else{
                            break;
                        }
                    }else {
                        break;
                    }
                }
            }
        }
        return move;
    }
}
