package view;

import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static view.ChessGameFrame.*;


/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;
    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.BLACK;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    public ArrayList<List<String>> steps = new ArrayList<>();
    public String player;

    public Chessboard(int width, int height,List<String> chessData) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        if (chessData==null) {
            initial();
        }
    }

    public List<String> getLastStep(){
        return steps.get(steps.size()-1);
    }
    public void firstStep(List<String> chessData){
        steps.add(chessData);
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(),
                col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        player = currentColor == ChessColor.BLACK ? "WHITE" : "BLACK";
        setPlayerLabel(currentColor);
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            StringBuilder str = new StringBuilder();
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof KingChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.BLACK)
                        str.append('K');
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE)
                        str.append('k');
                }
                if (chessComponents[i][j] instanceof QueenChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.BLACK)
                        str.append('Q');
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE)
                        str.append('q');
                }
                if (chessComponents[i][j] instanceof RookChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.BLACK)
                        str.append('R');
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE)
                        str.append('r');
                }
                if (chessComponents[i][j] instanceof BishopChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.BLACK)
                        str.append('B');
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE)
                        str.append('b');
                }
                if (chessComponents[i][j] instanceof KnightChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.BLACK)
                        str.append('N');
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE)
                        str.append('n');
                }
                if (chessComponents[i][j] instanceof PawnChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.BLACK)
                        str.append('P');
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE)
                        str.append('p');
                }
                if (chessComponents[i][j] instanceof EmptySlotComponent) {
                    str.append('_');
                }
            }
            result.add(str.toString());
        }
        if (getCurrentColor() == ChessColor.BLACK)
            result.add("b");
        if (getCurrentColor() == ChessColor.WHITE)
            result.add("w");
        steps.add(result);
        roundLabel.setText("Round:"+(steps.size()+1)/2);


    }

    private void initChessOnBoard(ChessComponent[][] chessComponents){
        for (ChessComponent[] component : chessComponents) {
            for (ChessComponent chessComponent : component) {
                putChessOnBoard(chessComponent);
                chessComponent.setVisible(true);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    public void checkMate(){
        boolean life = false;
        if (currentColor==ChessColor.BLACK) {
            if (ifWhiteCheckmate(chessComponents)) {
                JOptionPane.showMessageDialog(this, "Warning, do not send your king to be eaten.");
                steps.remove(steps.size()-1);
                loadGame(steps.get(steps.size()-1));
                setPlayerLabel(currentColor);
                roundLabel.setText("Round:"+(steps.size()+1)/2);
                repaint();
            }
            if (ifBlackCheckmate(chessComponents)) {
                if (blackEscapeCheckmate(chessComponents)) {
                    JOptionPane.showMessageDialog(this, "Pay attention: Black");
                } else {
                    JOptionPane.showMessageDialog(this, "Win: White");
                }
            }
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
//                        life = true;
//                        break;
//                    }
//                }
//                if (life) {
//                    break;
//                }
//            }
//            if (!life) {
//                JOptionPane.showMessageDialog(this, "Win: White");
//            }
        }else {
            if (ifBlackCheckmate(chessComponents)) {
                JOptionPane.showMessageDialog(this, "Warning, do not send your king to be eaten.");
                steps.remove(steps.size()-1);
                loadGame(steps.get(steps.size()-1));
                setPlayerLabel(currentColor);
                roundLabel.setText("Round:"+(steps.size()+1)/2);
                repaint();
            }
            if (ifWhiteCheckmate(chessComponents)) {
                if (whiteEscapeCheckmate(chessComponents)) {
                    JOptionPane.showMessageDialog(this, "Pay attention: White");
                } else {
                    JOptionPane.showMessageDialog(this, "Win: Black");
                }
            }
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    if (chessComponents[i][j] instanceof KingChessComponent && chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
//                        life = true;
//                        break;
//                    }
//                }
//                if (life) {
//                    break;
//                }
//            }
//            if (!life) {
//                JOptionPane.showMessageDialog(this, "Win: Black");
//            }
        }
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                if( chessComponents[i][j] != null )
                    remove(chessComponents[i][j]);
            }
        }

        if (chessData.get(8).charAt(0) == 'w') {
            currentColor = ChessColor.WHITE;
            player = "WHITE";
        } else {
            currentColor = ChessColor.BLACK;
            player = "BLACK";
        }

        //String---->排进数组chessComponent[][]
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                switch (chessData.get(i).charAt(j)) {
                    case ('K') -> chessComponents[i][j] = new KingChessComponent(new ChessboardPoint(i,j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                    case ('k') -> chessComponents[i][j] = new KingChessComponent(new ChessboardPoint(i,j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                    case ('Q') -> chessComponents[i][j] = new QueenChessComponent(new ChessboardPoint(i,j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                    case ('q') -> chessComponents[i][j] = new QueenChessComponent(new ChessboardPoint(i,j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                    case ('R') -> chessComponents[i][j] = new RookChessComponent(new ChessboardPoint(i,j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                    case ('r') -> chessComponents[i][j] = new RookChessComponent(new ChessboardPoint(i,j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                    case ('N') -> chessComponents[i][j] = new KnightChessComponent(new ChessboardPoint(i,j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                    case ('n') -> chessComponents[i][j] = new KnightChessComponent(new ChessboardPoint(i,j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                    case ('B') -> chessComponents[i][j] = new BishopChessComponent(new ChessboardPoint(i,j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                    case ('b') -> chessComponents[i][j] = new BishopChessComponent(new ChessboardPoint(i,j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                    case ('P') -> chessComponents[i][j] = new PawnChessComponent(new ChessboardPoint(i,j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                    case ('p') -> chessComponents[i][j] = new PawnChessComponent(new ChessboardPoint(i,j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                    case ('_') -> chessComponents[i][j] = new EmptySlotComponent(new ChessboardPoint(i,j), calculatePoint(i, j), clickController, CHESS_SIZE);
                }
            }
        }

        //数组---->排上棋盘
        initChessOnBoard(chessComponents);

    }

    public void initial() {
        List<String> chessboards = new ArrayList<>();
        chessboards.add("RNBQKBNR");
        chessboards.add("PPPPPPPP");
        chessboards.add("________");
        chessboards.add("________");
        chessboards.add("________");
        chessboards.add("________");
        chessboards.add("pppppppp");
        chessboards.add("rnbqkbnr");
        chessboards.add("w");
        steps.clear();
        steps.add(chessboards);
        loadGame(steps.get(0));
    }

    public boolean ifWhiteCheckmate(ChessComponent[][] chessComponents) {
        int x = 0, y = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof KingChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                        x = i;
                        y = j;
                        break;
                    }
                }
            }
        }
        List<ChessboardPoint> move;
        boolean checkmate = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                    move = chessComponents[i][j].canMoveTo(chessComponents);
                    if (move.size() != 0) {
                        for (int t = 0; t < move.size(); t++) {
                            if (move.get(t).getX() == x && move.get(t).getY() == y) {
                                checkmate = true;
                            }
                        }
                    }
                }
            }
        }
        return checkmate;
    }

    public boolean ifBlackCheckmate(ChessComponent[][] chessComponents) {
        int x = 0, y = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j] instanceof KingChessComponent) {
                    if (chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                        x = i;
                        y = j;
                        break;
                    }
                }
            }
        }
        List<ChessboardPoint> move;
        boolean checkmate = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                    move = chessComponents[i][j].canMoveTo(chessComponents);
                    if (move.size() != 0) {
                        for (int t = 0; t < move.size(); t++) {
                            if (move.get(t).getX() == x && move.get(t).getY() == y) {
                                checkmate = true;
                            }
                        }
                    }
                }
            }
        }
        return checkmate;
    }

    public boolean whiteEscapeCheckmate(ChessComponent[][] chessComponents) {
        ChessComponent[][] tryEscape = new ChessComponent[8][8];
        boolean safe = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tryEscape[i][j] = chessComponents[i][j];
            }
        }
        List<ChessboardPoint> move;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() == ChessColor.WHITE) {
                    move = chessComponents[i][j].canMoveTo(chessComponents);
                    if (move.size() != 0) {
                        for (int t = 0; t < move.size(); t++) {
                            tryEscape[move.get(t).getX()][move.get(t).getY()] = tryEscape[i][j];
                            tryEscape[i][j] = new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE);
                            if (!ifWhiteCheckmate(tryEscape)) {
                                safe = true;
                            }
                        }
                    }
                    if (safe){
                        break;
                    }
                }
                if (safe) {
                    break;
                }
            }
            if (safe){
                break;
            }
        }
        return safe;
    }

    public boolean blackEscapeCheckmate(ChessComponent[][] chessComponents) {
        ChessComponent[][] tryEscape = new ChessComponent[8][8];
        boolean safe = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tryEscape[i][j] = chessComponents[i][j];
            }
        }
        List<ChessboardPoint> move;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].getChessColor() == ChessColor.BLACK) {
                    move = chessComponents[i][j].canMoveTo(chessComponents);
                    if (move.size() != 0) {
                        for (int t = 0; t < move.size(); t++) {
                            tryEscape[move.get(t).getX()][move.get(t).getY()] = tryEscape[i][j];
                            tryEscape[i][j] = new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE);
                            if (!ifBlackCheckmate(tryEscape)) {
                                safe = true;
                                break;
                            }
                        }
                    }
                }
                if (safe) {
                    break;
                }
            }
            if (safe) {
                break;
            }
        }
        return safe;
    }
    public void noMoveDraw(){
        boolean canMove=false;
        if (currentColor==ChessColor.BLACK){
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(chessComponents[i][j].getChessColor()==ChessColor.BLACK){
                        if(chessComponents[i][j].canMoveTo(chessComponents).size()!=0){
                            canMove=true;
                            break;
                        }
                    }
                }
                if (canMove){
                    break;
                }
            }
            if(!canMove){
                JOptionPane.showMessageDialog(this, "Draw: Black has no chess to move.");
            }
        }else {
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(chessComponents[i][j].getChessColor()==ChessColor.WHITE){
                        if(chessComponents[i][j].canMoveTo(chessComponents).size()!=0){
                            canMove=true;
                            break;
                        }
                    }
                }
                if (canMove){
                    break;
                }
            }
            if(!canMove){
                JOptionPane.showMessageDialog(this, "Draw: White has no chess to move.");
            }
        }
    }
    public void threeTimesDraw(){
        int n=0;
        if(steps.size()>=3){
            for (int i=0;i<steps.size()-1;i++){
                boolean same=true;
                for(int j=0;j<8;j++){
                    if (!Objects.equals(steps.get(i).get(j), steps.get(steps.size() - 1).get(j))){
                        same=false;
                        break;
                    }
                }
                if(same){
                    n++;
                }
            }
            if(n>=3){
                JOptionPane.showMessageDialog(this, "Draw: More than three times.");
            }
        }
    }
}

