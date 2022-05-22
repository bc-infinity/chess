package controller;


import model.ChessColor;
import model.ChessComponent;
import view.Chessboard;
import view.ChessboardPoint;

import java.util.List;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;
    private ChessComponent second;
    private ChessComponent third;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
                List<ChessboardPoint> show=chessComponent.canMoveTo(chessboard.getChessComponents());
                if(show.size()>0){
                    for(int i=0;i<8;i++){
                        for (int j=0;j<8;j++){
                            for(int k=0;k<show.size();k++){
                                if (show.get(k).getX()==i&&show.get(k).getY()==j){
                                    chessboard.getChessComponents()[i][j].setAvailable(true);
                                }
                            }
                        }
                    }
                }
                chessboard.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
                for(int i=0;i<8;i++){
                    for (int j=0;j<8;j++){
                        if (chessboard.getChessComponents()[i][j].isAvailable()){
                            chessboard.getChessComponents()[i][j].setAvailable(false);
                        }
                    }
                }
                chessboard.repaint();
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                first.setSelected(false);
                for(int i=0;i<8;i++){
                    for (int j=0;j<8;j++){
                        if (chessboard.getChessComponents()[i][j].isAvailable()){
                            chessboard.getChessComponents()[i][j].setAvailable(false);
                        }
                    }
                }
                chessboard.repaint();
                chessboard.noMoveDraw();
                chessboard.checkMate();
                chessboard.threeTimesDraw();
                first = null;
            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }

    public void Enter(ChessComponent chessComponent) {
        if (chessComponent.getChessColor() != ChessColor.NONE) {
            chessComponent.setEntered(true);
            second = chessComponent;
            second.repaint();
        }
    }

    public void Exited(ChessComponent chessComponent) {
        if (chessComponent.getChessColor() != ChessColor.NONE) {
            chessComponent.setEntered(false);
            second = chessComponent;
            second.repaint();
        }
    }
}

