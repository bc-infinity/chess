package controller;

import view.Chessboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GameController {
    private Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            if (path.substring(path.length()-3,path.length()-1).equals("txt")) {
                List<String> chessData = Files.readAllLines(Path.of(path));
                if (checkBoard(chessData)) {
                    if (checkPiece(chessData)) {
                        if (checkPlayer(chessData)) {
                            chessboard.loadGame(chessData);
                            return chessData;
                        } else {
                            System.out.println("103");
                            return null;
                        }
                    } else {
                        System.out.println("102");
                        return null;
                    }

                } else {
                    System.out.println("101");
                    return null;
                }
            }else {
                System.out.println("104");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean checkBoard(List<String> chessData){
        boolean ok=false;
        if(chessData.size()>=8){
            boolean len=true;
            for(int i=0;i<8;i++){
                if(chessData.get(i).length()!=8){
                    len=false;
                    break;
                }
            }
            if (len){
                ok=true;
            }
        }
        return ok;
    }
    public boolean checkPiece(List<String> chessData){
        boolean ok=true;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                char piece=chessData.get(i).charAt(j);
                if(piece<65||(piece>90&&piece<97&&piece!=95)||piece>122){
                    ok=false;
                    break;
                }
            }
        }
        return ok;
    }
    public boolean checkPlayer(List<String> chessData){
        if(chessData.size()==9){
            return true;
        }else {
            return false;
        }
    }
}
